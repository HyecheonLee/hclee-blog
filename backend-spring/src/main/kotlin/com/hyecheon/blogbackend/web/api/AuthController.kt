package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.model.UserMapper
import com.hyecheon.blogbackend.service.UserService
import com.hyecheon.blogbackend.web.dto.UserReqDto
import com.hyecheon.blogbackend.web.dto.UserReqWithTokenDto
import com.hyecheon.blogbackend.web.dto.UserSignInDto
import com.hyecheon.blogbackend.web.dto.UserSignUpReqDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
		val userMapper: UserMapper,
		val userService: UserService
) {
	@PostMapping("/api/signup")
	fun signup(@RequestBody userSignUpDto: UserSignUpReqDto) = let {
		val user = userMapper.toModel(userSignUpDto)
		userService.createUser(user)
	}

	@PostMapping("/api/signin")
	fun signin(@RequestBody userSignInDto: UserSignInDto) = let {
		val loggedUser = userService.login(userSignInDto.email, userSignInDto.password)
		loggedUser
	}
}