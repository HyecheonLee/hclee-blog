package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.mapper.UserMapper
import com.hyecheon.blogbackend.security.jwt.JWT
import com.hyecheon.blogbackend.service.UserService
import com.hyecheon.blogbackend.web.dto.UserReqWithTokenDto
import com.hyecheon.blogbackend.web.dto.UserSignInDto
import com.hyecheon.blogbackend.web.dto.UserSignUpReqDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class AuthController(
		val userMapper: UserMapper,
		val userService: UserService,
		val jwt: JWT) {
	@PostMapping("/api/signup")
	fun signup(@RequestBody userSignUpDto: UserSignUpReqDto) = let {
		val user = userMapper.toModel(userSignUpDto)
		userService.createUser(user)
	}

	@PostMapping("/api/signin")
	fun signin(@RequestBody userSignInDto: UserSignInDto) = let {
		val loggedUser = userService.login(userSignInDto.email, userSignInDto.password)
		userMapper.toUserProfileDto(loggedUser)
		UserReqWithTokenDto(token = loggedUser.generateToken(jwt), user = userMapper.toUserProfileDto(loggedUser))
	}

	@GetMapping("/api/signout")
	fun signout(request: HttpServletRequest, response: HttpServletResponse) = let {
		val authentication = SecurityContextHolder.getContext().authentication
		SecurityContextLogoutHandler().logout(request, response, authentication)
		"""{"message": "Signout success"}"""
	}
}