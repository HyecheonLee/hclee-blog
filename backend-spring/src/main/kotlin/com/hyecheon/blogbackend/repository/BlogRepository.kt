package com.hyecheon.blogbackend.repository

import com.hyecheon.blogbackend.model.Blog
import org.springframework.data.jpa.repository.JpaRepository

interface BlogRepository : JpaRepository<Blog, Long>