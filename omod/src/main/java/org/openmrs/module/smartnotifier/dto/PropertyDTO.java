/**
 *
 */
package org.openmrs.module.smartnotifier.dto;

/**
 * @author Stélio Moiane
 */
public class PropertyDTO {
	
	private String appointment_date;
	
	private String health_facility_code;
	
	public PropertyDTO(final String appointment_date, final String health_facility_code) {
		this.appointment_date = appointment_date;
		this.health_facility_code = health_facility_code;
	}
	
	public String getAppointment_date() {
		return this.appointment_date;
	}
	
	public String getHealth_facility_code() {
		return this.health_facility_code;
	}
}
