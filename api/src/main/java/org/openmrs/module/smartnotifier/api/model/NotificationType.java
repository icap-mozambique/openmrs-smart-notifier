/**
 *
 */
package org.openmrs.module.smartnotifier.api.model;

/**
 * @author Stélio Moiane
 */
public enum NotificationType {
	
	ON_ART_LESS_THAN_6_MONTHS("820802");
	
	private String value;
	
	NotificationType(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
