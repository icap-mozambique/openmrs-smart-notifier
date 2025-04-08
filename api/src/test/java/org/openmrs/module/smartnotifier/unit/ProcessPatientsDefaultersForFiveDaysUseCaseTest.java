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
import org.openmrs.module.smartnotifier.api.application.ProcessPatientsDefaultersForFiveDaysUseCase;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsDefaultersForFiveDaysService;
import org.openmrs.module.smartnotifier.api.util.ParamBuilder;
import org.openmrs.module.smartnotifier.api.util.QueryUtil;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsDefaultersForFiveDaysUseCaseTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsDefaultersForFiveDaysService processPatientDefaultersForFiveDaysUseCase;
	
	@Mock
	private PatientNotificationPort paNotificationPort;
	
	@Test
	public void shouldProcessPatientsDefaultersForFiveDays() throws BusinessException {
		
		final LocalDate localDate = LocalDate.now();
		final Location location = new Location();
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setPhoneNumber("840546824");
		
		Mockito.when(
		    this.paNotificationPort.getPatientsToNotify(
		        QueryUtil.loadQuery(ProcessPatientsDefaultersForFiveDaysUseCase.PATIENTS_DEFAULTERS_FOR_FIVE_DAYS),
		        new ParamBuilder().add("endDate", localDate).add("location", location.getId()).getParams())).thenReturn(
		    Arrays.asList(patientNotification));
		
		final List<PatientNotification> patientNotifications = this.processPatientDefaultersForFiveDaysUseCase.process(
		    localDate, location);
		
		Assert.assertFalse(patientNotifications.isEmpty());
		
		for (final PatientNotification notification : patientNotifications) {
			Assert.assertEquals(NotificationType.DEFAULTERS_FOR_FIVE_DAYS, notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			
			Mockito.verify(this.paNotificationPort, Mockito.times(1)).savePatientNotification(notification);
		}
	}
}
