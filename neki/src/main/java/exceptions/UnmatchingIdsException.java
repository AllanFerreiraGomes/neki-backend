package exceptions;

public class UnmatchingIdsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnmatchingIdsException(String message) {
		super(message);
	}
	
	public UnmatchingIdsException() {
		super("Valor do id do elemento a ser atualizado não foi declarado.");
	}
	
	public UnmatchingIdsException(Integer id) {
		super("Elemento com id " + id + " não existe.");
	}
	
	public UnmatchingIdsException(Integer id, String elemento) {
		super("Não é possível criar id no método save");
	}
	
	
	
}
