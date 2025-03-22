/**
 *
 */
package org.openmrs.module.smartnotifier.integration;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.PatientsOnArtLessThanSixMonthsService;
import org.openmrs.module.smartnotifier.util.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class PatientOnArtLessThanSixMonthsServiceIntegTest extends IntegrationTest {
	
	@Autowired
	private PatientsOnArtLessThanSixMonthsService patientsOnArtLessThanSixMonthsService;
	
	@Test
	public void shouldProcessPatientsOnArtLessThanSixMonths() throws BusinessException {
		final Location location = Context.getLocationService().getLocation(271);

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
