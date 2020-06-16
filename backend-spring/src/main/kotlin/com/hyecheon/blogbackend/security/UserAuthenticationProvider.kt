package com.hyecheon.blogbackend.security

import com.hyecheon.blogbackend.mapper.UserMapper
import com.hyecheon.blogbackend.security.jwt.JWT
import com.hyecheon.blogbackend.security.jwt.JwtAuthentication
import com.hyecheon.blogbackend.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
class UserAuthenticationProvider(
		val userService: UserService,
		val jwt: JWT, val userMapper: UserMapper) : AuthenticationProvider {


	override fun authenticate(authentication: Authentication): Authentication {
		val principal = authentication.principal as String
		val credentials = authentication.credentials as String
		val user = userService.login(principal, credentials)
		val userProfileDto = userMapper.toUserProfileDto(user)
		return UserAuthenticationToken(principal = JwtAuthentication(userProfileDto), authorities = user.getAuthorities())
	}

	override fun supports(authentication: Class<*>): Boolean {
		return UserAuthenticationToken::class.java.isAssignableFrom(authentication)
	}
}