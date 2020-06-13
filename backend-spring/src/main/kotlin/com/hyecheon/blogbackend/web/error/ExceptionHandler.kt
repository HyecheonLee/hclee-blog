package com.hyecheon.blogbackend.web.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.RuntimeException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun handleValidationException(e: MethodArgumentNotValidException, request: HttpServletRequest): ApiError {
		val validationErrors =
				e.bindingResult.fieldErrors
						.fold(mutableMapOf<String, String>()) { acc, fieldError ->
							fieldError.defaultMessage?.let {
								acc[fieldError.field] = it
							}
							acc
						}
		return ApiValidationErrors(status = 400, message = "Validation error", url = request.servletPath, validationErrors = validationErrors)
	}

	@ExceptionHandler(Exception::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun handleException(e: RuntimeException, request: HttpServletRequest): ApiError {
		return ApiRuntimeErrors(status = 400, message = e.message ?: "", url = request.servletPath)
	}
}