package main;



/**
 * Application-specific aliasing of a {@link RuntimeException}
 * @author Andrew Pett & Matthew Mortimer
 *
 */
@SuppressWarnings("serial")
public class CluedoException extends RuntimeException {
	
	public CluedoException() {
		super();
	}
	public CluedoException(String message) {
		super(message);
	}
	
}
