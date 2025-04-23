/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtLessThanSixMonthsUseCase;
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
@Service("smartnotifier.ProcessPatientsOnArtLessThanSixMonthsUseCase")
public class ProcessPatientsOnArtLessThanSixMonthsService extends BaseOpenmrsService implements ProcessPatientsOnArtLessThanSixMonthsUseCase {
	
	private PatientNotificationPort patientNotificationPort;
	
	@Autowired
	public ProcessPatientsOnArtLessThanSixMonthsService(final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}
	
	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {
		
		final Map<String, Object> params = new ParamBuilder().add("endDate", endDate).add("location", location.getId())
		        .getParams();
		
		final List<PatientNotification> patientNotifications = this.patientNotificationPort.getPatientsToNotify(
		    ProcessPatientsOnArtLessThanSixMonthsUseCase.PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS, params);
		
		for (final PatientNotification patientNotification : patientNotifications) {
			
			patientNotification.setNotificationStatus(NotificationStatus.PENDING);
			patientNotification.setNotificationType(NotificationType.ON_ART_LESS_THAN_6_MONTHS);
			
			LocalDate appointmentDate = DateUtil.toLocalDate(patientNotification.getAppointmentDate());
			
			if (DayOfWeek.SATURDAY.equals(appointmentDate.getDayOfWeek())) {
				appointmentDate = appointmentDate.minusDays(1);
			}
			
			if (DayOfWeek.SUNDAY.equals(appointmentDate.getDayOfWeek())) {
				appointmentDate = appointmentDate.minusDays(2);
			}
			
			if (!PhoneNumberValidator.isValid(patientNotification.getPhoneNumber())) {
				patientNotification.setNotificationStatus(NotificationStatus.INVALID_PHONE_NUMBER);
			}
			
			patientNotification.setSuggestedAppointmentDate(DateUtil.toTimestamp(appointmentDate));
			
			this.patientNotificationPort.savePatientNotification(patientNotification);
		}
		
		return patientNotifications;
	}
}
