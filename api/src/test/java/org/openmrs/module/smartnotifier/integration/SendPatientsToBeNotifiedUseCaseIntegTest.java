/**
 *
 */
package org.openmrs.module.smartnotifier.integration;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.module.smartnotifier.api.application.in.SendPatientsToBeNotifiedUseCase;
import org.openmrs.module.smartnotifier.api.application.out.Message;
import org.openmrs.module.smartnotifier.api.application.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.application.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.infrastructure.adapter.PatientNotificationAdapter;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.util.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stélio Moiane
 */
public class SendPatientsToBeNotifiedUseCaseIntegTest extends AbstractIntegrationTest {
	
	@Autowired
	private SendPatientsToBeNotifiedUseCase sendPatientsToBeNotifiedService;
	
	@Autowired
	private PatientNotificationAdapter patientNotificationDAO;
	
	@Mock
	private SendPatientPort sendPatientPort;
	
	@Test
	@Ignore
	public void shouldSendPatientsToBeNotified() throws BusinessException {

		this.sendPatientsToBeNotifiedService.setSendPatientPort(this.sendPatientPort);

		Mockito.when(this.sendPatientPort.send(this.patientNotificationDAO.getPendingPatientNotifications()))
		.thenReturn(new Message(MessageStatus.OK, "Teste"));

		final LocalDate sendDate = LocalDate.of(2025, 04, 28);

		final List<PatientNotification> patientNotifications = this.sendPatientsToBeNotifiedService.send(sendDate);

		Assert.assertFalse(patientNotifications.isEmpty());

		patientNotifications.forEach(notification -> {
			Assert.assertEquals(NotificationStatus.SENT, notification.getNotificationStatus());
			Assert.assertEquals(DateUtil.toTimestamp(sendDate), notification.getNotificationSentDate());
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
