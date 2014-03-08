package core;

/**
 * 
 * @author Sean Stephens
 * 
 */
@SuppressWarnings("serial")
public class EmptyTokenException extends RuntimeException {

	public EmptyTokenException() {
		super("Tried to construct Token with empty symbol.");
	}
}