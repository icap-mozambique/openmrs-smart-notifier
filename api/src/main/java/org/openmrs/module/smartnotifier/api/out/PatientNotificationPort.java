/**
 *
 */
package org.openmrs.module.smartnotifier.api.out;

import java.util.List;
import java.util.Map;

import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;

/**
 * @author Stélio Moiane
 */
public interface PatientNotificationPort {
	
	public PatientNotification savePatientNotification(final PatientNotification patientNotification)
	        throws BusinessException;
	
	public List<PatientNotification> getPatientsToNotify(final String query, final Map<String, Object> params)
	        throws BusinessException;
	
	public List<PatientNotification> getPendingPatientNotifications() throws BusinessException;
}
