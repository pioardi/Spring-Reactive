package com.aardizio.config;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mapping manager configuration for cassandra
 * 
 * @author Alessandro Pio Ardizio
 */
@Configuration
public class MappingManagerConfig {

    @Bean
    public MappingManager mappingManager(Session session) {
        return new MappingManager(session);
    }

}