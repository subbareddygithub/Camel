package com.ibm;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Autowired
    private Environment env;

    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;

    @Override
    public void configure() {
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(env.getProperty("server.port", "8080"))
            .contextPath(contextPath.substring(0, contextPath.length() - 2))
            // turn on openapi api-doc
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "User API")
            .apiProperty("api.version", "1.0.0");

        from("timer:hello?period={{timer.period}}").routeId("hello")
            .transform().method("myBean", "saySomething")
            .filter(simple("${body} contains 'foo'"))
                .to("log:foo")
            .end()
            .to("stream:out");

        rest("/users") //Base Path
            .description("User REST service")
            .consumes("application/json")
            .produces("application/json")

            .get()
                .description("Find all users")
                .outType(User[].class)
                .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
                .to("bean:userService?method=findUsers")    //

            .put("/{id}")
                .description("Update a user")
                .type(User.class)
                .param().name("id").type(RestParamType.query).description("The ID of the user to update").dataType("integer").endParam()    
                .param().name("body").type(RestParamType.body).description("The user to update").endParam()
                .responseMessage().code(204).message("User successfully updated").endResponseMessage()
                //.route().routeId("put-user-by-id") // route
                // .to("direct:update-user"); //another camel route
                .to("bean:userService?method=updateUser")
                // .log("Processing ${id}")
                // .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
                // .setBody(constant("User successfully updated"))
                
            .get("/{id}") //Uri template
                .description("Find user by ID")
                .outType(User.class)
                .param().name("id").type(RestParamType.header).description("The ID of the user").dataType("integer").endParam()
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .route().routeId("get-user-by-id")
                .to("bean:userService?method=findUser(${header.id})");
        
        from("direct:update-user")
            .to("bean:userService?method=updateUser")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
            .setBody(constant("User successfully updated"));
    }

}
