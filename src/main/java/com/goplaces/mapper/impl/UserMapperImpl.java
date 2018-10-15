package com.goplaces.mapper.impl;

import org.springframework.stereotype.Component;

import com.goplaces.dto.UserBackDTOFollow;
import com.goplaces.dto.UserBackDTOPlaces;
import com.goplaces.dto.UserDTO;
import com.goplaces.mapper.IUserMapper;
import com.goplaces.model.Place;
import com.goplaces.model.User;

@Component
public class UserMapperImpl implements IUserMapper {

	@Override
	public User userDTOtoUser(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}

		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setProfileImagePath(userDTO.getProfileImagePath());
		user.setUserDescription(userDTO.getUserDescription());

		return user;
	}

	@Override
	public UserBackDTOFollow userToUserDTOFollow(User user) {
		if (user == null) {
			return null;
		}

		UserBackDTOFollow userBack = new UserBackDTOFollow();
		userBack.setEmail(user.getEmail());
		userBack.setProfileImagePath(user.getProfileImagePath());
		userBack.setUserDescription(user.getUserDescription());
		for (User u : user.getFollow()) {
			userBack.setFollow(u);
		}
		return userBack;
	}

	@Override
	public UserBackDTOPlaces userToUserDTOPlace(User user) {
		if (user == null) {
			return null;
		}

		UserBackDTOPlaces userBack = new UserBackDTOPlaces();
		userBack.setEmail(user.getEmail());
		userBack.setProfileImagePath(user.getProfileImagePath());
		userBack.setUserDescription(user.getUserDescription());
		for (Place pl : user.getPlaces()) {
			userBack.setPlaces(pl);
		}
		return userBack;
	}

}
