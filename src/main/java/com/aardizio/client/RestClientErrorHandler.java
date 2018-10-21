package com.aardizio.client;


import org.springframework.web.reactive.function.client.ClientResponse;

import com.aardizio.errors.RestClientException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
/**
 * Generic error handler for rest requests.
 * @author Alessandro Pio Ardizio
 */
@Slf4j
public class RestClientErrorHandler {


    public static  Mono<Throwable> handle(ClientResponse cr){
        // TODO do exception mapping here
        return Mono.just(new RestClientException(cr.statusCode().toString() , "Message" ,""));
    }

    public static  void handle(Throwable e){
        log.error("{}",e);
    }

}