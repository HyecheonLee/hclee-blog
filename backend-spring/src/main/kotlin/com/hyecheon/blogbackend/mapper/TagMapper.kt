package com.hyecheon.blogbackend.mapper

import com.hyecheon.blogbackend.model.Tag
import com.hyecheon.blogbackend.web.dto.TagReqDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface TagMapper {
	fun toModel(tagReqDto: TagReqDto): Tag
}