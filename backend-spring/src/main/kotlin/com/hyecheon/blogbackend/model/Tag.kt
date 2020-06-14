package com.hyecheon.blogbackend.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime

@Document("tags")
data class Tag(
		@MongoId
		var id: String? = null,
		var name: String = "",
		@Indexed(unique = true)
		var slug: String = "")

