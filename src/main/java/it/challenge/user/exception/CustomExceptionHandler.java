package it.challenge.user.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomNotFoundException.class)
	public @ResponseBody ExceptionResponse handleNotFound(CustomNotFoundException ex,
			HttpServletRequest request) {

		ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getRequestURI());
		return exception;
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(CustomAlreadyExistsException.class)
	public @ResponseBody ExceptionResponse handleAlreadyExists(CustomAlreadyExistsException ex,
			HttpServletRequest request) {

		ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getRequestURI());
		return exception;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleIllegalState(Exception ex, HttpServletRequest request) {

		ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getRequestURI());
		return exception;
	}
}
