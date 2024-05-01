package com.ibm;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.junit5.params.Test;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.route.CamelKafkaRoute;

public class SimpleRouteBuilderTest  extends CamelSpringTestSupport {

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("simpleTransform-context.xml");
	}
	
	@Test
	public void testPayloadIsTransformed() 
	   throws InterruptedException {
	 MockEndpoint mockOut = getMockEndpoint("mock:out");
	 mockOut.setExpectedMessageCount(1);
	 template.sendBody("direct:in", "this is test");
	 assertMockEndpointsSatisfied();
	}
}
