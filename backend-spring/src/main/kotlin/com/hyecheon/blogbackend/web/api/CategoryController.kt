package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.model.CategoryMapper
import com.hyecheon.blogbackend.service.CategoryService
import com.hyecheon.blogbackend.web.dto.CategoryReqDto
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
class CategoryController(
		val categoryMapper: CategoryMapper,
		val categoryService: CategoryService) {

	@Secured(value = ["ROLE_ADMIN"])
	@PostMapping("/api/category")
	fun create(@RequestBody categoryReqDto: CategoryReqDto) = let {
		val category = categoryMapper.toModel(categoryReqDto)
		categoryService.save(category)
	}

	@GetMapping("/api/categories")
	fun list() = let {
		categoryService.list()
	}


	@GetMapping("/api/category/{slug}")
	fun findBySlug(@PathVariable slug: String) = let { categoryService.findBySlug(slug) }

	@Secured(value = ["ROLE_ADMIN"])
	@DeleteMapping("/api/category/{slug}")
	fun deleteBySlug(@PathVariable slug: String) = let {
		categoryService.deleteBySlug(slug)
		"""{"message":"Category deleted success"}"""
	}
}
