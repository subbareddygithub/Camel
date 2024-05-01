package com.ibm.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelKafkaRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {

		String topicName = "topic=my-topic";
		String kafkaServer = "kafka:localhost:9092";
		String zooKeeperHost = "zookeeperHost=localhost&zookeeperPort=2181";
		String serializerClass = "serializerClass=kafka.serializer.StringEncoder";
		String toKafka = new StringBuilder().append(kafkaServer).append("?").append(topicName)
				         .append("&").append(zooKeeperHost).append("&").append(serializerClass)
				         .toString();
		//restConfiguration().host("localhost").port(8085);
		
		 from("direct:hello").routeId("post-user")
		 .log("${body}")
		 .to(toKafka);
	}

	
}
