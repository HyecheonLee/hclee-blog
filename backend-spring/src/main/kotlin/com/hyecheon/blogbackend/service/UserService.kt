package com.hyecheon.blogbackend.service

import com.hyecheon.blogbackend.model.User
import com.hyecheon.blogbackend.repository.UserRepository
import com.hyecheon.blogbackend.utils.CLIENT_HOST
import com.hyecheon.blogbackend.utils.encryptPassword
import com.hyecheon.blogbackend.utils.shortUUID
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.roundToInt

@Service
class UserService(
		val userRepository: UserRepository) {

	fun createUser(user: User): User {
		if (user.email == null) {
			throw IllegalArgumentException("email not found")
		}
		if (userRepository.findByEmail(user.email!!).isPresent) {
			throw IllegalArgumentException("Email is taken")
		}
		val salt = Date().time.times(Math.random()).roundToInt().toString()
		user.salt = salt
		user.hashedPassword?.let {
			user.hashedPassword = encryptPassword(it, salt)
		}
		user.username = shortUUID()
		user.profile = "${CLIENT_HOST}/profile/${user.username}"
		return userRepository.save(user)
	}
}