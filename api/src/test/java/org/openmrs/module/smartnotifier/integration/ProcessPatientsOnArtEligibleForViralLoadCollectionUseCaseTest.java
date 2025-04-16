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
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtEligibleForViralLoadCollectionService;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 *
 */
public class ProcessPatientsOnArtEligibleForViralLoadCollectionUseCaseTest extends AbstractIntegrationTest {

	@Autowired
	private ProcessPatientsOnArtEligibleForViralLoadCollectionService processPatientsOnArtEligibleForViralLoadCollectionUseCase;

	@Test
	@Ignore
	public void shouldProcessPatientsOnArtEligibleForViralLoadCollection() throws BusinessException {

		final LocalDate localDate = LocalDate.of(2025, 04, 15);
		final Location location = Context.getLocationService().getDefaultLocation();

		final List<PatientNotification> patientsToNotify = this.processPatientsOnArtEligibleForViralLoadCollectionUseCase.process(localDate,
				location);

		Assert.assertFalse(patientsToNotify.isEmpty());

		for (final PatientNotification notification : patientsToNotify) {
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			Assert.assertEquals(NotificationType.ON_ART_ELIGIBLE_FOR_VIRAL_LOAD_COLLECTION, notification.getNotificationType());
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
