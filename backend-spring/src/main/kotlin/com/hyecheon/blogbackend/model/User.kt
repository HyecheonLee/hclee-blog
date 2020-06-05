package com.hyecheon.blogbackend.model

import com.hyecheon.blogbackend.security.JWT
import com.hyecheon.blogbackend.utils.encryptPassword
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime

@Document(collection = "users")
data class User(
		@MongoId
		var id: String = "",
		var username: String = "",
		@Indexed(unique = true)
		var name: String = "",
		@Indexed(unique = true)
		var email: String = "",
		var profile: String = "",
		var hashedPassword: String = "",
		var salt: String = "",
		var role: Int = -1,
		var photo: String = "",
		var resetPasswordLink: String = ""
) {


	var loginCount: Long = 0

	@Transient
	var loginTime: LocalDateTime = LocalDateTime.now()

	fun afterLoginSuccess(): Long {
		this.loginTime = LocalDateTime.now()
		return ++loginCount
	}

	fun generateToken(jwt: JWT) = let {
		jwt.generateToken(mutableMapOf("userame" to username, "email" to email, "roles" to role))
	}

	fun checkPassword(password: String) = let {
		encryptPassword(password, salt) == hashedPassword
	}
}