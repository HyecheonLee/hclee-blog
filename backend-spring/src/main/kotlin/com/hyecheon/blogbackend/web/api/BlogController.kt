package com.hyecheon.blogbackend.web.api

import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController("/api/blog")
class BlogController {
	fun index() = LocalDateTime.now()
}