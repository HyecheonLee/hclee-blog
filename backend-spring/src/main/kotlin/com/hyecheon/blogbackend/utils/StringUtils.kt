package com.hyecheon.blogbackend.utils


fun String.toSlug() = toLowerCase()
		.replace("\n", " ")
		.replace("[^a-z\\d\\s]".toRegex(), " ")
		.split(" ")
		.joinToString("-")
		.replace("-+".toRegex(), "-")