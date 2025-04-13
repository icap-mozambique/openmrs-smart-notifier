/**
 *
 */
package org.openmrs.module.smartnotifier.api.application.in;

import java.util.List;

import org.openmrs.module.smartnotifier.api.application.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface SendPatientsToBeNotifiedUseCase {
	
	List<PatientNotification> send() throws BusinessException;
	
	void setSendPatientPort(SendPatientPort sendPatientPort);
	
}
