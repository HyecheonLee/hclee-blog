package com.hyecheon.blogbackend.service

import com.hyecheon.blogbackend.model.Blog
import com.hyecheon.blogbackend.repository.BlogRepository
import org.springframework.stereotype.Service

@Service
class BlogService(
		private val blogRepository: BlogRepository) {
	fun save(blog: Blog): Blog {
		val savedBlog = blogRepository.save(blog)
		val blogCategories = savedBlog.blogCategories
		return blogRepository.findById(savedBlog.id!!).get()
	}
}