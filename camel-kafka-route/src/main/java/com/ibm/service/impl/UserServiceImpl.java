package com.ibm.service.impl;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.model.User;
import com.ibm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	ProducerTemplate template;

	@Override
	public User execute(User user) {
		
		template.sendBody("direct:hello", user.toString());

		return user;
	}

}
