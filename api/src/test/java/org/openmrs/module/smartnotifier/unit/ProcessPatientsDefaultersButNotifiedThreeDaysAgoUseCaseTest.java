/**
 *
 */
package org.openmrs.module.smartnotifier.unit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.Location;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.ParamBuilder;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsDefaultersButNotifiedThreeDaysAgoService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCaseTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsDefaultersButNotifiedThreeDaysAgoService processPatientsDefaultersButNotifiedThreeDaysAgoUseCase;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Test
	public void shouldProcessPatientsDefaultersButNotifiedThreeDaysAgo() throws BusinessException {
		
		final LocalDate endDate = LocalDate.of(2025, 04, 04);
		final Location location = new Location();
		
		final PatientNotification notification = new PatientNotification();
		notification.setPhoneNumber("822546100");
		
		Mockito.when(
		    this.patientNotificationPort.getPatientsToNotify(
		        ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase.PATIENTS_DEFAULTERS_BUT_NOTIFIED_THREE_DAYS_AGO,
		        new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams())).thenReturn(
		    Arrays.asList(notification));
		
		final List<PatientNotification> patientNotifications = this.processPatientsDefaultersButNotifiedThreeDaysAgoUseCase
		        .process(endDate, location);
		
		Assert.assertFalse(patientNotifications.isEmpty());
		
		for (final PatientNotification patientNotification : patientNotifications) {
			Assert.assertEquals(NotificationType.DEFULTERS_NOTIFIED_THREE_DAYS_AGO,
			    patientNotification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, patientNotification.getNotificationStatus());
		}
	}
}
