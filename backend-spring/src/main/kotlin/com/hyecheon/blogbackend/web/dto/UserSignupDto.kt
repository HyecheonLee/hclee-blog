package com.hyecheon.blogbackend.web.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class UserSignupReqDto(
		@NotEmpty(message = "Name is required")
		val name: String,
		@Email(message = "Must be a valid email address")
		val email: String,
		@Min(value = 6, message = "Password must be at least 6 character long")
		val password: String
)


data class UserSignupResDto(
		val name: String,
		val email: String,
		val password: String
)