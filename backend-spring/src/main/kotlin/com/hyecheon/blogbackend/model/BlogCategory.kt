package com.hyecheon.blogbackend.model

import javax.persistence.*

@Entity
data class BlogCategory(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long? = null,
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "blog_id")
		var blog: Blog,
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "category_id")
		var category: Category

) : BaseEntity() {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is BlogCategory) return false

		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		return id?.hashCode() ?: 0
	}

	override fun toString(): String {
		return "BlogCategory(id=$id)"
	}
}