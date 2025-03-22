/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.smartnotifier.api.dao.PatientNotificationDAO;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.NotificationType;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.util.ParamBuilder;
import org.openmrs.module.smartnotifier.api.util.QueryUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stélio Moiane
 */
@Transactional
public class PatientsOnArtLessThanSixMonthsServiceImpl extends BaseOpenmrsService implements PatientsOnArtLessThanSixMonthsService {
	
	private PatientNotificationDAO patientNotificationDAO;
	
	public void setPatientNotificationDAO(final PatientNotificationDAO patientNotificationDAO) {
		this.patientNotificationDAO = patientNotificationDAO;
	}
	
	@Override
	public List<PatientNotification> process(final LocalDate endDate, final Location location) throws BusinessException {
		
		final Map<String, Object> params = new ParamBuilder().add("endDate", endDate).add("location", location.getId())
		        .getParams();
		
		final List<PatientNotification> patientNotifications = this.patientNotificationDAO.getPatientsToNotify(
		    QueryUtil.loadQuery(PatientsOnArtLessThanSixMonthsService.PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS), params);
		
		for (final PatientNotification patientNotification : patientNotifications) {
			
			LocalDate appointmentDate = patientNotification.getAppointmentDate();
			
			if (DayOfWeek.SATURDAY.equals(appointmentDate.getDayOfWeek())) {
				appointmentDate = appointmentDate.plusDays(2);
			}
			
			if (DayOfWeek.SUNDAY.equals(appointmentDate.getDayOfWeek())) {
				appointmentDate = appointmentDate.plusDays(1);
			}
			
			patientNotification.setAppointmentDate(appointmentDate);
			patientNotification.setNotificationType(NotificationType.ON_ART_LESS_THAN_6_MONTHS);
			patientNotification.setNotificationStatus(NotificationStatus.PENDING);
			
			this.patientNotificationDAO.savePatientNotification(patientNotification);
		}
		
		return patientNotifications;
	}
}
