package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoryRepository : JpaRepository<Category, Long> {
	fun findBySlug(slug: String): Optional<Category>
	fun deleteBySlug(slug: String)
}