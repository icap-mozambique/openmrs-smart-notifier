/**
 *
 */
package org.openmrs.module.smartnotifier.api.application;

import java.util.List;

import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.SendPatientPort;

/**
 * @author Stélio Moiane
 */
public interface SendPatientsToBeNotifiedUseCase {
	
	List<PatientNotification> send() throws BusinessException;
	
	void setSendPatientPort(SendPatientPort sendPatientPort);
	
}
