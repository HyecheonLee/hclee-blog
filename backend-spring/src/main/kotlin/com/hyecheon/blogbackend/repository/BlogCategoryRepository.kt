package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.BlogCategory
import org.springframework.data.jpa.repository.JpaRepository

interface BlogCategoryRepository : JpaRepository<BlogCategory, Long>