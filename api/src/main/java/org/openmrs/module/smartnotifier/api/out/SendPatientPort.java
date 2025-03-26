/**
 *
 */
package org.openmrs.module.smartnotifier.api.out;

import java.util.List;

import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface SendPatientPort {
	
	Message send(List<PatientNotification> patientNotifications) throws BusinessException;
	
}
