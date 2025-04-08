/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.smartnotifier.api.application.ProcessPatientsDefaultersForFiveDaysUseCase;
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
public class ProcessPatientsDefaultersForFiveDaysService implements ProcessPatientsDefaultersForFiveDaysUseCase {
	
	private PatientNotificationPort patientNotificationPort;
	
	@Autowired
	public ProcessPatientsDefaultersForFiveDaysService(final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}
	
	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {
		
		final List<PatientNotification> patientsToNotify = this.patientNotificationPort.getPatientsToNotify(
		    QueryUtil.loadQuery(ProcessPatientsDefaultersForFiveDaysUseCase.PATIENTS_DEFAULTERS_FOR_FIVE_DAYS),
		    new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams());
		
		for (final PatientNotification notification : patientsToNotify) {
			notification.setNotificationStatus(NotificationStatus.PENDING);
			notification.setNotificationType(NotificationType.DEFAULTERS_FOR_FIVE_DAYS);
			notification.setSuggestedAppointmentDate(notification.getAppointmentDate());
			
			if (!PhoneNumberValidator.isValidate(notification.getPhoneNumber())) {
				notification.setNotificationStatus(NotificationStatus.INVALID_PHONE_NUMBER);
			}
			
			this.patientNotificationPort.savePatientNotification(notification);
		}
		
		return patientsToNotify;
	}
}
