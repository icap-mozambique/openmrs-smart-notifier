/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.util.List;

import org.openmrs.module.smartnotifier.api.dao.PatientNotificationDAO;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.model.NotificationStatus;
import org.openmrs.module.smartnotifier.api.model.PatientNotification;
import org.openmrs.module.smartnotifier.api.out.Message;
import org.openmrs.module.smartnotifier.api.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.out.SendPatientPort;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stélio Moiane
 */
@Transactional
public class SendPatientsToBeNotifiedServiceImpl implements SendPatientsToBeNotifiedService {
	
	private PatientNotificationDAO patientNotificationDAO;
	
	private SendPatientPort sendPatientPort;
	
	@Override
	public List<PatientNotification> send() throws BusinessException {
		
		final List<PatientNotification> patientNotifications = this.patientNotificationDAO.getPendingPatientNotifications();
		
		if (patientNotifications.isEmpty()) {
			throw new BusinessException("There is no pending notifications to be sent.");
		}
		
		final Message message = this.sendPatientPort.send(patientNotifications);
		
		if (MessageStatus.OK.equals(message.getMessageStatus())) {
			for (final PatientNotification patientNotification : patientNotifications) {
				patientNotification.setNotificationStatus(NotificationStatus.SENT);
				
				this.patientNotificationDAO.savePatientNotification(patientNotification);
			}
		}
		
		return patientNotifications;
	}
	
	public void setPatientNotificationDAO(final PatientNotificationDAO patientNotificationDAO) {
		this.patientNotificationDAO = patientNotificationDAO;
	}
	
	@Override
	public void setSendPatientPort(final SendPatientPort sendPatientPort) {
		this.sendPatientPort = sendPatientPort;
	}
}
