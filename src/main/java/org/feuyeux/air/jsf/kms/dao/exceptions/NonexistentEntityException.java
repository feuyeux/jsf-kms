package org.feuyeux.air.jsf.kms.dao.exceptions;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class NonexistentEntityException extends Exception {
	private static final long serialVersionUID = 2462359126625502143L;

	public NonexistentEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonexistentEntityException(String message) {
		super(message);
	}
}
