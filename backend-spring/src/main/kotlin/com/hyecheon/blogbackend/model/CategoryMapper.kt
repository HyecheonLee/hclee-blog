package com.hyecheon.blogbackend.model

import com.hyecheon.blogbackend.web.dto.CategoryReqDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryMapper {
	fun toModel(categoryReqDto: CategoryReqDto): Category
}