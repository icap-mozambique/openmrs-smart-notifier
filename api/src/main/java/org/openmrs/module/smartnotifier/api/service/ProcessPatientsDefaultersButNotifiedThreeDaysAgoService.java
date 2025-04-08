/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.smartnotifier.api.application.ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.util.ParamBuilder;
import org.openmrs.module.smartnotifier.api.util.PhoneNumberValidator;
import org.openmrs.module.smartnotifier.api.util.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stélio Moiane
 */

@Transactional
public class ProcessPatientsDefaultersButNotifiedThreeDaysAgoService implements ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase {
	
	private PatientNotificationPort patientNotificationPort;
	
	@Autowired
	public ProcessPatientsDefaultersButNotifiedThreeDaysAgoService(final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}
	
	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {
		
		final List<PatientNotification> patientsToNotify = this.patientNotificationPort
		        .getPatientsToNotify(
		            QueryUtil
		                    .loadQuery(ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase.PATIENTS_DEFAULTERS_BUT_NOTIFIED_THREE_DAYS_AGO),
		            new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams());
		
		for (final PatientNotification patientNotification : patientsToNotify) {
			patientNotification.setNotificationStatus(NotificationStatus.PENDING);
			patientNotification.setNotificationType(NotificationType.DEFULTERS_NOTIFIED_THREE_DAYS_AGO);
			patientNotification.setSuggestedAppointmentDate(patientNotification.getAppointmentDate());
			
			if (!PhoneNumberValidator.isValidate(patientNotification.getPhoneNumber())) {
				patientNotification.setNotificationStatus(NotificationStatus.INVALID_PHONE_NUMBER);
			}
			
			this.patientNotificationPort.savePatientNotification(patientNotification);
		}
		
		return patientsToNotify;
	}
}
