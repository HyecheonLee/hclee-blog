package com.hyecheon.blogbackend.security.jwt

import com.auth0.jwt.internal.org.apache.commons.codec.CharEncoding
import com.hyecheon.blogbackend.security.UserAuthenticationToken
import com.hyecheon.blogbackend.utils.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.GenericFilterBean
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.regex.Pattern
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationTokenFilter : GenericFilterBean() {
	companion object : Log

	@Value("\${jwt.token.header}")
	private lateinit var tokenHeader: String

	@Value("\${jwt.token.refreshTime}")
	private var refreshTime: Int = 600

	@Autowired
	private lateinit var jwt: JWT

	private val BEARER: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)

	override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		val request = request as HttpServletRequest
		val response = response as HttpServletResponse

		if (SecurityContextHolder.getContext().authentication == null) {
			obtainAuthorizationToken(request)?.let { authorizationToken ->
				val claims = verify(authorizationToken)
				if (log.isDebugEnabled) {
					log.debug("JWT parse result: {}", claims)
				}
				/*만료 10분 전*/
				if (canRefresh(claims, refreshTime)) {
					val newAuthorizationToken = jwt.refreshToken(authorizationToken)
					response.setHeader("api_key", newAuthorizationToken)
				}

				val authorities = obtainAuthorities(claims)
				if (claims.containsKey("email") && authorities != null && authorities.isNotEmpty()) {
					val email = claims["email"] as String
					val error = claims["error"] ?: ""

					val jwtAuthentication = JwtAuthentication(email, error as String, addr = request.remoteAddr)
					if (jwtAuthentication.isError()) {
						log.error("jwt error [{}]", jwtAuthentication)
					}
					val authentication = UserAuthenticationToken(jwtAuthentication, authorities = authorities)
					authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
					SecurityContextHolder.getContext().authentication = authentication
				}
			}
		} else {
			if (log.isDebugEnabled) {
				log.debug("SecurityContextHolder not populated with jwt token, as it already contained: '{}'",
						SecurityContextHolder.getContext().authentication);
			}
		}
		chain.doFilter(request, response)
	}

	private fun canRefresh(claims: Map<String, Any>, rangeMinutes: Int = 600): Boolean {
		if (claims.containsKey("exp")) {
			val expiration = claims["exp"] as Int
			if (expiration != 0) {
				val remainTime = expiration - (System.currentTimeMillis() / 1000L)
				return remainTime < rangeMinutes
			}
		}
		return false
	}

	private fun obtainAuthorities(claims: Map<String, Any>): List<GrantedAuthority>? {
		return claims["roles"]?.let {
			val authMaps = it as Collection<Map<String, String>>
			authMaps.map { authMap ->
				SimpleGrantedAuthority(authMap["authority"] ?: "ROLE_ANONYMOUS")
			}
		}
	}

	private fun obtainAuthorizationToken(request: HttpServletRequest): String? {
		var token = request.getHeader(tokenHeader)
		if (!token.isNullOrEmpty()) {
			if (log.isDebugEnabled) log.debug("Jwt authorization request detected: {}", token)
			try {
				token = URLDecoder.decode(token, CharEncoding.UTF_8)
				val parts = token.split(" ").toTypedArray()
				if (parts.size == 2) {
					val scheme = parts[0]
					val credentials = parts[1]
					return if (BEARER.matcher(scheme).matches()) credentials else
						jwt.generateToken(
								mutableMapOf("email" to "ANONYMOUS@ANONYMOUS.com",
										"error" to "credentials error [$credentials]",
										"roles" to AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")))
				}
			} catch (e: UnsupportedEncodingException) {
				log.error(e.message, e)
			}
		}
		return jwt.generateToken(
				mutableMapOf("email" to "ANONYMOUS@ANONYMOUS.com", "roles" to AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")))
	}


	private fun verify(token: String) = let {
		try {
			jwt.verify(token)
		} catch (e: Exception) {
			jwt.verify(
					jwt.generateToken(mutableMapOf("email" to "ANONYMOUS@ANONYMOUS.com",
							"error" to "verify error [$token]",
							"roles" to AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"))))
		}
	}
}