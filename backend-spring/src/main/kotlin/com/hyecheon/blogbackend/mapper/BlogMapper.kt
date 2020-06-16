package com.hyecheon.blogbackend.mapper

import com.hyecheon.blogbackend.model.Blog
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface BlogMapper {
	fun blogReqDto(blog: Blog)
}