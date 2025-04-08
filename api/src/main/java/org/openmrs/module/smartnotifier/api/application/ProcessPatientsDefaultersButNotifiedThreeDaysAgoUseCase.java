/**
 *
 */
package org.openmrs.module.smartnotifier.api.application;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase {
	
	String PATIENTS_DEFAULTERS_BUT_NOTIFIED_THREE_DAYS_AGO = "NOTIFICATION/PATIENTS_DEFAULTERS_BUT_NOTIFIED_THREE_DAYS_AGO.sql";
	
	List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
