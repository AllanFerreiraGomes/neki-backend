package exceptions;

public class UniqueElementException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UniqueElementException(String message) {
		super(message);
	}
	
	public UniqueElementException() {
		super("Tentativa de criação com elemento único repetido.");
	}
	
}