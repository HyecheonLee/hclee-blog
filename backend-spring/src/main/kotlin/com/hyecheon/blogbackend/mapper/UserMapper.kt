package com.hyecheon.blogbackend.mapper

import com.hyecheon.blogbackend.model.User
import com.hyecheon.blogbackend.web.dto.UserProfileDto
import com.hyecheon.blogbackend.web.dto.UserSignInDto
import com.hyecheon.blogbackend.web.dto.UserSignUpReqDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserMapper {
	@Mapping(source = "password", target = "hashedPassword")
	fun toModel(userSignUpReqDto: UserSignUpReqDto): User

	@Mapping(source = "password", target = "hashedPassword")
	fun toModel(userSignInDto: UserSignInDto): User

	fun toUserProfileDto(user: User): UserProfileDto
}