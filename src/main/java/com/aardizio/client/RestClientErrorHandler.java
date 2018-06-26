package com.aardizio.client;


import com.aardizio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientResponse;

import reactor.core.publisher.Mono;
/**
 * Generic error handler for rest requests.
 * @author Alessandro Pio Ardizio
 */
public class RestClientErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestClientExample.class);

    public static  Mono<Throwable> handle(ClientResponse cr){
        // TODO do exception mapping here
        return Mono.just(new RestClientException(cr.statusCode().toString() , "Message" ,""));
    }

    public static  void handle(Throwable e){
        log.error("{}",e);
    }

}