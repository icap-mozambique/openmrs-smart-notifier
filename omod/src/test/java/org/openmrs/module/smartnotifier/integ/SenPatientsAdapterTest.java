/**
 *
 */
package org.openmrs.module.smartnotifier.integ;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.smartnotifier.adapter.SendPatientsToViamoAdapter;
import org.openmrs.module.smartnotifier.api.application.out.Message;
import org.openmrs.module.smartnotifier.api.application.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.builder.JerseyBuilder;

/**
 * @author Stélio Moiane
 */
public class SenPatientsAdapterTest {

	@Test
	@Ignore
	public void shouldSendPatientsToBeNotified() throws BusinessException {

		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setPhoneNumber("822546100");
		patientNotification.setSuggestedAppointmentDate(DateUtil.toTimestamp(LocalDate.of(2025, 03, 28)));
		patientNotification.setNotificationType(NotificationType.ON_ART_LESS_THAN_6_MONTHS);

		final JerseyBuilder builder = new JerseyBuilder();

		final SendPatientsToViamoAdapter patientsAdapter = new SendPatientsToViamoAdapter(builder);
		final Message message = patientsAdapter.send(Arrays.asList(patientNotification));

		Assert.assertEquals(MessageStatus.OK, message.getMessageStatus());
	}
}
