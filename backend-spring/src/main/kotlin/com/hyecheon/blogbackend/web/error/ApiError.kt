package com.hyecheon.blogbackend.web.error

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(value = JsonInclude.Include.NON_NULL)
data class ApiError(
		var status: Int,
		var message: String,
		var url: String,
		var validationErrors: Map<String, String> = mapOf()) {
	val timestamp: LocalDateTime = LocalDateTime.now()
}