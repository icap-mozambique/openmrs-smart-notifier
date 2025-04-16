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
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.ParamBuilder;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.domain.NotificationType;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.ProcessPatientsOnArtEligibleForViralLoadCollectionService;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */
public class ProcessPatientsOnArtEligibleForViralLoadCollectionUseCaseTest extends AbstractUnitTest {
	
	@InjectMocks
	private ProcessPatientsOnArtEligibleForViralLoadCollectionService processPatientsOnArtEligibleForViralLoadCollectionUseCase;
	
	@Mock
	private PatientNotificationPort patientNotificationPort;
	
	@Test
	public void shouldProcessPatientsOnArtEligibleForViralLoadCollection() throws BusinessException {
		
		final LocalDate localDate = LocalDate.now();
		final Location location = new Location();
		
		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setPhoneNumber("822546100");
		
		Mockito.when(
		    this.patientNotificationPort.getPatientsToNotify(
		        ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase.PATIENTS_OR_ART_AND_ELIGIBLE_FOR_VL_COLLECTION,
		        new ParamBuilder().add("endDate", localDate).add("location", location.getId()).getParams())).thenReturn(
		    Arrays.asList(patientNotification));
		
		final List<PatientNotification> patientsToNotify = this.processPatientsOnArtEligibleForViralLoadCollectionUseCase
		        .process(localDate, location);
		
		Assert.assertFalse(patientsToNotify.isEmpty());
		
		for (final PatientNotification notification : patientsToNotify) {
			
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());
			Assert.assertEquals(NotificationType.ON_ART_ELIGIBLE_FOR_VIRAL_LOAD_COLLECTION,
			    notification.getNotificationType());
			
			Mockito.verify(this.patientNotificationPort, Mockito.times(1)).savePatientNotification(notification);
		}
	}
}
