package com.goplaces.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class UserBackDTO {
	private String email;
	private String profileImagePath;
	private String userDescription;
}
