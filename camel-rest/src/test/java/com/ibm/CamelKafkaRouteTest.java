package com.ibm;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest
@CamelSpringBootTest
public class CamelKafkaRouteTest  {

	@Autowired
   CamelContext camelContext;
	
	@Autowired
	private ProducerTemplate template;
	
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void testRoute() throws Exception {
		
		AdviceWith.adviceWith(camelContext, "post-user",
				// intercepting an exchange on route
				r -> {
					// replacing consumer with direct component
					r.replaceFromWith("direct:rest-with-post");
					r.interceptSendToEndpoint("bean:userService?method=createUser").to("mock:kafka");
				}
			);

        // Prepare input data for the REST API call
        String input = "{ \"id\": 5, \"name\": \"John\" }";
		ExchangeBuilder builder = ExchangeBuilder.anExchange(camelContext)
				.withHeader(Exchange.HTTP_METHOD, HttpMethod.POST)
				.withHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.withHeader(Exchange.ACCEPT_CONTENT_TYPE, MediaType.APPLICATION_JSON);
		
		Exchange outExchangeUser = builder.withBody(input).build();

        // Expect the mock endpoint to receive the data
       // getMockEndpoint("mock:kafka").expectedBodiesReceived(input);

        // Send the input data to the REST API endpoint
       // template.sendBody("direct:create-user", input);
        template.send("direct:create-user", outExchangeUser);
        

        // Verify that the mock endpoint received the data
       // assertMockEndpointsSatisfied();

        // Verify that the Kafka endpoint was not actually called
        //verifyNoMoreInteractions(camelContext);
    }
	

}
