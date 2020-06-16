package com.hyecheon.blogbackend.model

import javax.persistence.*

@Entity
data class Blog(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long? = null,
		var title: String = "",
		var slug: String = "",
		@Lob
		var body: String = "",
		var excerpt: String = "",
		var mtitle: String = "",
		var mdesc: String = "",
		@OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
		var photo: Photo? = null,
		@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE])
		var blogCategories: MutableSet<BlogCategory> = mutableSetOf(),
		@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE])
		var blogTags: MutableSet<BlogTag> = mutableSetOf(),
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id")
		var postedBy: User? = null
) : BaseEntity() {
	fun addTag(tag: Tag) {
		blogTags.add(BlogTag(blog = this, tag = tag))
	}

	fun addCategory(category: Category) {
		blogCategories.add(BlogCategory(blog = this, category = category))
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Blog) return false

		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		return id?.hashCode() ?: 0
	}


}

@Entity
data class Photo(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long? = null,
		var contentType: String = "",
		var data: ByteArray = byteArrayOf()) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Photo) return false

		if (id != other.id) return false
		if (contentType != other.contentType) return false
		if (!data.contentEquals(other.data)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id?.hashCode() ?: 0
		result = 31 * result + contentType.hashCode()
		result = 31 * result + data.contentHashCode()
		return result
	}

}