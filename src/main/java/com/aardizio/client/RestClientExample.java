package com.aardizio.client;

import com.aardizio.model.Hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

/**
 * Example web client to rest calls.
 * 
 * @author Alessandro Pio Ardizio
 * 
 */
@Component
public class RestClientExample {

    @Autowired
    @Qualifier("hotelWebClient")
    private WebClient hotelWebClient;

    public Flux<Hotel> getAllHotels(){

        Flux<Hotel> hotels = hotelWebClient.get()
                                            .uri("/hotels")
                                            .accept(MediaType.APPLICATION_JSON)
                                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                                            .retrieve()
                                            .onStatus(HttpStatus::is4xxClientError, RestClientErrorHandler::handle)
                                            .onStatus(HttpStatus::is5xxServerError, RestClientErrorHandler::handle)
                                            .bodyToFlux(Hotel.class);
        return hotels;
    }

}