package in.ankushs.dbip.exceptions;

public class InvalidIPException extends RuntimeException{
	private String message;
	public InvalidIPException(final String message){
		super(message);
	}
}
