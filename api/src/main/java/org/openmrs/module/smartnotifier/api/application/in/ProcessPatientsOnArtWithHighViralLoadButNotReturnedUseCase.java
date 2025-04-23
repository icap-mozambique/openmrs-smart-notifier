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
public interface ProcessPatientsOnArtWithHighViralLoadButNotReturnedUseCase {
	
	String PATIENTS_ON_ART_WITH_HIGH_VL_NOTIFIED_BUT_NOT_RETURNED = "NOTIFICATION/PATIENTS_ON_ART_WITH_HIGH_VL_NOTIFIED_BUT_NOT_RETURNED.sql";
	
	List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
