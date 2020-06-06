package com.hyecheon.blogbackend.security

import com.hyecheon.blogbackend.security.jwt.JwtAccessDeniedHandler
import com.hyecheon.blogbackend.security.jwt.JwtAuthenticationTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
		@Value("\${jwt.token.role}")
		val role: String,
		val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
		val userAuthenticationProvider: UserAuthenticationProvider,
		val entryPointUnauthorizedHandler: EntryPointUnauthorizedHandler) : WebSecurityConfigurerAdapter() {

	@Autowired
	fun configureAuthentication(authenticationManagerBuilder: AuthenticationManagerBuilder) {
		authenticationManagerBuilder
				.authenticationProvider(userAuthenticationProvider)
	}

	@Bean
	override fun authenticationManagerBean(): AuthenticationManager {
		return super.authenticationManagerBean()
	}

	@Bean
	fun jwtAuthenticationTokenFilterBean(): JwtAuthenticationTokenFilter {
		return JwtAuthenticationTokenFilter()
	}


	override fun configure(http: HttpSecurity) {
		http.csrf()
				.disable()
				.headers()
				.disable()
				.exceptionHandling()
				.accessDeniedHandler(jwtAccessDeniedHandler)
				.authenticationEntryPoint(entryPointUnauthorizedHandler)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/signup").permitAll()
				.antMatchers("/api/signin").permitAll()
				.antMatchers("/api/**").hasRole(role)
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.disable()
		http.addFilterBefore(jwtAuthenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter::class.java)
	}
}