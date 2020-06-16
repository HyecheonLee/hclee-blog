package com.hyecheon.blogbackend.model

import javax.persistence.*

@Entity
data class Category(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long? = null,
		var name: String = "",
		@Column(unique = true)
		var slug: String = "",
		@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
		var blogTags: MutableList<BlogCategory> = mutableListOf()
) : BaseEntity() {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Category) return false

		if (id != other.id) return false
		if (name != other.name) return false
		if (slug != other.slug) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id?.hashCode() ?: 0
		result = 31 * result + name.hashCode()
		result = 31 * result + slug.hashCode()
		return result
	}

	override fun toString(): String {
		return "Category(id=$id, name='$name', slug='$slug')"
	}
}

