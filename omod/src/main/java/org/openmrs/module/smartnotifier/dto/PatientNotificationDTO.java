/**
 *
 */
package org.openmrs.module.smartnotifier.dto;

/**
 * @author Stélio Moiane
 */
public class PatientNotificationDTO {
	
	private String phone;
	
	private String receive_voice;
	
	private String receive_sms;
	
	private String groups;
	
	private String active;
	
	private PropertyDTO property;
	
	public PatientNotificationDTO(final String phone, final String groups, final String appointmentDate) {
		this.phone = phone;
		this.groups = groups;
		
		this.receive_voice = "1";
		this.receive_sms = "1";
		this.active = "1";
		
		this.property = new PropertyDTO(appointmentDate);
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getReceive_voice() {
		return this.receive_voice;
	}
	
	public String getReceive_sms() {
		return this.receive_sms;
	}
	
	public String getGroups() {
		return this.groups;
	}
	
	public String getActive() {
		return this.active;
	}
	
	public PropertyDTO getProperty() {
		return this.property;
	}
}
