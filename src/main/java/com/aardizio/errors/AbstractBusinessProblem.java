package com.aardizio.errors;

import org.zalando.problem.AbstractThrowableProblem;

import javax.annotation.concurrent.Immutable;
import java.net.URI;

import static java.lang.String.format;
import static org.zalando.problem.Status.BAD_REQUEST;

@Immutable
public abstract class AbstractBusinessProblem extends AbstractThrowableProblem {
	
	static final URI TYPE = URI.create("https://example.org/out-of-stock");
	
	private String message;
	private String errorCode;
	private String level;
	
	public AbstractBusinessProblem(final String message, final String errorCode, final String level) {
		super(TYPE, "Business Problem", BAD_REQUEST, format("Item %s is no longer available", message));
		this.message = message;
		this.errorCode = errorCode;
		this.level = level;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getLevel() {
		return level;
	}
	
}
