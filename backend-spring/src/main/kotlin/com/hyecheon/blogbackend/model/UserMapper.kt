package com.hyecheon.blogbackend.model

import com.hyecheon.blogbackend.web.dto.UserProfileResDto
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

	fun toUserProfileDto(user: User): UserProfileResDto
}