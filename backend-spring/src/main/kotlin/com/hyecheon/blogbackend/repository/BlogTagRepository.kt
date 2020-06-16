package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.BlogTag
import org.springframework.data.jpa.repository.JpaRepository

interface BlogTagRepository : JpaRepository<BlogTag, Long>