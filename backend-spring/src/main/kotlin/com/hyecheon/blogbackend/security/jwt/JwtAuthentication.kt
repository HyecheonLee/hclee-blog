package com.hyecheon.blogbackend.security.jwt

import com.hyecheon.blogbackend.web.dto.UserProfileDto

data class JwtAuthentication(
		val user: UserProfileDto,
		val error: String = "",
		val addr: String = "") {
	fun isError(): Boolean = error.isNotEmpty()

}