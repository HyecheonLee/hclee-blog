package com.hyecheon.blogbackend.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

inline fun seoulDateTime(): ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

inline fun getTimeNow(pattern: String = "yyyy-MM-dd HH:mm:ss"): String = seoulDateTime().format(DateTimeFormatter.ofPattern(pattern))
fun LocalDateTime.getString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String = format(DateTimeFormatter.ofPattern(pattern))
