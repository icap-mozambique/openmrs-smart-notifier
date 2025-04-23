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
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtWithHighViralLoadButNotReturnedService;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */

public class ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCaseTest extends AbstractIntegrationTest {
	
	@Autowired
	private ProcessPatientsOnArtWithHighViralLoadButNotReturnedService processPatientsOnArtWithHighViralLoadButNotReturnedUseCase;
	
	@Test
	@Ignore
	public void shouldPatientsOnArtWithHighViralLoadButNotReturned() throws BusinessException {
		final LocalDate endDate = LocalDate.of(2025, 04, 23);
		final Location location = Context.getLocationService().getDefaultLocation();
		
		final List<PatientNotification> patientsToNotify = this.processPatientsOnArtWithHighViralLoadButNotReturnedUseCase
		        .process(endDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification notification : patientsToNotify) {
			Assert.assertEquals(NotificationType.ON_ART_WITH_HIGH_VIRAL_LOAD_NOT_RETURNED,
			    notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			Assert.assertNotNull(notification.getSuggestedAppointmentDate());
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
