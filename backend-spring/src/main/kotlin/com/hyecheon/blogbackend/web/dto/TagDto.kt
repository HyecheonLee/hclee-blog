package com.hyecheon.blogbackend.web.dto

import javax.validation.constraints.NotEmpty

data class TagReqDto(
		@NotEmpty(message = "Name is required")
		val name: String
)