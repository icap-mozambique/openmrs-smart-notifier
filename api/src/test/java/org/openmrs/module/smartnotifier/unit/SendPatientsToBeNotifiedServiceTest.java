/**
 *
 */
package org.openmrs.module.smartnotifier.unit;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.module.smartnotifier.api.dao.PatientNotificationDAO;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.Message;
import org.openmrs.module.smartnotifier.api.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.service.SendPatientsToBeNotifiedServiceImpl;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */

public class SendPatientsToBeNotifiedServiceTest extends AbstractUnitTest {
	
	@InjectMocks
	private SendPatientsToBeNotifiedServiceImpl sendPatientsToBeNotifiedService;
	
	@Mock
	private PatientNotificationDAO patientNotificationDAO;
	
	@Mock
	private SendPatientPort sendPatientPort;
	
	@Test
	public void shouldSendPatientsToBeNotified() throws BusinessException {

		final PatientNotification patientNotification = new PatientNotification();

		final Message message = new Message(MessageStatus.OK, "Teste");

		Mockito.when(this.patientNotificationDAO.getPendingPatientNotifications()).thenReturn(Arrays.asList(patientNotification));
		Mockito.when(this.sendPatientPort.send(Arrays.asList(patientNotification))).thenReturn(message);

		final List<PatientNotification> patients = this.sendPatientsToBeNotifiedService.send();

		Assert.assertFalse(patients.isEmpty());

		Mockito.verify(this.patientNotificationDAO, Mockito.timeout(1)).getPendingPatientNotifications();

		patients.forEach(patient -> {
			Assert.assertEquals(NotificationStatus.SENT, patient.getNotificationStatus());
			Mockito.verify(this.patientNotificationDAO, Mockito.timeout(1)).savePatientNotification(patientNotification);
		});
	}
}
