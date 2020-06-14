package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.Tag
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TagRepository : MongoRepository<Tag, String> {
	fun findBySlug(slug: String): Optional<Tag>
	fun deleteBySlug(slug: String)
}