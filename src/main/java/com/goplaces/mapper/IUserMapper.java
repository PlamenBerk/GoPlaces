package com.goplaces.mapper;

import com.goplaces.dto.UserDTO;
import com.goplaces.model.User;

public interface IUserMapper {
	User userDTOtoUser(UserDTO userDTO);
}
