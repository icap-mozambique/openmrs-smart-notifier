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
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtLessThanSixMonthsUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.infrastructure.util.ParamBuilder;
import org.openmrs.module.smartnotifier.api.infrastructure.util.QueryUtil;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtLessThanSixMonthsService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */

public class ProcessPatientsOnArtLessThanSixMonthsServiceTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsOnArtLessThanSixMonthsService patientsOnArtLessThanSixMonthsService;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Test
	public void shouldProcessPatientsOnArtLessThanSixMonths() throws BusinessException {
		
		final LocalDate endDate = LocalDate.now();
		final Location location = new Location();
		final LocalDate appointmentDate = LocalDate.of(2025, 03, 22);
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setAppointmentDate(DateUtil.toTimestamp(appointmentDate));
		patientNotification.setPhoneNumber("840546824 ");
		
		Mockito.when(
		    this.patientNotificationPort.getPatientsToNotify(
		        QueryUtil.loadQuery(ProcessPatientsOnArtLessThanSixMonthsUseCase.PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS),
		        new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams())).thenReturn(
		    Arrays.asList(patientNotification));
		
		final List<PatientNotification> patientNotifications = this.patientsOnArtLessThanSixMonthsService.process(endDate,
		    location);
		
		Assert.assertFalse(patientNotifications.isEmpty());
		
		for (final PatientNotification notification : patientNotifications) {
			Assert.assertEquals(appointmentDate.minusDays(1),
			    DateUtil.toLocalDate(notification.getSuggestedAppointmentDate()));
			Assert.assertEquals(NotificationType.ON_ART_LESS_THAN_6_MONTHS, notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			
			Mockito.verify(this.patientNotificationPort, Mockito.times(1)).savePatientNotification(patientNotification);
		}
	}
}
