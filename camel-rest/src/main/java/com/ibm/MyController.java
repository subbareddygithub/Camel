package com.ibm;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//@RequestMapping
public class MyController {

	/*@Autowired
	ProducerTemplate template;
	
	@PostMapping("/user")
	public String execute(@RequestBody User user) {
		
		template.sendBody("direct:create-user", user.toString());
		return "triggred";
	}*/
}
