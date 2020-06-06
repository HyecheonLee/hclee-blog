package com.hyecheon.blogbackend.security

import com.hyecheon.blogbackend.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component


@Component
class UserAuthenticationProvider(
		@Value("\${jwt.token.role}")
		val role: String,
		val userService: UserService) : AuthenticationProvider {


	override fun authenticate(authentication: Authentication): Authentication {
		val principal = authentication.principal as String
		val credentials = authentication.credentials as String
		val user = userService.login(principal, credentials)
		return UserAuthenticationToken(principal = user.user.username, authorities = generateAuthorities())
	}

	override fun supports(authentication: Class<*>): Boolean {
		return UserAuthenticationToken::class.java.isAssignableFrom(authentication)
	}

	private fun generateAuthorities(): Collection<GrantedAuthority> {
		return AuthorityUtils.createAuthorityList("ROLE_$role")
	}
}