package com.th.feleck.common.configuration

import JwtTokenFilter
import com.th.feleck.common.exception.CustomAuthenticationEntryPoint
import com.th.feleck.user.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class AuthenticationConfig(
    private val userSerivce: UserService
) {


    @Value("\${jwt.secret-key}")
    private lateinit var key: String

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        return http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/api/*/users/sign-up", "/api/*/users/login").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(JwtTokenFilter(key, userSerivce), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling{
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint())
//                it.accessDeniedHandler(accessDeniedHandler)
            }
            .build()
    }
}