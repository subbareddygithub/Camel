package com.ibm;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = MySpringBootApplication.class, properties = {"local.server.port=8080"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CamelSpringBootTest
public class ApplicationTestByRestTemplate {


	final String apiBasePath = "http://localhost:%s/api/users/%s";
	
	@LocalServerPort
    private int serverPort;

	@Autowired
    private TestRestTemplate testRestTemplate;

	@Test
    void givenValidId_WillReturnUserData() {
        Integer userId = 3;
        String uri = format(apiBasePath, serverPort, userId);
		User returnedUser = testRestTemplate.getForObject(uri, User.class);
        User mockUser = new User(3, "Sonny Rollins");

        assertNotNull(mockUser);
        assertNotNull(returnedUser);
        assertEquals(userId, returnedUser.getId());
		assertEquals(mockUser.toString(), returnedUser.toString());
    }

	@Test
    void givenInvalidId_WillReturn404() {
        Integer userId = 5;
        String uri = format(apiBasePath, serverPort, userId);
        ResponseEntity<Object> returnedUser = testRestTemplate.getForEntity(uri, Object.class);
        assertNotNull(returnedUser);
        assertEquals(404, returnedUser.getStatusCode().value());
    }

	@Test
    void givenNone_WillReturnAllUserData() {
        String uri = format(apiBasePath, serverPort, "");
		User[] returnedUserCollection = testRestTemplate.getForObject(uri, User[].class);
		
        assertNotNull(returnedUserCollection);
		assertEquals(3, returnedUserCollection.length);
    }

	@Test
    void givenUserId_WillUpdateUserData() {
		Integer userId = 3;
		User mockUser = new User(3, "Nitin");

        String uri = format(apiBasePath, serverPort, userId);
		testRestTemplate.put(uri, mockUser);
		
		User returnedUser = testRestTemplate.getForObject(uri, User.class);

        assertNotNull(mockUser);
        assertNotNull(returnedUser);
        //assertEquals(userId, returnedUser.getId());
		assertEquals(mockUser.toString(), returnedUser.toString());
	}
}
