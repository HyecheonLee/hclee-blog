package com.hyecheon.blogbackend.web.dto

data class PhotoResDto(
		val id: Long,
		val contentType: String = "",
		val data: ByteArray = byteArrayOf()) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is PhotoResDto) return false

		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}
}