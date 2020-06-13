package com.hyecheon.blogbackend.service

import com.hyecheon.blogbackend.model.Category
import com.hyecheon.blogbackend.repository.CategoryRepository
import com.hyecheon.blogbackend.utils.toSlug
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CategoryService(
		var categoryRepository: CategoryRepository) {

	@Transactional(readOnly = false)
	fun save(category: Category): Category = let {
		val name = category.name
		category.slug = name.toSlug()
		return categoryRepository.save(category)
	}

	fun list(): MutableList<Category> = categoryRepository.findAll()

	fun findBySlug(slug: String) = let {
		categoryRepository.findBySlug(slug).orElseThrow { IllegalArgumentException("slug not found") }!!
	}

	fun deleteBySlug(slug: String) = let {
		val category = categoryRepository.findBySlug(slug).orElseThrow { IllegalArgumentException("slug not found") }
		categoryRepository.deleteBySlug(category.slug)
	}
}