/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface PatientsOnArtLessThanSixMonthsService extends OpenmrsService {
	
	String PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS = "NOTIFICATION/PATIENTS_ON_ART_LESS_THAN_SIX_MONTHS.sql";
	
	public List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
