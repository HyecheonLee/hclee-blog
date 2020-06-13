package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.Category
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CategoryRepository : MongoRepository<Category, String> {
	fun findBySlug(slug: String): Optional<Category>
	fun deleteBySlug(slug: String)
}