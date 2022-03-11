package com.serrano.app.forum.enums;

import java.util.Arrays;

import com.serrano.app.forum.exception.VoteNotFoundException;

public enum VoteType {
	UP(1),
	DOWN(-1);
	
	private int direction;
	
	VoteType(int direction){}
	
	public static VoteType lookup(Integer direction) {
		return Arrays.stream(VoteType.values())
				.filter(value -> value.getDirection().equals(direction))
				.findAny()
				.orElseThrow(() -> new VoteNotFoundException(direction));
	}
	
	private Integer getDirection() {
		return direction;
	}
}
