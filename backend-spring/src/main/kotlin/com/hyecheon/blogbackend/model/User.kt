package com.hyecheon.blogbackend.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
		var username: String? = null,
		@Indexed(unique = true)
		var name: String? = null,
		@Indexed(unique = true)
		var email: String? = null,
		var profile: String? = null,
		var hashedPassword: String? = null,
		var salt: String? = null,
		var role: Int? = null,
		var photo: String? = null,
		var resetPasswordLink: String? = null
)