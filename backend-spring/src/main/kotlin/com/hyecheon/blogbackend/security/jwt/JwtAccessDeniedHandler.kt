package com.hyecheon.blogbackend.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyecheon.blogbackend.web.error.ApiRuntimeErrors
import org.springframework.http.HttpStatus
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

	val om = ObjectMapper()

	companion object {
		var E403 = ApiRuntimeErrors(status = HttpStatus.FORBIDDEN.value(), error = "Authentication error (cause: forbidden)", url = "")
	}

	override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: org.springframework.security.access.AccessDeniedException) {
		E403.url = request.servletPath

		response.status = HttpServletResponse.SC_FORBIDDEN
		response.setHeader("content-type", "application/json")
		response.writer.write(om.writeValueAsString(E403))
		response.writer.flush()
		response.writer.close()
	}
}