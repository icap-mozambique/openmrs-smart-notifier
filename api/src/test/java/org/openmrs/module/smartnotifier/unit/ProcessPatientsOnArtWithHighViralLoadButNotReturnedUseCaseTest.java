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
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.common.ParamBuilder;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtWithHighViralLoadButNotReturnedService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */

public class ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCaseTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsOnArtWithHighViralLoadButNotReturnedService processPatientsOnArtWithHighViralLoadButNotReturnedUseCase;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Test
	public void shouldPatientsOnArtWithHighViralLoadButNotReturned() throws BusinessException {
		final LocalDate endDate = LocalDate.of(2025, 04, 23);
		final Location location = new Location();
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setPhoneNumber("822546100");
		patientNotification.setAppointmentDate(DateUtil.toTimestamp(endDate));
		
		Mockito.when(
		    this.patientNotificationPort
		            .getPatientsToNotify(
		                ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase.PATIENTS_ON_ART_WITH_HIGH_VL_NOTIFIED_BUT_NOT_RETURNED,
		                new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams()))
		        .thenReturn(Arrays.asList(patientNotification));
		
		final List<PatientNotification> patientsToNotify = this.processPatientsOnArtWithHighViralLoadButNotReturnedUseCase
		        .process(endDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification notification : patientsToNotify) {
			Assert.assertEquals(NotificationType.ON_ART_WITH_HIGH_VIRAL_LOAD_NOT_RETURNED,
			    notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			Assert.assertNotNull(notification.getSuggestedAppointmentDate());
			
			Mockito.verify(this.patientNotificationPort, Mockito.times(1)).savePatientNotification(notification);
		}
	}
}
