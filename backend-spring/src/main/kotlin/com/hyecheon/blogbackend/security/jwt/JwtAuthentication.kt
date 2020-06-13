package com.hyecheon.blogbackend.security.jwt

data class JwtAuthentication(
		val email: String,
		val error: String = "",
		val addr: String = "") {
	fun isError(): Boolean = error.isNotEmpty()

}