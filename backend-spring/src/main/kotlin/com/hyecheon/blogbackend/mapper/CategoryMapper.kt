package com.hyecheon.blogbackend.mapper

import com.hyecheon.blogbackend.model.Category
import com.hyecheon.blogbackend.web.dto.CategoryReqDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryMapper {
	fun toModel(categoryReqDto: CategoryReqDto): Category
}