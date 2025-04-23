/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
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

@Service("smartnotifier.ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase")
public class ProcessPatientsOnArtWithHighViralLoadButNotReturnedService extends BaseOpenmrsService
implements ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase {

	private PatientNotificationPort patientNotificationPort;

	@Autowired
	public ProcessPatientsOnArtWithHighViralLoadButNotReturnedService(final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}

	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {

		final List<PatientNotification> patientsToNotify = this.patientNotificationPort
				.getPatientsToNotify(
						ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase.PATIENTS_ON_ART_WITH_HIGH_VL_NOTIFIED_BUT_NOT_RETURNED,
						new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams());

		for (final PatientNotification patientNotification : patientsToNotify) {
			patientNotification.setNotificationType(NotificationType.ON_ART_WITH_HIGH_VIRAL_LOAD_NOT_RETURNED);
			patientNotification.setNotificationStatus(NotificationStatus.PENDING);
			patientNotification.setSuggestedAppointmentDate(patientNotification.getAppointmentDate());

			if (!PhoneNumberValidator.isValid(patientNotification.getPhoneNumber())) {
				patientNotification.setNotificationStatus(NotificationStatus.INVALID_PHONE_NUMBER);
			}

			this.patientNotificationPort.savePatientNotification(patientNotification);
		}

		return patientsToNotify;
	}
}
