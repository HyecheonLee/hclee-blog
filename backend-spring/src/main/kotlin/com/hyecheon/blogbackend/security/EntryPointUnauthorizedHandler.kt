package com.hyecheon.blogbackend.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyecheon.blogbackend.web.error.ApiError
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class EntryPointUnauthorizedHandler : AuthenticationEntryPoint {
	companion object {
		var E401 = ApiError(status = HttpStatus.UNAUTHORIZED.value(), message = "Access denied")
	}

	private val om = ObjectMapper()

	override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
		E401.url = request.servletPath

		response.status = HttpServletResponse.SC_UNAUTHORIZED;
		response.setHeader("content-type", "application/json");
		response.writer.write(om.writeValueAsString(E401))
		response.writer.flush()
		response.writer.close()

	}

}