package core;

/**
 * 
 * @author Sean Stephens
 * 
 */
@SuppressWarnings("serial")
public class EmptySymbolException extends RuntimeException {

	public EmptySymbolException() {
		super("Tried to construct Token with empty symbol.");
	}
}