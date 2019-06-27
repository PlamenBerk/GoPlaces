package com.goplaces.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goplaces.dto.FollowerDTO;
import com.goplaces.dto.UserBackDTOFollow;
import com.goplaces.dto.UserDTO;
import com.goplaces.model.User;
import com.goplaces.service.UserService;

@RestController
@RequestMapping("/user-management")
public class UserController {

	@Autowired
	private UserService userService;

	private final Logger performanceLogger = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	private final Logger businessLogger = LoggerFactory
			.getLogger("business." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@RequestMapping(value = "/user/followings", method = RequestMethod.GET)
	public ResponseEntity<?> listAllUserWithFollowingsPaging(@RequestParam("page") int page,
			@RequestParam("size") int size) throws Exception {
		return new ResponseEntity<List<UserBackDTOFollow>>(userService.findAllUserWithFollowingPage(page, size),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUserPaging(@RequestParam("page") int page, @RequestParam("size") int size)
			throws Exception {

		businessLogger.info("Received request to get users for page {} and size {}", page, size);

		long startTime = System.currentTimeMillis();
		List<User> response = userService.findAllUserPage(page, size);
		long endTime = System.currentTimeMillis();

		performanceLogger.info("Get all user paging took " + (endTime - startTime) + " ms");
		businessLogger.info("Returning get all user paging response: " + response);

		return new ResponseEntity<List<User>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addNewUser(@RequestBody UserDTO userDTO) throws Exception {

		businessLogger.info("Received add new user request {}", userDTO);

		long startTime = System.currentTimeMillis();
		User response = userService.registerUser(userDTO);
		long endTime = System.currentTimeMillis();

		performanceLogger.info("Add new user took " + (endTime - startTime) + " ms");
		businessLogger.info("Returning add new user response: " + response);

		return new ResponseEntity<User>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Integer id)
			throws Exception {

		businessLogger.info("Received request to add new user {}", userDTO);

		long startTime = System.currentTimeMillis();
		User response = userService.updateUser(userDTO, id);
		long endTime = System.currentTimeMillis();

		performanceLogger.info("Update user took " + (endTime - startTime) + " ms");
		businessLogger.info("Update user response: " + response);

		return new ResponseEntity<User>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFollowingForUser(@RequestBody FollowerDTO followerDTO) throws Exception {

		businessLogger.info("Received request to add following for user {}", followerDTO);

		long startTime = System.currentTimeMillis();
		User response = userService.updateFollowing(followerDTO);
		long endTime = System.currentTimeMillis();

		performanceLogger.info("Add following for user took " + (endTime - startTime) + " ms");
		businessLogger.info("Add following for user response: " + response);

		return new ResponseEntity<User>(response, HttpStatus.OK);
	}

}
