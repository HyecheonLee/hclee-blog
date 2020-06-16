package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.mapper.UserMapper
import com.hyecheon.blogbackend.security.UserAuthenticationToken
import com.hyecheon.blogbackend.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
		val userService: UserService,
		val userMapper: UserMapper) {


	@GetMapping("/api/profile")
	fun getProfile() = let {
		val userAuthenticationToken = SecurityContextHolder.getContext().authentication as UserAuthenticationToken
		val jwtAuthentication = userAuthenticationToken.principal
		jwtAuthentication.user
	}
}