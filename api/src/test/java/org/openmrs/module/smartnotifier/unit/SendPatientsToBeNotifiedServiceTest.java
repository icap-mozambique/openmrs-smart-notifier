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
import org.openmrs.module.smartnotifier.api.application.out.Message;
import org.openmrs.module.smartnotifier.api.application.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.application.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.SendPatientsToBeNotifiedService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */

public class SendPatientsToBeNotifiedServiceTest extends AbstractUnitTest {
	
	@InjectMocks
	private SendPatientsToBeNotifiedService sendPatientsToBeNotifiedService;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Mock
	private SendPatientPort sendPatientPort;
	
	@Test
	public void shouldSendPatientsToBeNotified() throws BusinessException {
		
		final PatientNotification patientNotification = new PatientNotification();
		final LocalDate sendDate = LocalDate.of(2025, 04, 28);
		
		final Message message = new Message(MessageStatus.OK, "Teste");
		
		Mockito.when(this.patientNotificationPort.getPendingPatientNotifications()).thenReturn(
		    Arrays.asList(patientNotification));
		Mockito.when(this.sendPatientPort.send(Arrays.asList(patientNotification))).thenReturn(message);
		
		final List<PatientNotification> patients = this.sendPatientsToBeNotifiedService.send(sendDate);
		
		Assert.assertFalse(patients.isEmpty());
		
		Mockito.verify(this.patientNotificationPort, Mockito.timeout(1)).getPendingPatientNotifications();
		
		for (final PatientNotification patient : patients) {
			Assert.assertEquals(NotificationStatus.SENT, patient.getNotificationStatus());
			Assert.assertEquals(DateUtil.toTimestamp(sendDate), patient.getNotificationSentDate());
			
			Mockito.verify(this.patientNotificationPort, Mockito.timeout(1)).savePatientNotification(patientNotification);
		}
	}
}
