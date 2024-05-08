package com.th.feleck.common.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.*

object JwtTokenUtils {


    fun getUserName(token: String, key: String): String {
        return extractClaims(token, key).get("userName", String::class.java)
    }

    fun isExpired(token: String, key: String): Boolean {
        val expiredDate = extractClaims(token, key).expiration
        return expiredDate.before(Date())
    }

    fun extractClaims(token: String, key: String): Claims{
        return Jwts.parser().setSigningKey(getKey(key)).build().parseClaimsJws(token).body
    }

    fun generateToken(userName: String, key: String, expiredTimeMs: Long): String {
        val claims = mutableMapOf<String, String>()
        claims["userName"] = userName

        return Jwts.builder()
            .claims(claims) // 사용자 이름을 주제로 설정
            .issuedAt(Date()) // 토큰 발급 시간 설정
            .expiration(Date(System.currentTimeMillis() + expiredTimeMs)) // 만료 시간 설정
            .signWith(getKey(key), SignatureAlgorithm.HS256) // 서명 알고리즘과 키 지정
            .compact()
    }

    fun getKey(key: String) : Key {
        val keyBytes = key.toByteArray()
        return Keys.hmacShaKeyFor(keyBytes)
    }
}