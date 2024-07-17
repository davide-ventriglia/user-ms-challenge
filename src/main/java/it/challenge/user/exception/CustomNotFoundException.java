package it.challenge.user.exception;

public class CustomNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 3635429153164725196L;

	public CustomNotFoundException(String message) {
		super(message);
	}
}
