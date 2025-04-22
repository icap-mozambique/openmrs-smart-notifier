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
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.common.ParamBuilder;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCaseTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationService processPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Test
	public void processPatientsWithTwoConsecutivePickupsWithoutConsultation() throws BusinessException {
		
		final LocalDate endDate = LocalDate.of(2025, 04, 20);
		final Location location = new Location();
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setAppointmentDate(DateUtil.toTimestamp(endDate));
		patientNotification.setPhoneNumber("840546824");
		
		Mockito.when(
		    this.patientNotificationPort
		            .getPatientsToNotify(
		                ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase.PATIENTS_WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION,
		                new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams()))
		        .thenReturn(Arrays.asList(patientNotification));
		
		final List<PatientNotification> patientsToNotify = this.processPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase
		        .process(endDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification notificaton : patientsToNotify) {
			Assert.assertEquals(NotificationType.WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION,
			    notificaton.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notificaton.getNotificationStatus());
			
			Mockito.verify(this.patientNotificationPort, Mockito.times(1)).savePatientNotification(notificaton);
		}
	}
}
