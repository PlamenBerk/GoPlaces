package com.goplaces.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goplaces.dto.FollowerDTO;
import com.goplaces.dto.UserDTO;
import com.goplaces.exception.UserException;
import com.goplaces.mapper.IUserMapper;
import com.goplaces.model.User;
import com.goplaces.repository.IUserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private IUserMapper userMapper;

	@Autowired
	private IUserRepository userRepository;

	@Cacheable("users")
	public List<User> findAllUserPage(int page, int size) throws Exception {
		Pageable pageableRequest = PageRequest.of(page, size);
		Page<User> users = userRepository.findAll(pageableRequest);
		List<User> usersList = users.getContent();

		return usersList;
	}

	public User registerUser(UserDTO userDTO) throws UserException, IOException {
		User user = userMapper.userDTOtoUser(userDTO);

		setUserProfileImage(userDTO, user);

		if (user == null) {
			throw new UserException("cannot create user");
		}

		return userRepository.save(user);
	}

	public User updateUser(UserDTO userDTO, Integer id) throws UserException, IOException {
		User managedUser = userRepository.findById(id).get();

		setUserProfileImage(userDTO, managedUser);

		if (managedUser == null || userDTO == null) {
			throw new UserException("cannot update user");
		}

		managedUser.setUserDescription(userDTO.getUserDescription());
		managedUser.setProfileImagePath(userDTO.getProfileImagePath());

		return managedUser;
	}

	public User updateFollowing(FollowerDTO followerDTO) throws UserException, NoResultException {
		User currentUser = userRepository.findById(followerDTO.getCurrentUserId()).get();
		User followedUser = null;

		try {
			followedUser = userRepository.findUserByEmail(followerDTO.getRequestedEmailForFollow());
		} catch (Exception e) {
			throw new UserException("Cannot find user with email " + followerDTO.getRequestedEmailForFollow());
		}

		if (currentUser == null) {
			throw new UserException("Problem with current user");
		}

		if (followerDTO.isFollowing()) {
			currentUser.setFollow(followedUser);
			return currentUser;
		} else {
			if (currentUser.getFollow().contains(followedUser)) {
				currentUser.getFollow().remove(followedUser);
			}
			return currentUser;
		}

	}

	private void setUserProfileImage(UserDTO userDTO, User user) throws IOException {
		if (!(userDTO.getProfileImageBase64().trim().isEmpty()) && !(userDTO.getProfileImageBase64().trim().equals(""))
				&& userDTO.getProfileImageBase64() != null) {

			byte[] decodedBytes = Base64.getDecoder().decode(userDTO.getProfileImageBase64());
			ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
			BufferedImage bImage2 = ImageIO.read(bis);
			String imageName = user.getEmail() + "_" + UUID.randomUUID().toString();
			ImageIO.write(bImage2, "jpg", new File("D://UserProfileImages/" + imageName + ".jpg"));
			user.setProfileImagePath(imageName);

		}
	}

}
