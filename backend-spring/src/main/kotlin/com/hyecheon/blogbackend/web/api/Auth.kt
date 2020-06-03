package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.model.UserMapper
import com.hyecheon.blogbackend.service.UserService
import com.hyecheon.blogbackend.web.dto.UserSignupReqDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Auth(
		val userMapper: UserMapper,
		val userService: UserService
) {
	@PostMapping("/api/signup")
	fun signup(@RequestBody userSignupDto: UserSignupReqDto) = let {
		val user = userMapper.toModel(userSignupDto)
		userService.createUser(user)
	}
}