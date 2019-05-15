package com.aardizio.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.baeldung.com/problem-spring-web
 * https://github.com/zalando/problem
 * https://github.com/zalando/problem-spring-web
 */
@Component
@ControllerAdvice
@Slf4j
public class ApiErrorHandler implements ProblemHandling {

	private Map<Class, ApiError> errors = new HashMap<>();

	{
		register(Exception.class, new ApiError(HttpStatus.BAD_REQUEST, "001", "2"));
	}

	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

		log.error("Handling exception of type {} , for request path {}", ex.getClass(), request.getContextPath());
		if (errors.containsKey(ex.getClass())) {
			ApiError ae = errors.get(ex.getClass());
			ae.setMessage(ex.getMessage());
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(ae);
		} else {
			// returning a default response
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON)
					.body(errors.get(ex.getClass()));
		}
	}

	public void register(Class key, ApiError value) {
		errors.put(key, value);
	}

}
