package com.hyecheon.blogbackend.config

import com.hyecheon.blogbackend.utils.CLIENT_HOST
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
				.allowedOrigins(CLIENT_HOST)
	}
}