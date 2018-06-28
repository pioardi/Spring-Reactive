package com.aardizio.errors;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RestClientException extends Exception implements Serializable{

    private static final long serialVersionUID = 1L ;

    private String code;
    private String infos;

    public RestClientException(String code ,String message , String infos){
        super(message);
        this.code = code;
        this.infos = infos;
    }

}