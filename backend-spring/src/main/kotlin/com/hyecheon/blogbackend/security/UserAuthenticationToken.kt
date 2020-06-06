package com.hyecheon.blogbackend.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class UserAuthenticationToken : AbstractAuthenticationToken {
	private val principal: Any
	private var credentials: String?

	constructor(principal: Any, credentials: String) : super(null) {
		super.setAuthenticated(false)
		this.principal = principal
		this.credentials = credentials
	}

	constructor(principal: Any, credentials: String = "", authorities: Collection<GrantedAuthority>) : super(authorities) {
		super.setAuthenticated(true)
		this.principal = principal
		this.credentials = credentials
	}

	override fun getCredentials(): String? {
		return credentials
	}

	override fun getPrincipal(): Any {
		return principal
	}

	@Throws(IllegalArgumentException::class)
	override fun setAuthenticated(isAuthenticated: Boolean) {
		require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
		super.setAuthenticated(false)
	}

	override fun eraseCredentials() {
		super.eraseCredentials()
		credentials = ""
	}
}