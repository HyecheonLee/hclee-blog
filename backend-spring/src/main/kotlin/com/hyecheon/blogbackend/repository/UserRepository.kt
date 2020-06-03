package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User, String> {
	fun findByEmail(email: String): Optional<User>
}