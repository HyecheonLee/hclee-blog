package com.hyecheon.blogbackend.model

import com.hyecheon.blogbackend.security.jwt.JWT
import com.hyecheon.blogbackend.utils.encryptPassword
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import java.time.LocalDateTime

@Document(collection = "users")
data class User(
		@MongoId
		var id: String? = null,
		var username: String = "",
		var name: String = "",
		@Indexed(unique = true)
		var email: String = "",
		var profile: String = "",
		@Field("hashed_password")
		var hashedPassword: String = "",
		var salt: String = "",
		var role: String = "ROLE_USER",
		var photo: String = "",
		@Field("reset_password_link")
		var resetPasswordLink: String = "",
		var loginCount: Long = 0,
		var loginTime: LocalDateTime = LocalDateTime.now()) {

	fun afterLoginSuccess(): Long {
		this.loginTime = LocalDateTime.now()
		return ++loginCount
	}

	fun generateToken(jwt: JWT) = let {
		jwt.generateToken(mutableMapOf("id" to id!!, "email" to email, "roles" to AuthorityUtils.createAuthorityList(role)))
	}

	fun checkPassword(password: String) = let { encryptPassword(password, salt) == hashedPassword }

	fun getAuthorities(): MutableList<GrantedAuthority> = let { AuthorityUtils.createAuthorityList(role) }
}