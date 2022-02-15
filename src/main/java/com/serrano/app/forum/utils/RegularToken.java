package com.serrano.app.forum.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.domain.VerificationToken;

public class RegularToken {

	private static final ChronoUnit DEFAULT_UNIT = ChronoUnit.MINUTES;
	
	public static VerificationToken create(User user, long expiratesInMinutes) {
		String token = UUID.randomUUID().toString();
		Instant currentTime = Instant.now();
		VerificationToken verificationToken = VerificationToken.builder()
				.token(token)
				.created_at(currentTime)
				.expires_at(currentTime.plus(expiratesInMinutes, DEFAULT_UNIT))
				.user(user)
				.build();
		return verificationToken;
	}
}
