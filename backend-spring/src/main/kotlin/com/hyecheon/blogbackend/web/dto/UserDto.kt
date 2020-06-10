package com.hyecheon.blogbackend.web.dto

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class UserSignUpReqDto(
		@NotEmpty(message = "Name is required")
		val name: String,
		@Email(message = "Must be a valid email address")
		val email: String,
		@Min(value = 6, message = "Password must be at least 6 character long")
		val password: String
)

data class UserSignInDto(
		@Email(message = "Must be a valid email address")
		val email: String,
		@Min(value = 6, message = "Password must be at least 6 character long")
		val password: String
)

data class UserReqWithTokenDto(
		var token: String,
		var user: UserProfileResDto
)

data class UserReqDto(
		var username: String,
		var name: String,
		var email: String,
		var role: String
)

data class UserSignUpResDto(
		val name: String,
		val email: String,
		val password: String
)

data class UserProfileResDto(
		var username: String = "",
		var name: String = "",
		var email: String = "",
		var profile: String = "",
		var role: String = "ROLE_USER",
		var photo: String = "",
		var loginCount: Long = 0,
		var loginTime: LocalDateTime = LocalDateTime.now());