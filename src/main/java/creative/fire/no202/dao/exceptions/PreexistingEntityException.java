package creative.fire.no202.dao.exceptions;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class PreexistingEntityException extends Exception {
	private static final long serialVersionUID = -8935459259580777996L;

	public PreexistingEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreexistingEntityException(String message) {
		super(message);
	}
}
