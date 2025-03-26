/**
 *
 */
package org.openmrs.module.smartnotifier.api.out;

/**
 * @author Stélio Moiane
 */
public class Message {
	
	private MessageStatus messageStatus;
	
	private String message;
	
	public Message(final MessageStatus messageStatus, final String message) {
		this.messageStatus = messageStatus;
		this.message = message;
	}
	
	public MessageStatus getMessageStatus() {
		return this.messageStatus;
	}
	
	public String getMessage() {
		return this.message;
	}
}
