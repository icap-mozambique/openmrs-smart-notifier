/**
 *
 */
package org.openmrs.module.smartnotifier.api.exception;

/**
 * @author Stélio Moiane
 */
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BusinessException() {
	}
	
	public BusinessException(final String message) {
		super(message);
	}
}
