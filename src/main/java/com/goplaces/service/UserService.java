package com.goplaces.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goplaces.dto.FollowerDTO;
import com.goplaces.dto.UserBackDTOFollow;
import com.goplaces.dto.UserBackDTOPlaces;
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

	private final Logger businessLogger = LoggerFactory
			.getLogger("business." + MethodHandles.lookup().lookupClass().getCanonicalName());

	private final Logger technicalLogger = LoggerFactory
			.getLogger("technical." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Cacheable("users")
	public List<User> findAllUserPage(int page, int size) throws Exception {
		List<User> usersList = null;

		try {
			businessLogger.info("Finding users for page: " + page + " and size: " + size);

			Pageable pageableRequest = PageRequest.of(page, size);
			Page<User> users = userRepository.findAll(pageableRequest);
			usersList = users.getContent();

			businessLogger.info("Found users for page: " + page + " and size: " + size + " :: " + usersList);
		} catch (Exception e) {
			technicalLogger.error(e.getMessage(), e);
			throw new UserException(e.getMessage());
		}

		return usersList;
	}

	// @Cacheable("userFollowing")
	public List<UserBackDTOFollow> findAllUserWithFollowingPage(int page, int size) {
		Pageable pageableRequest = PageRequest.of(page, size);
		Page<User> users = userRepository.findAll(pageableRequest);
		List<User> usersList = users.getContent();

		List<UserBackDTOFollow> userDTOFollow = new ArrayList<>();
		for (User u : usersList) {
			userDTOFollow.add(userMapper.userToUserDTOFollow(u));
		}

		return userDTOFollow;
	}

	// @Cacheable("usersPlaces")
	public List<UserBackDTOPlaces> findAllUserWithPlacesPage(int page, int size) {
		Pageable pageableRequest = PageRequest.of(page, size);
		Page<User> users = userRepository.findAll(pageableRequest);
		List<User> usersList = users.getContent();

		List<UserBackDTOPlaces> userDTOPlace = new ArrayList<>();
		for (User u : usersList) {
			userDTOPlace.add(userMapper.userToUserDTOPlace(u));
		}

		return userDTOPlace;
	}

	public User registerUser(UserDTO userDTO) throws UserException, IOException {
		User user = userMapper.userDTOtoUser(userDTO);

		if (user == null) {
			throw new UserException("cannot create user");
		}

		setUserProfileImage(userDTO, user);
		User u = null;
		try {
			u = userRepository.save(user);
			businessLogger.info("Registering user: " + u);
		} catch (Exception e) {
			technicalLogger.error(e.getMessage(), e);
			throw new UserException(e.getMessage());
		}
		return u;
	}

	public User updateUser(UserDTO userDTO, Integer id) throws UserException, IOException {
		User managedUser = null;
		try {
			managedUser = userRepository.findById(id).get();
			if (managedUser == null || userDTO == null) {
				throw new UserException("cannot update user");
			}
			setUserProfileImage(userDTO, managedUser);
			managedUser.setUserDescription(userDTO.getUserDescription());
			managedUser.setProfileImagePath(userDTO.getProfileImagePath());

			businessLogger.info("Updating user: " + managedUser);
		} catch (Exception e) {
			technicalLogger.error(e.getMessage(), e);
			throw new UserException(e.getMessage());
		}
		return managedUser;
	}

	public User updateFollowing(FollowerDTO followerDTO) throws UserException, NoResultException {
		User currentUser = null;
		try {
			currentUser = userRepository.findById(followerDTO.getCurrentUserId()).get();
		} catch (Exception e) {
			technicalLogger.error(e.getMessage(), e);
			throw new UserException(e.getMessage());
		}

		User followedUser = null;

		try {
			followedUser = userRepository.findUserByEmail(followerDTO.getRequestedEmailForFollow());
		} catch (Exception e) {
			technicalLogger.error(e.getMessage(), e);
			throw new UserException("Cannot find user with email " + followerDTO.getRequestedEmailForFollow());
		}

		if (followerDTO.isFollowing()) {
			currentUser.getFollow().add(followedUser);
			businessLogger.info("User: " + currentUser + " following " + followedUser);
			return currentUser;
		} else {
			if (currentUser.getFollow().contains(followedUser)) {
				currentUser.getFollow().remove(followedUser);
				businessLogger.info("User: " + currentUser + " unfollowing " + followedUser);
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
