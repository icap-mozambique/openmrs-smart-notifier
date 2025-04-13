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
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCaseTest extends AbstractIntegrationTest {
	
	@Autowired
	private ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase processPatientsDefaultersButNotifiedThreeDaysAgoUseCase;
	
	@Test
	@Ignore
	public void shouldProcessPatientsDefaultersButNotifiedThreeDaysAgo() throws BusinessException {
		
		final LocalDate endDate = LocalDate.of(2025, 04, 04);
		final Location location = Context.getLocationService().getDefaultLocation();
		
		final List<PatientNotification> patientNotifications = this.processPatientsDefaultersButNotifiedThreeDaysAgoUseCase
		        .process(endDate, location);
		
		Assert.assertFalse(patientNotifications.isEmpty());
		
		for (final PatientNotification patientNotification : patientNotifications) {
			Assert.assertEquals(NotificationType.DEFULTERS_NOTIFIED_THREE_DAYS_AGO,
			    patientNotification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, patientNotification.getNotificationStatus());
		}
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
