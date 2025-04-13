/**
 *
 */
package org.openmrs.module.smartnotifier.api.application.out;

import java.util.List;

import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface SendPatientPort {
	
	Message send(List<PatientNotification> patientNotifications) throws BusinessException;
	
}
