package com.hyecheon.blogbackend.web.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class IndexController {
	@GetMapping("/api")
	fun index() = LocalDateTime.now()
}