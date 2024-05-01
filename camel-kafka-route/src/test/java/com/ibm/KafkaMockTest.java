package com.ibm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.junit5.params.Test;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@MockEndpoints("mock:file:output")
@CamelSpringBootTest
@SpringBootTest(
	    properties = { "camel.springboot.name=customName" })
public class KafkaMockTest extends CamelTestSupport {
	
	 @Autowired
     private CamelContext camelContext;

     @Autowired
     ProducerTemplate producerTemplate;

     @EndpointInject
     MockEndpoint mockEndpoint;

     @Test
     public void shouldAutowireProducerTemplate() {
         assertNotNull(producerTemplate);
     }
     @Test
     public void shouldSetCustomName() {
         assertEquals("customName", producerTemplate.getCamelContext().getName());
     }
 
     @Test
     public void shouldInjectEndpoint() throws InterruptedException {
    	 String input = "{ \"id\": 5, \"name\": \"John\" }";
         mockEndpoint.setExpectedMessageCount(1);
         producerTemplate.sendBody("direct:hello", input);
         mockEndpoint.assertIsSatisfied();
     }

     @Test
     void whenSendBody_thenGreetingReceivedSuccessfully() throws InterruptedException {
         mockEndpoint.expectedBodiesReceived("Hello IBM Camel Users!");
         producerTemplate.sendBody("direct:hello", null);
         mockEndpoint.assertIsSatisfied();
     }
}
