package com.aardizio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ReactiveWebClients {

    @Bean("hotelWebClient")
    public WebClient hotelWebClient(){
        return WebClient.create("http://localhost:8080");
    }


    
}