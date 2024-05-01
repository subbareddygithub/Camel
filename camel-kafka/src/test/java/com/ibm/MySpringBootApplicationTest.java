package com.ibm;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest
@CamelSpringBootTest
public class MySpringBootApplicationTest {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private ProducerTemplate producerTemplate;

	MockEndpoint mockHello;

	    // //Spring context fixtures
		// @Configuration
		// static class TestConfig {
	
		// 	@Bean
		// 	RoutesBuilder route() {
		// 		return new RouteBuilder() {
		// 			@Override
		// 			public void configure() throws Exception {
		// 				from("http://localhost:8080/api/users/3").to("mock:users");
		// 			}
		// 		};
		// 	}
		// }

	// @Configuration
	// public static class TestConfiguration {
	// 	@Bean
	// 	public RouteBuilder routeBuilder() {
	// 		return new RouteBuilder() {
	// 			@Override
	// 			public void configure() throws Exception {
	// 				from("http://localhost:8080/api/users/3").to("mock:users");
	// 			}
	// 		};
	// 	}
	// }

	// @Before
	// public void before() throws Exception {
	// 	//Before is called for each methods, but we only want to run this once as the context is created once for the whole class
	// 	 mockHello = camelContext.getEndpoint("mock:stream:out", MockEndpoint.class);
	// 	if (camelContext.getStatus()==ServiceStatus.Stopped) {
			
	// 		AdviceWith.adviceWith(camelContext, "hello",
	// 			// intercepting an exchange on route
	// 			advice -> {
	// 				// replacing consumer with direct component
	// 				advice.replaceFromWith("direct:start");
	// 				// mocking producer
	// 				advice.mockEndpoints("stream*");
	// 			}
	// 		);
			
	// 		//Manually start context after adviceWith
	// 		camelContext.start();
	// 	} else {
	// 		//If context was already started (there was an earlier test run) then reset mock endpoints otherwise the tests can interfere
	// 		mockHello.reset();
	// 	}
	// }

	/* 
	// Uncomment this working code to ensue all is working
	@Test
	public void test() throws Exception {
		mockHello = camelContext.getEndpoint("mock:stream:out", MockEndpoint.class);

		AdviceWith.adviceWith(camelContext, "hello",
				// intercepting an exchange on route
				r -> {
					// replacing consumer with direct component
					r.replaceFromWith("direct:start");
					// mocking producer
					r.mockEndpoints("stream*");
				}
		);

		// setting expectations
		mockHello.expectedMessageCount(1);
		mockHello.expectedBodiesReceived("Hello World");

		// invoking consumer
		producerTemplate.sendBody("direct:start", null);

		// asserting mock is satisfied
		mockHello.assertIsSatisfied();
	}*/

	// @EndpointInject(uri = "mock:users")
	// protected MockEndpoint mockEndpointUser;

	Integer port=8080;

	// @Test
	// public void testUserPut() throws Exception {
	// 	MockEndpoint mockEndpointUser = camelContext.getEndpoint("mock:users", MockEndpoint.class);
		
	// 	AdviceWith.adviceWith(camelContext, "put-user-by-id",
	// 		// intercepting an exchange on route
	// 		r -> {
	// 			// replacing consumer with direct component
	// 			r.replaceFromWith("direct:rest");
	// 			// mocking producer
	// 			r.mockEndpoints("users*");
	// 		}
	// 	);

	// 	User user = new User(3, "Nitin");

	// 	mockEndpointUser.expectedBodiesReceived(user);
	// 	//mockEndpointUser.expectedBodiesReceived("User successfully updated");
	// 	//mockEndpointUser.expectedMessageCount(1);

	// 	//Send a message to each post endpoint
	// 	ExchangeBuilder builder = ExchangeBuilder.anExchange(camelContext)
	// 		.withHeader(Exchange.HTTP_METHOD, HttpMethod.PUT)
	// 		.withHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON)
	// 		.withHeader(Exchange.ACCEPT_CONTENT_TYPE, MediaType.APPLICATION_JSON);

	// 	// Exchange outExchangeUser = builder.withHeader(Exchange.HTTP_RESPONSE_CODE, 204).withBody("User successfully updated").build();
	// 	Exchange outExchangeUser = builder.withHeader("id", 3)
	// 									  .withBody("{\"id\": 3, \"name\": \"Nitin\"}").build();
	// 	producerTemplate.send("direct:rest", outExchangeUser);

	// 	mockEndpointUser.assertIsSatisfied();
	// }

	@Test
	public void testUserGet() throws Exception {
		MockEndpoint mockEndpointUser = camelContext.getEndpoint("mock:users", MockEndpoint.class);
		
		AdviceWith.adviceWith(camelContext, "get-user-by-id",
			// intercepting an exchange on route
			r -> {
				// replacing consumer with direct component
				r.replaceFromWith("direct:rest-with-get");
				// mocking producer
				r.mockEndpoints("users*");
			}
		);

		User user = new User(3, "Indu");

		mockEndpointUser.expectedBodiesReceived(user);
		//mockEndpointUser.expectedBodiesReceived("User successfully updated");
		//mockEndpointUser.expectedMessageCount(1);

		//Send a message to each post endpoint
		ExchangeBuilder builder = ExchangeBuilder.anExchange(camelContext)
			.withHeader(Exchange.HTTP_METHOD, HttpMethod.PUT)
			.withHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.withHeader(Exchange.ACCEPT_CONTENT_TYPE, MediaType.APPLICATION_JSON);

		// Exchange outExchangeUser = builder.withHeader(Exchange.HTTP_RESPONSE_CODE, 204).withBody("User successfully updated").build();
		Exchange outExchangeUser = builder.withHeader("id", 3)
										  .withBody("{\"id\": 3, \"name\": \"Indu\"}").build();

		producerTemplate.send("direct:rest-with-get", outExchangeUser);
	

		mockEndpointUser.assertIsSatisfied();
		

	}


}
