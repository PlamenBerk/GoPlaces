package com.goplaces.mapper.impl;

import org.springframework.stereotype.Component;

import com.goplaces.dto.UserDTO;
import com.goplaces.mapper.IUserMapper;
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

}