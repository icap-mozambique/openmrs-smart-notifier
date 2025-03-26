/**
 *
 */
package org.openmrs.module.smartnotifier.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.module.smartnotifier.api.dao.PatientNotificationDAO;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.Message;
import org.openmrs.module.smartnotifier.api.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.service.SendPatientsToBeNotifiedService;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class SentPatientsToBeNotifiedServiceIntegTest extends AbstractIntegrationTest {
	
	@Autowired
	private SendPatientsToBeNotifiedService sendPatientsToBeNotifiedService;
	
	@Autowired
	private PatientNotificationDAO patientNotificationDAO;
	
	@Mock
	private SendPatientPort sendPatientPort;
	
	@Test
	@Ignore
	public void shouldSendPatientsToBeNotified() throws BusinessException {

		this.sendPatientsToBeNotifiedService.setSendPatientPort(this.sendPatientPort);

		Mockito.when(this.sendPatientPort.send(this.patientNotificationDAO.getPendingPatientNotifications()))
		.thenReturn(new Message(MessageStatus.OK, "Teste"));

		final List<PatientNotification> patientNotifications = this.sendPatientsToBeNotifiedService.send();

		Assert.assertFalse(patientNotifications.isEmpty());

		patientNotifications.forEach(notification -> {
			Assert.assertEquals(NotificationStatus.SENT, notification.getNotificationStatus());
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
