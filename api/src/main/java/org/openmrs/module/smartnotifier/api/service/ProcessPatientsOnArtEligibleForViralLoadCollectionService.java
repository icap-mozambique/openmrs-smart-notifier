/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase;
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
 *
 */
@Service("smartnotifier.ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase")
public class ProcessPatientsOnArtEligibleForViralLoadCollectionService extends BaseOpenmrsService
implements ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase {

	private PatientNotificationPort patientNotificationPort;

	@Autowired
	public ProcessPatientsOnArtEligibleForViralLoadCollectionService(final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}

	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {

		final List<PatientNotification> patientsToNotify = this.patientNotificationPort.getPatientsToNotify(
				ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase.PATIENTS_OR_ART_AND_ELIGIBLE_FOR_VL_COLLECTION,
				new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams());

		for (final PatientNotification patientNotification : patientsToNotify) {
			patientNotification.setNotificationStatus(NotificationStatus.PENDING);
			patientNotification.setSuggestedAppointmentDate(patientNotification.getAppointmentDate());
			patientNotification.setNotificationType(NotificationType.ON_ART_ELIGIBLE_FOR_VIRAL_LOAD_COLLECTION);

			if (!PhoneNumberValidator.isValidate(patientNotification.getPhoneNumber())) {
				patientNotification.setNotificationStatus(NotificationStatus.INVALID_PHONE_NUMBER);
			}

			this.patientNotificationPort.savePatientNotification(patientNotification);
		}

		return patientsToNotify;
	}
}
