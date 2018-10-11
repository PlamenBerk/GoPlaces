package com.goplaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goplaces.dto.FollowerDTO;
import com.goplaces.dto.UserDTO;
import com.goplaces.model.User;
import com.goplaces.service.UserService;

@RestController
@RequestMapping("/user-management")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@RequestBody UserDTO userDTO) throws Exception {
		return new ResponseEntity<User>(userService.registerUser(userDTO), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Integer id) throws Exception {
		return new ResponseEntity<User>(userService.updateUser(userDTO, id), HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<?> addFollowingForUser(@RequestBody FollowerDTO followerDTO) throws Exception {
		return new ResponseEntity<User>(userService.updateFollowing(followerDTO), HttpStatus.OK);
	}

}
