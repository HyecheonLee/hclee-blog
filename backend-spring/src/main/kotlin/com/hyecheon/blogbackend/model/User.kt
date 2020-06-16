package com.hyecheon.blogbackend.model

import com.hyecheon.blogbackend.security.jwt.JWT
import com.hyecheon.blogbackend.utils.encryptPassword
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long? = null,
		var username: String = "",
		var name: String = "",
		@Column(unique = true)
		var email: String = "",
		var profile: String = "",
		var hashedPassword: String = "",
		var salt: String = "",
		var role: String = "ROLE_USER",
		var photo: String = "",
		var resetPasswordLink: String = "",
		var loginCount: Long = 0,
		var loginTime: LocalDateTime = LocalDateTime.now(),
		@OneToMany(mappedBy = "postedBy", fetch = FetchType.LAZY)
		var blog: MutableList<Blog> = mutableListOf()) : BaseEntity() {

	fun afterLoginSuccess(): Long {
		this.loginTime = LocalDateTime.now()
		return ++loginCount
	}

	fun generateToken(jwt: JWT) = let {
		jwt.generateToken(mutableMapOf(
				"id" to id!!,
				"username" to username,
				"name" to name,
				"email" to email,
				"profile" to profile,
				"role" to role,
				"photo" to photo,
				"loginCount" to loginCount,
				"loginTime" to loginTime.toString(),
				"roles" to AuthorityUtils.createAuthorityList(role)))
	}

	fun checkPassword(password: String) = let { encryptPassword(password, salt) == hashedPassword }

	@Transient
	fun getAuthorities(): MutableList<GrantedAuthority> = let { AuthorityUtils.createAuthorityList(role) }
}