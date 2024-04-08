package com.devsu.operations.config;

import com.devsu.operations.errorhandler.rest.RestErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OperationConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.errorHandler(new RestErrorHandler()).build();
    }
}
