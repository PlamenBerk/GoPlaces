package com.goplaces.mapper;

import com.goplaces.dto.UserBackDTOFollow;
import com.goplaces.dto.UserBackDTOPlaces;
import com.goplaces.dto.UserDTO;
import com.goplaces.model.User;

public interface IUserMapper {
	User userDTOtoUser(UserDTO userDTO);

	UserBackDTOFollow userToUserDTOFollow(User user);

	UserBackDTOPlaces userToUserDTOPlace(User user);
}
