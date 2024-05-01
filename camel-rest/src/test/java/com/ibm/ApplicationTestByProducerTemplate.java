package com.ibm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.ServiceStatus;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest
@CamelSpringBootTest
public class ApplicationTestByProducerTemplate {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private ProducerTemplate producerTemplate;

	MockEndpoint mockHello;

	Integer port=8080;

	
	@Test
	public void testUserPost() throws Exception {
		MockEndpoint mockEndpointUser = camelContext.getEndpoint("mock:users", MockEndpoint.class);
		
		AdviceWith.adviceWith(camelContext, "post-user",
			// intercepting an exchange on route
			r -> {
				// replacing consumer with direct component
				r.replaceFromWith("direct:rest-with-post");
				// mocking producer
				r.mockEndpoints("users*");
			}
		);

		final String responseBody = "User successfully created";

		mockEndpointUser.expectedBodiesReceived(responseBody);
		mockEndpointUser.expectedMessageCount(0);

		//Send a message to each put endpoint
		ExchangeBuilder builder = ExchangeBuilder.anExchange(camelContext)
			.withHeader(Exchange.HTTP_METHOD, HttpMethod.POST)
			.withHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.withHeader(Exchange.ACCEPT_CONTENT_TYPE, MediaType.APPLICATION_JSON);

		// Exchange outExchangeUser = builder.withHeader(Exchange.HTTP_RESPONSE_CODE, 204).withBody("User successfully updated").build();
		Exchange outExchangeUser = builder.withBody("{\"id\": 4, \"name\": \"Ram\"}").build();
		Exchange responseExchange = producerTemplate.send("direct:rest-with-post", outExchangeUser);
		
		
		
		//assertEquals(responseBody, responseExchange.getIn().getBody().toString());
		//assertEquals(204, responseExchange.getIn().getHeader("CamelHttpResponseCode"));
		
	

		mockEndpointUser.assertIsSatisfied();
	}

	@Test
	public void testCamelStarts() {
		assertEquals(ServiceStatus.Started, camelContext.getStatus());
		// assertNotNull(camelContext.getRoutes()).hasSizeGreaterThan(0);
	}
}
