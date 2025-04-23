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
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtWithHighViralLoadService;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */

public class ProcessPatientsOnArtWithHighViralLoadUseCaseTest extends AbstractIntegrationTest {
	
	@Autowired
	private ProcessPatientsOnArtWithHighViralLoadService processPatientsOnArtWithHighViralLoadUseCase;
	
	@Test
	@Ignore
	public void shouldProcessPatientsOnARTWithHighViralLoad() throws BusinessException {
		final LocalDate endDate = LocalDate.of(2025, 03, 25);
		final Location location = Context.getLocationService().getDefaultLocation();
		
		final List<PatientNotification> patientsToNotify = this.processPatientsOnArtWithHighViralLoadUseCase.process(
		    endDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification patientNotification : patientsToNotify) {
			Assert.assertEquals(NotificationType.ON_ART_WITH_HIGH_VIRAL_LOAD, patientNotification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, patientNotification.getNotificationStatus());
			Assert.assertNotNull(patientNotification.getSuggestedAppointmentDate());
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
