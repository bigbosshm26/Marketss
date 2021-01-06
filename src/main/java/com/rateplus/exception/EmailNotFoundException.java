package com.rateplus.exception;

import org.springframework.security.core.AuthenticationException;

public class EmailNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String msg) {
		super(msg);
	}

	public EmailNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
