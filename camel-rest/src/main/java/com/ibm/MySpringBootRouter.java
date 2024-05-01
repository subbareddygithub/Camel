package com.ibm;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;
    
   /* @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;

    @Value("${camel.component.kafka.brokers}")
    private String kafkaBrokers;*/

    


    @Override
    public void configure() {

        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(env.getProperty("server.port", "8080"))
            //.contextPath(contextPath.substring(0, contextPath.length() - 2))
            // turn on openapi api-doc
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "User API")
            .apiProperty("api.version", "1.0.0");

      

        rest("/users") //Base Path
            .description("User REST service")
            .consumes("application/json")
            .produces("application/json")

        
            .post()
              .description("Create a user")
              .type(User.class)
              .param().name("body").type(body).description("The user to created").endParam()
              .responseMessage().code(204).message("User successfully created").endResponseMessage()
			  .responseMessage().code(400).message("Unexpected body").endResponseMessage() //Wrong input
			  .responseMessage().code(500).endResponseMessage() //Not-OK
              .route().routeId("post-user") // route
              .to("direct:create-user") //another camel route
              //.to("bean:userService?method=createUser")
              .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
              .setBody(constant("User successfully created"))
              .endRest();

        from("direct:create-user")
                .log("${body}");
        
       
        
     /*   from("direct:create-user")
        .log("${body}")
        .to("bean:userService?method=createUser")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
        .setBody(constant("User successfully created"));*/
        
     /*   from("direct:create-user")
        .log("${body}")
        .to("kafka:sample-camel-topic");
       
    }

    

	@Override
	public void configure() throws Exception {
		
	/*	restConfiguration()
		    .component("servlet")
		    .host("localhost")
		    .port(8080);
		    
		rest("/users")
            .post()
            .route().routeId("post-user")
            //.to("direct:create-user");
			.to("kafka:payexecute-topic");

		from("kafka:payexecute-topic")
            //.setHeader(KafkaConstants.KEY, constant("1"))
            //.setHeader(KafkaConstants.TOPIC, constant("camel-kafka-test-topic"))
            .log("${body}");
            //.to("kafka:localhost:9092?topic=camel-kafka-test-topic&zookeeperHost=localhost&zookeeperPort=2181");
	    //.to("kafka:simple-topic");
		*/
		/*restConfiguration().component("servlet");
        rest("/users")
                .post()
                .to("kafka:paymenttopic");*/
	}
}
