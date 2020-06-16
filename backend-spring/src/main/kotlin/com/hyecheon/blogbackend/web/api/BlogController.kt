package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.model.Photo
import com.hyecheon.blogbackend.security.UserAuthenticationToken
import com.hyecheon.blogbackend.service.BlogService
import com.hyecheon.blogbackend.web.dto.BlogReqDto
import com.hyecheon.blogbackend.web.dto.BlogResDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@RestController
class BlogController(
		private val blogService: BlogService) {

	fun index() = LocalDateTime.now()

	@PostMapping("/api/blog")
	fun saveBlog(blogReqDto: BlogReqDto, @RequestPart photo: MultipartFile) = let {
		val userAuthenticationToken = SecurityContextHolder.getContext().authentication as UserAuthenticationToken
		val jwtAuthentication = userAuthenticationToken.principal
		val blog = blogReqDto.toModel(photo.toPhoto())
		val userProfileDto = jwtAuthentication.user
		blog.postedBy = userProfileDto.toModel()
		val savedBlog = blogService.save(blog)
		BlogResDto.fromModel(savedBlog)
	}
}

private fun MultipartFile.toPhoto(): Photo {
	return Photo(contentType = contentType ?: "", data = bytes)
}