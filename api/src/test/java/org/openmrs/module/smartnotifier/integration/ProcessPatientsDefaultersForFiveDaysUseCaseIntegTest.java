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
import org.openmrs.module.smartnotifier.api.application.ProcessPatientsDefaultersForFiveDaysUseCase;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsDefaultersForFiveDaysUseCaseIntegTest extends AbstractIntegrationTest {
	
	@Autowired
	private ProcessPatientsDefaultersForFiveDaysUseCase patientsDefaultersForFiveDaysUseCase;
	
	@Test
	@Ignore
	public void shouldProcessPatientsDefaultersForFiveDays() throws BusinessException {

		final LocalDate localDate = LocalDate.now();
		final Location location = Context.getLocationService().getDefaultLocation();

		final List<PatientNotification> patientNotifications = this.patientsDefaultersForFiveDaysUseCase.process(localDate, location);

		Assert.assertFalse(patientNotifications.isEmpty());

		patientNotifications.forEach(notification -> {
			Assert.assertEquals(NotificationType.DEFAULTERS_FOR_FIVE_DAYS, notification.getNotificationType());
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
