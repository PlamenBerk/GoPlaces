package com.goplaces.mapper;

import com.goplaces.dto.CreateUserDTO;
import com.goplaces.model.User;

public interface IUserMapper {
	User userDTOtoUser(CreateUserDTO userDTO);
}
