/**
 *
 */
package org.openmrs.module.smartnotifier.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;

/**
 * @author Stélio Moiane
 */

@Entity(name = "patient_notifications")
@Table(name = "patient_notifications")
public class PatientNotification extends BaseOpenmrsData {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@Column(name = "identifier", length = 25)
	private String identifier;
	
	@Column(name = "art_start_date", length = 15)
	private LocalDate artStartDate;
	
	@Column(name = "phone_number", length = 15)
	private String phoneNumebr;
	
	@Column(name = "appointment_date", length = 15)
	private LocalDate appointmentDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "notification_type", length = 150)
	private NotificationType notificationType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "notification_status", length = 25)
	private NotificationStatus notificationStatus;
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	@Override
	public void setId(final Integer id) {
		this.id = id;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public void setPatient(final Patient patient) {
		this.patient = patient;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}
	
	public LocalDate getArtStartDate() {
		return this.artStartDate;
	}
	
	public void setArtStartDate(final LocalDate artStartDate) {
		this.artStartDate = artStartDate;
	}
	
	public String getPhoneNumebr() {
		return this.phoneNumebr;
	}
	
	public void setPhoneNumebr(final String phoneNumebr) {
		this.phoneNumebr = phoneNumebr;
	}
	
	public LocalDate getAppointmentDate() {
		return this.appointmentDate;
	}
	
	public void setAppointmentDate(final LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public NotificationType getNotificationType() {
		return this.notificationType;
	}
	
	public void setNotificationType(final NotificationType notificationType) {
		this.notificationType = notificationType;
	}
	
	public NotificationStatus getNotificationStatus() {
		return this.notificationStatus;
	}
	
	public void setNotificationStatus(final NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
}
