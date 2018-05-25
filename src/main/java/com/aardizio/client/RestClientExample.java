package com.aardizio.client;

import com.aardizio.model.Hotels;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

/**
 * Example web client to rest calls.
 * 
 * @author Alessandro Pio Ardizio
 * 
 */
public class RestClientExample {

    public Flux<Hotels> send() {

        WebClient client = WebClient.create("http://microservice");
        Flux<Hotels> hotels = client.get().uri("/hotels").accept(MediaType.APPLICATION_JSON).retrieve()
                .onStatus(HttpStatus::is4xxClientError, RestClientErrorHandler::handle)
                .onStatus(HttpStatus::is5xxServerError, RestClientErrorHandler::handle).bodyToFlux(Hotels.class);
        return hotels;
    }

}