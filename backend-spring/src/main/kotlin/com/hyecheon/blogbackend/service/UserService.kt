package com.hyecheon.blogbackend.service

import com.hyecheon.blogbackend.model.User
import com.hyecheon.blogbackend.repository.UserRepository
import com.hyecheon.blogbackend.security.jwt.JWT
import com.hyecheon.blogbackend.utils.CLIENT_HOST
import com.hyecheon.blogbackend.utils.encryptPassword
import com.hyecheon.blogbackend.utils.shortUUID
import com.hyecheon.blogbackend.web.dto.UserReqDto
import com.hyecheon.blogbackend.web.dto.UserReqWithTokenDto
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.roundToInt

@Service
class UserService(
		val userRepository: UserRepository,
		val jwt: JWT) {

	fun createUser(user: User): User {
		if (userRepository.findByEmail(user.email).isPresent) {
			throw IllegalArgumentException("Email is taken")
		}
		val salt = Date().time.times(Math.random()).roundToInt().toString()
		user.salt = salt
		user.hashedPassword.let {
			user.hashedPassword = encryptPassword(it, salt)
		}
		user.username = shortUUID()
		user.profile = "${CLIENT_HOST}/profile/${user.username}"
		return userRepository.save(user)
	}

	fun login(email: String, password: String): UserReqWithTokenDto {
		val loggedUser = userRepository.findByEmail(email).orElseThrow { throw IllegalArgumentException("User with that email does not exist. Pleas signup.") }
		if (!loggedUser.checkPassword(password)) {
			throw IllegalArgumentException("Bad credential")
		}
		loggedUser.afterLoginSuccess()
		return UserReqWithTokenDto(loggedUser.generateToken(jwt),
				UserReqDto(loggedUser.username, loggedUser.name, loggedUser.email, loggedUser.role))
	}
}