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
import org.openmrs.module.smartnotifier.api.dao.PatientNotificationDAO;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.service.PatientsOnArtLessThanSixMonthsService;
import org.openmrs.module.smartnotifier.api.service.PatientsOnArtLessThanSixMonthsServiceImpl;
import org.openmrs.module.smartnotifier.api.util.DateUtil;
import org.openmrs.module.smartnotifier.api.util.ParamBuilder;
import org.openmrs.module.smartnotifier.api.util.QueryUtil;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */

public class PatientsOnArtLessThanSixMonthsServiceTest extends AbstractUnitTest {

	@InjectMocks
	private PatientsOnArtLessThanSixMonthsServiceImpl patientsOnArtLessThanSixMonthsService;

	@Mock
	private PatientNotificationDAO patientNotificationDAO;

	@Test
	public void shouldProcessPatientsOnArtLessThanSixMonths() throws BusinessException {

		final LocalDate endDate = LocalDate.now();
		final Location location = new Location();
		final LocalDate appointmentDate = LocalDate.of(2025, 03, 22);

		final PatientNotification patientNotification = new PatientNotification();
		patientNotification.setAppointmentDate(DateUtil.toTimestamp(appointmentDate));

		Mockito.when(
				this.patientNotificationDAO.getPatientsToNotify(
						QueryUtil.loadQuery(PatientsOnArtLessThanSixMonthsService.PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS),
						new ParamBuilder().add("endDate", endDate).add("location", location.getId()).getParams()))
		.thenReturn(Arrays.asList(patientNotification));

		final List<PatientNotification> patientNotifications = this.patientsOnArtLessThanSixMonthsService.process(endDate,
				location);

		Assert.assertFalse(patientNotifications.isEmpty());

		patientNotifications.forEach(notification -> {
			Assert.assertEquals(appointmentDate.minusDays(1), DateUtil.toLocalDate(notification.getSuggestedAppointmentDate()));
			Assert.assertEquals(NotificationType.ON_ART_LESS_THAN_6_MONTHS, notification.getNotificationType());
			Assert.assertEquals(NotificationStatus.PENDING, notification.getNotificationStatus());

			Mockito.verify(this.patientNotificationDAO, Mockito.times(1)).savePatientNotification(patientNotification);
		});
	}
}
