/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.common.ParamBuilder;
import org.openmrs.module.smartnotifier.api.common.PhoneNumberValidator;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Stélio Moiane
 */
@Service("smartnotifier.ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase")
public class ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationService extends BaseOpenmrsService implements ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase {
	
	private PatientNotificationPort patientNotificationPort;
	
	@Autowired
	public ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationService(
	    final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}
	
	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {
		
		final List<PatientNotification> patientsToNotify = this.patientNotificationPort
		        .getPatientsToNotify(
		            ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase.PATIENTS_WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION,
		            new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams());
		
		for (final PatientNotification patientNotification : patientsToNotify) {
			patientNotification.setNotificationType(NotificationType.WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION);
			patientNotification.setNotificationStatus(NotificationStatus.PENDING);
			
			if (!PhoneNumberValidator.isValidate(patientNotification.getPhoneNumber())) {
				patientNotification.setNotificationStatus(NotificationStatus.INVALID_PHONE_NUMBER);
			}
			
			LocalDate appointmentDate = DateUtil.toLocalDate(patientNotification.getAppointmentDate());
			if (DayOfWeek.SATURDAY.equals(appointmentDate.getDayOfWeek())) {
				appointmentDate = appointmentDate.minusDays(1);
			}
			
			if (DayOfWeek.SUNDAY.equals(appointmentDate.getDayOfWeek())) {
				appointmentDate = appointmentDate.minusDays(2);
			}
			
			patientNotification.setSuggestedAppointmentDate(DateUtil.toTimestamp(appointmentDate));
			
			this.patientNotificationPort.savePatientNotification(patientNotification);
		}
		
		return patientsToNotify;
	}
}
