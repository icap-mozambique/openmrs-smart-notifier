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
public interface ProcessPatientsWithTwoConsecutivesPickupsWithoutConsultationUseCase {
	
	String PATIENTS_WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION = "NOTIFICATION/PATIENTS_WITH_TWO_CONSECUTIVE_PICKUPS_WITHOUT_CONSULTATION.sql";
	
	List<PatientNotification> process(LocalDate endDate, Location location) throws BusinessException;
	
}
