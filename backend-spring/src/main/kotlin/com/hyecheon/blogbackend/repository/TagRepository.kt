package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TagRepository : JpaRepository<Tag, Long> {
	fun findBySlug(slug: String): Optional<Tag>
	fun deleteBySlug(slug: String)
}