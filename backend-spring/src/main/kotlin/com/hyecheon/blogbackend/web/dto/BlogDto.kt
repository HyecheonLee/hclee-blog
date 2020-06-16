package com.hyecheon.blogbackend.web.dto

import com.hyecheon.blogbackend.model.Blog
import com.hyecheon.blogbackend.model.Category
import com.hyecheon.blogbackend.model.Photo
import com.hyecheon.blogbackend.model.Tag


data class BlogReqDto(
		var id: Long? = null,
		var title: String,
		var body: String,
		var categories: MutableList<Long> = mutableListOf(),
		var tags: MutableList<Long> = mutableListOf()) {
	fun toModel(photo: Photo): Blog {
		val blog = Blog()
		if (id != null) {
			blog.id = id
		}
		blog.title = title
		blog.body = body
		tags.forEach { id ->
			blog.addTag(Tag(id = id))
		}
		categories.forEach { id ->
			blog.addCategory(Category(id = id))
		}
		blog.photo = photo
		return blog
	}
}

data class BlogResDto(
		val id: Long,
		val title: String = "",
		val body: String = "",
		val categories: MutableList<CategoryResDto> = mutableListOf(),
		val tags: MutableList<TagResDto> = mutableListOf(),
		val photo: Photo? = null) {
	companion object {
		fun fromModel(blog: Blog): BlogResDto {
			val categoryResDtoList = blog.blogCategories.map { blogCategory -> CategoryResDto(blogCategory.category.name) }.toMutableList()
			val tagResDtoList = blog.blogTags.map { blogCategory -> TagResDto(blogCategory.tag.name) }.toMutableList()
			return BlogResDto(id = blog.id!!, title = blog.title, body = blog.title, categories = categoryResDtoList, tags = tagResDtoList, photo = blog.photo)
		}
	}
}