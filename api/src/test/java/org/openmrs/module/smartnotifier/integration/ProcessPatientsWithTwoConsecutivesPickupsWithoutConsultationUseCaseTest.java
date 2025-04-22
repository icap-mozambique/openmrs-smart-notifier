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
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationService;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCaseTest extends AbstractIntegrationTest {
	
	@Autowired
	private ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationService processPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase;
	
	@Test
	@Ignore
	public void processPatientsWithTwoConsecutivePickupsWithoutConsultation() throws BusinessException {
		
		final LocalDate endDate = LocalDate.of(2025, 04, 20);
		final Location location = Context.getLocationService().getDefaultLocation();
		
		final List<PatientNotification> patientsToNotify = this.processPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase
		        .process(endDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification notificaton : patientsToNotify) {
			Assert.assertEquals(NotificationType.WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION,
			    notificaton.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notificaton.getNotificationStatus());
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
