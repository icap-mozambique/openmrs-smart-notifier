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
public interface ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase {
	
	String PATIENTS_OR_ART_AND_ELIGIBLE_FOR_VL_COLLECTION = "NOTIFICATION/PATIENTS_OR_ART_AND_ELIGIBLE_FOR_VL_COLLECTION.sql";
	
	List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
