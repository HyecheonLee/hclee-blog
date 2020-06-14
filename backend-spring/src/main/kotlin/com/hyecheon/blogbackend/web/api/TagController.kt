package com.hyecheon.blogbackend.web.api

import com.hyecheon.blogbackend.model.TagMapper
import com.hyecheon.blogbackend.service.TagService
import com.hyecheon.blogbackend.web.dto.TagReqDto
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class TagController(
		val tagMapper: TagMapper,
		val tagService: TagService) {

	@Secured(value = ["ROLE_ADMIN"])
	@PostMapping("/api/tag")
	fun create(@RequestBody tagReqDto: TagReqDto) = let {
		val tag = tagMapper.toModel(tagReqDto)
		tagService.save(tag)
	}

	@GetMapping("/api/tags")
	fun list() = let {
		tagService.list()
	}


	@GetMapping("/api/tag/{slug}")
	fun findBySlug(@PathVariable slug: String) = let { tagService.findBySlug(slug) }

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/api/tag/{slug}")
	fun deleteBySlug(@PathVariable slug: String) = let {
		tagService.deleteBySlug(slug)
		"""{"message":"tag deleted success"}"""
	}
}
