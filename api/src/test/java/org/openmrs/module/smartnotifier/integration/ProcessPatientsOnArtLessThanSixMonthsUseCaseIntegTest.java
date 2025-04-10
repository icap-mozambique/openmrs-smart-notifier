/**
 *
 */
package org.openmrs.module.smartnotifier.integration;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.smartnotifier.api.application.ProcessPatientsOnArtLessThanSixMonthsUseCase;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsOnArtLessThanSixMonthsUseCaseIntegTest extends AbstractIntegrationTest {

	@Autowired
	private ProcessPatientsOnArtLessThanSixMonthsUseCase patientsOnArtLessThanSixMonthsService;

	@Test
	@Ignore
	public void shouldProcessPatientsOnArtLessThanSixMonths() throws BusinessException {
		final Location location = Context.getLocationService().getDefaultLocation();

		final LocalDate endDate = LocalDate.now();

		final List<PatientNotification> patientNotifications = this.patientsOnArtLessThanSixMonthsService.process(endDate,
				location);

		Assert.assertFalse(patientNotifications.isEmpty());

		patientNotifications.forEach(notification -> {
			Assert.assertEquals(NotificationType.ON_ART_LESS_THAN_6_MONTHS, notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
		});
	}

	@Override
	protected String username() {
		return "admin";
	}

	@Override
	protected String password() {
		return "Ic@pSIS2021";
	}
}
