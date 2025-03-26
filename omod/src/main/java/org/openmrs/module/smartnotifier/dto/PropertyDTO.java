/**
 *
 */
package org.openmrs.module.smartnotifier.dto;

/**
 * @author Stélio Moiane
 */
public class PropertyDTO {
	
	private String appointment_date;
	
	public PropertyDTO(final String appointment_date) {
		this.appointment_date = appointment_date;
	}
	
	public String getAppointment_date() {
		return this.appointment_date;
	}
}
