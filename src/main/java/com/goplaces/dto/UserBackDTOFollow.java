package com.goplaces.dto;

import java.util.HashSet;
import java.util.Set;

import com.goplaces.model.User;

public class UserBackDTOFollow extends UserBackDTO {
	private Set<User> follow = new HashSet<>();

	public Set<User> getFollow() {
		return follow;
	}

	public void setFollow(User follow) {
		this.follow.add(follow);
	}
}
