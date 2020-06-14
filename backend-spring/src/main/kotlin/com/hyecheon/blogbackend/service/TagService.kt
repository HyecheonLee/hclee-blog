package com.hyecheon.blogbackend.service

import com.hyecheon.blogbackend.model.Tag
import com.hyecheon.blogbackend.repository.TagRepository
import com.hyecheon.blogbackend.utils.toSlug
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class TagService(
		var categoryRepository: TagRepository) {

	@Transactional(readOnly = false)
	fun save(tag: Tag): Tag = let {
		val name = tag.name
		tag.slug = name.toSlug()
		return categoryRepository.save(tag)
	}

	fun list(): MutableList<Tag> = categoryRepository.findAll()

	fun findBySlug(slug: String) = let {
		categoryRepository.findBySlug(slug).orElseThrow { IllegalArgumentException("slug not found") }!!
	}

	fun deleteBySlug(slug: String) = let {
		val category = categoryRepository.findBySlug(slug).orElseThrow { IllegalArgumentException("slug not found") }
		categoryRepository.deleteBySlug(category.slug)
	}
}