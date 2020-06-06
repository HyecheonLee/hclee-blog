package com.hyecheon.blogbackend.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val CLIENT_HOST = "http://localhost:3000"

fun encryptPassword(data: String, key: String): String {
	val signingKey = SecretKeySpec(key.toByteArray(), "HmacSHA1")
	val mac = Mac.getInstance("HmacSHA1")
	mac.init(signingKey)
	return mac.doFinal(data.toByteArray()).joinToString("") { "%02x".format(it) }
}

fun shortUUID(): String {
	val uuid = UUID.randomUUID()
	val l = ByteBuffer.wrap(uuid.toString().toByteArray()).long
	return l.toString(Character.MAX_RADIX)
}

interface Log {
	val log: Logger get() = LoggerFactory.getLogger(this.javaClass)
}
