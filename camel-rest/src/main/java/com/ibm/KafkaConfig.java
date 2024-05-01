package com.ibm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${camel.component.kafka.brokers}")
    private String bootstrapServers;

    public String getBootstrapServers() {
        return bootstrapServers;
    }
}

