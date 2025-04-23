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
	
	DEFULTERS_NOTIFIED_THREE_DAYS_AGO("820804"),
	
	ON_ART_ELIGIBLE_FOR_VIRAL_LOAD_COLLECTION("820805"),
	
	WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION("820806"),
	
	ON_ART_WITH_HIGH_VIRAL_LOAD("820807"),
	
	ON_ART_WITH_HIGH_VIRAL_LOAD_NOT_RETURNED("820808");
	
	private String value;
	
	NotificationType(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
