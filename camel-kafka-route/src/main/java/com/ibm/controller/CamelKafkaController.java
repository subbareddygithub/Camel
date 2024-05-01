package com.ibm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.model.User;
import com.ibm.service.UserService;

@RestController
@RequestMapping("/api/user")
public class CamelKafkaController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User execute(@RequestBody User user) {
		
		User response = userService.execute(user);
		
		return response;
	}

}
