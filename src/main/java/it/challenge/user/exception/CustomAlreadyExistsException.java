package it.challenge.user.exception;

public class CustomAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6733855422469008046L;

	public CustomAlreadyExistsException(String message) {
		super(message);
	}
}
