package com.hyecheon.blogbackend.security.jwt

import com.auth0.jwt.Algorithm
import com.auth0.jwt.JWTSigner
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class JWT {
	@Value("\${jwt.token.issuer}")
	lateinit var issuer: String

	@Value("\${jwt.token.clientId}")
	lateinit var clientId: String

	@Value("\${jwt.token.clientSecret}")
	lateinit var clientSecret: String

	@Value("\${jwt.token.expirySeconds}")
	var expirySeconds = 0

	lateinit var signer: JWTSigner
	lateinit var jwtVerifier: JWTVerifier

	@PostConstruct
	fun init() {
		signer = JWTSigner(Base64.decodeBase64(clientSecret))
		jwtVerifier = JWTVerifier(Base64.decodeBase64(clientSecret), clientId, issuer)
	}

	fun defaultOptions() = let {
		val options = JWTSigner.Options()
		options.algorithm = Algorithm.HS512
		options.expirySeconds = expirySeconds
		options.notValidBeforeLeeway = 0
		options.isIssuedAt = true
		options.isJwtId = false
		options
	}

	fun generateToken(claims: MutableMap<String, Any>) = let {
		claims["iss"] = issuer
		signer.sign(claims, defaultOptions())!!
	}

	fun refreshToken(token: String) = let {
		val claims = verify(token)
		claims.remove("exp")
		claims.remove("nbf")
		claims.remove("iat")
		signer.sign(claims, defaultOptions())!!
	}

	fun verify(token: String): MutableMap<String, Any> = let {
		jwtVerifier.verify(token)
	}
}