package com.hyecheon.blogbackend.security

import com.hyecheon.blogbackend.security.jwt.JwtAuthentication
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class UserAuthenticationToken : AbstractAuthenticationToken {
	private val principal: JwtAuthentication
	private var credentials: String?

	constructor(principal: JwtAuthentication, credentials: String = "", authorities: Collection<GrantedAuthority>) : super(authorities) {
		super.setAuthenticated(true)
		this.principal = principal
		this.credentials = credentials
	}

	override fun getCredentials(): String? {
		return credentials
	}

	override fun getPrincipal(): JwtAuthentication {
		return principal
	}

	@Throws(IllegalArgumentException::class)
	override fun setAuthenticated(isAuthenticated: Boolean) {
		require(!isAuthenticated) { "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead" }
		super.setAuthenticated(false)
	}

	override fun eraseCredentials() {
		super.eraseCredentials()
		credentials = null
	}
}