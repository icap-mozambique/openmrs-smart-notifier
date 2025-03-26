/**
 *
 */
package org.openmrs.module.smartnotifier.integ;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.smartnotifier.adapter.SendPatientsAdapter;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.Message;
import org.openmrs.module.smartnotifier.api.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.util.DateUtil;

/**
 * @author Stélio Moiane
 */
public class SenPatientsAdapterTest {
	
	@Test
	@Ignore
	public void shouldSendPatientsToBeNotified() throws BusinessException {
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setPhoneNumebr("822546100");
		patientNotification.setSuggestedAppointmentDate(DateUtil.toTimestamp(LocalDate.of(2025, 03, 28)));
		patientNotification.setNotificationType(NotificationType.ON_ART_LESS_THAN_6_MONTHS);
		
		final SendPatientsAdapter patientsAdapter = new SendPatientsAdapter();
		final Message message = patientsAdapter.send(Arrays.asList(patientNotification));
		
		Assert.assertEquals(MessageStatus.OK, message.getMessageStatus());
	}
}
