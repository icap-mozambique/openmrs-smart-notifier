/**
 *
 */
package org.openmrs.module.smartnotifier.api.application.in;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface ProcessPatientsOnArtLessThanSixMonthsUseCase extends OpenmrsService {
	
	String PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS = "NOTIFICATION/PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS.sql";
	
	public List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
