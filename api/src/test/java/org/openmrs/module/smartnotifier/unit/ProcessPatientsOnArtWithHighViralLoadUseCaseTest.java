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
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtWithHighViralLoadUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.common.ParamBuilder;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtWithHighViralLoadService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */

public class ProcessPatientsOnArtWithHighViralLoadUseCaseTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsOnArtWithHighViralLoadService processPatientsOnArtWithHighViralLoadUseCase;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Test
	public void shouldProcessPatientsOnARTWithHighViralLoad() throws BusinessException {
		final LocalDate endDate = LocalDate.of(2025, 04, 23);
		final Location location = new Location();
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setPhoneNumber("822546100");
		patientNotification.setAppointmentDate(DateUtil.toTimestamp(endDate));
		
		Mockito.when(
		    this.patientNotificationPort.getPatientsToNotify(
		        ProcessPatientsOnArtWithHighViralLoadUseCase.PATIENTS_ON_ART_WITH_HIGH_VIRAL_LOAD,
		        new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams())).thenReturn(
		    Arrays.asList(patientNotification));
		
		final List<PatientNotification> patientsToNotify = this.processPatientsOnArtWithHighViralLoadUseCase.process(
		    endDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification notification : patientsToNotify) {
			Assert.assertEquals(NotificationType.ON_ART_WITH_HIGH_VIRAL_LOAD, notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			Assert.assertNotNull(notification.getSuggestedAppointmentDate());
			
			Mockito.verify(this.patientNotificationPort, Mockito.times(1)).savePatientNotification(notification);
		}
	}
}
