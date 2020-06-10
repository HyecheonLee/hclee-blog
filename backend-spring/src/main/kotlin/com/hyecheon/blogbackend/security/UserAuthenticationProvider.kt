package com.hyecheon.blogbackend.security

import com.hyecheon.blogbackend.security.jwt.JWT
import com.hyecheon.blogbackend.security.jwt.JwtAuthentication
import com.hyecheon.blogbackend.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
class UserAuthenticationProvider(
		val userService: UserService,
		val jwt: JWT) : AuthenticationProvider {


	override fun authenticate(authentication: Authentication): Authentication {
		val principal = authentication.principal as String
		val credentials = authentication.credentials as String
		val user = userService.login(principal, credentials)
		return UserAuthenticationToken(principal = JwtAuthentication(user.id!!, user.email), authorities = user.getAuthorities())
	}

	override fun supports(authentication: Class<*>): Boolean {
		return UserAuthenticationToken::class.java.isAssignableFrom(authentication)
	}
}