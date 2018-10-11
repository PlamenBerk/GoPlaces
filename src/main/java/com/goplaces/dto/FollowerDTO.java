package com.goplaces.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class FollowerDTO {
	private Integer currentUserId;
	private String requestedEmailForFollow;
	private boolean following;
}
