package com.goplaces.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goplaces.dto.CreateUserDTO;
import com.goplaces.exception.UserException;
import com.goplaces.mapper.IUserMapper;
import com.goplaces.model.User;

@Service
@Transactional
public class UserService extends BaseService {

	@Autowired
	private IUserMapper userMapper;

	public User registerUser(CreateUserDTO userDTO) throws UserException {
		User user = userMapper.userDTOtoUser(userDTO);
		if (user == null) {
			throw new UserException("cannot create user");
		}
		getEm().persist(user);
		return user;
	}

	public User updateUser(CreateUserDTO userDTO, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
