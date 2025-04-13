/**
 *
 */
package org.openmrs.module.smartnotifier.api.application.in;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface ProcessPatientsDefaultersForFiveDaysUseCase {
	
	String PATIENTS_DEFAULTERS_FOR_FIVE_DAYS = "NOTIFICATION/PATIENTS_DEFAULTERS_FOR_FIVE_DAYS.sql";
	
	List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
