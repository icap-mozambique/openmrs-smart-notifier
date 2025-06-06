/**
 *
 */
package org.openmrs.module.smartnotifier.api.infrastructure.entity;

import java.sql.Timestamp;

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
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;

/**
 * @author Stélio Moiane
 */

@Entity(name = "smartnotifier.api.infrastructure.entity.PatientNotification")
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
	
	@Column(name = "identifier", length = 30)
	private String identifier;
	
	@Column(name = "art_start_date", nullable = false)
	private Timestamp artStartDate;
	
	@Column(name = "phone_number", length = 150)
	private String phoneNumber;
	
	@Column(name = "appointment_date", nullable = false)
	private Timestamp appointmentDate;
	
	@Column(name = "suggested_appointment_date", nullable = false)
	private Timestamp suggestedAppointmentDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "notification_type", length = 150)
	private NotificationType notificationType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "notification_status", length = 25)
	private NotificationStatus notificationStatus;
	
	@Column(name = "notification_sent_date")
	private Timestamp notificationSentDate;
	
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
	
	public Timestamp getArtStartDate() {
		return this.artStartDate;
	}
	
	public void setArtStartDate(final Timestamp artStartDate) {
		this.artStartDate = artStartDate;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Timestamp getAppointmentDate() {
		return this.appointmentDate;
	}
	
	public void setAppointmentDate(final Timestamp appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public Timestamp getSuggestedAppointmentDate() {
		return this.suggestedAppointmentDate;
	}
	
	public void setSuggestedAppointmentDate(final Timestamp suggestedAppointmentDate) {
		this.suggestedAppointmentDate = suggestedAppointmentDate;
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
	
	public Timestamp getNotificationSentDate() {
		return this.notificationSentDate;
	}
	
	public void setNotificationSentDate(final Timestamp notificationSentDate) {
		this.notificationSentDate = notificationSentDate;
	}
}
