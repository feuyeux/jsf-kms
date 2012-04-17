package creative.fire.no202.dao.exceptions;

public class NonexistentEntityException extends Exception {
	private static final long serialVersionUID = 2462359126625502143L;

	public NonexistentEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonexistentEntityException(String message) {
		super(message);
	}
}
