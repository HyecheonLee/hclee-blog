package com.hyecheon.blogbackend.model

import com.hyecheon.blogbackend.web.dto.UserSignupReqDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserMapper {
	@Mapping(source = "password", target = "hashedPassword")
	fun toModel(userSignupReqDto: UserSignupReqDto): User
}