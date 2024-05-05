package com.example.security.global.security.provider;

import com.example.security.global.common.exception.BaseException;
import com.example.security.global.common.response.BaseResponseStatus;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Slf4j
@NoArgsConstructor
public class TokenProvider {
	private static final String secretKey = "ThisIsA_SecretKeyForJwtExamplewefhluaweflkawfhaklwfhaw";
	private static final String HEADER = "JWT_TOKEN";
	private static final Long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 12L;
	private static final Long REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 14 * 1L;

	public String[] generateTokens(Long userId) {
		String accessToken = createAccessToken(userId);
		String refreshToken = createRefreshToken(userId);
		return new String[] {accessToken, refreshToken};
	}

	private String createAccessToken(Long userId) {
		return createJwt(userId, ACCESS_TOKEN_EXPIRED_TIME);
	}

	private String createRefreshToken(Long userId) {
		return createJwt(userId, REFRESH_TOKEN_EXPIRED_TIME);
	}

	private String createJwt(Long userId, Long tokenValid) {
		byte[] keyBytes = Decoders.BASE64.decode(getSecretKey());
		Key key = Keys.hmacShaKeyFor(keyBytes);

		return Jwts.builder()
			.signWith(key)
			.claim("userId", userId)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + tokenValid))
			.compact();
	}

	public String getAccessToken(HttpServletRequest request) throws BaseException {
		String accessToken = request.getHeader(HEADER);

		if (accessToken == null || accessToken.length() == 0) {
			throw new BaseException(BaseResponseStatus.EMPTY_ACCESS_KEY);
		}
		return accessToken;
	}

	public Long resolveToken(String accessToken) throws BaseException {
		return Optional.ofNullable(Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(accessToken)
				.getBody())
			.map((c) -> c.get("userId", Long.class))
			.orElseThrow(() -> new BaseException(BaseResponseStatus.EMPTY_ACCESS_KEY));
	}

	public void validateToken(String jwtToken) {
		if (jwtToken == null || jwtToken.isBlank()) {
			throw new BaseException(BaseResponseStatus.EMPTY_ACCESS_KEY);
		}
		try {
			Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(jwtToken);
		} catch (ExpiredJwtException e) {
			throw new BaseException(BaseResponseStatus.EXPIRATION_TOKEN);
		} catch (UnsupportedJwtException |
				 MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new BaseException(BaseResponseStatus.NOT_VALID_TOKEN);
		}
	}

	private String getSecretKey() {
		String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
		return secretKeyEncodeBase64;
	}
}
