/**
 *
 */
package org.openmrs.module.smartnotifier.api.domain;

/**
 * @author Stélio Moiane
 */
public enum NotificationType {
	
	ON_ART_LESS_THAN_6_MONTHS("820802"),
	
	DEFAULTERS_FOR_FIVE_DAYS("820803"),
	
	DEFULTERS_NOTIFIED_THREE_DAYS_AGO("820804");
	
	private String value;
	
	NotificationType(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
