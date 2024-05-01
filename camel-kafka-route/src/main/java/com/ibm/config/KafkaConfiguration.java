package com.ibm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Value("${camel.component.kafka.brokers}")
    private String kafkaServerUrl;


}
