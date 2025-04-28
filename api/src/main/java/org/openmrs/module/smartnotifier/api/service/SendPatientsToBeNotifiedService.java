/**
 *
 */
package org.openmrs.module.smartnotifier.api.service;

import java.time.LocalDate;
import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.smartnotifier.api.application.in.SendPatientsToBeNotifiedUseCase;
import org.openmrs.module.smartnotifier.api.application.out.Message;
import org.openmrs.module.smartnotifier.api.application.out.MessageStatus;
import org.openmrs.module.smartnotifier.api.application.out.PatientNotificationPort;
import org.openmrs.module.smartnotifier.api.application.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.module.smartnotifier.api.common.DateUtil;
import org.openmrs.module.smartnotifier.api.domain.NotificationStatus;
import org.openmrs.module.smartnotifier.api.infrastructure.entity.PatientNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Stélio Moiane
 */
@Service("smartnotifier.SendPatientsToBeNotifiedUseCase")
public class SendPatientsToBeNotifiedService extends BaseOpenmrsService implements SendPatientsToBeNotifiedUseCase {
	
	private PatientNotificationPort patientNotificationPort;
	
	private SendPatientPort sendPatientPort;
	
	@Autowired
	public SendPatientsToBeNotifiedService(final PatientNotificationPort patientNotificationPort) {
		this.patientNotificationPort = patientNotificationPort;
	}
	
	@Override
	public List<PatientNotification> send(final LocalDate sendDate) throws BusinessException {
		
		final List<PatientNotification> patientNotifications = this.patientNotificationPort.getPendingPatientNotifications();
		
		if (patientNotifications.isEmpty()) {
			throw new BusinessException("There is no pending notifications to be sent.");
		}
		
		final Message message = this.sendPatientPort.send(patientNotifications);
		
		if (MessageStatus.OK.equals(message.getMessageStatus())) {
			for (final PatientNotification patientNotification : patientNotifications) {
				patientNotification.setNotificationStatus(NotificationStatus.SENT);
				patientNotification.setNotificationSentDate(DateUtil.toTimestamp(sendDate));
				
				this.patientNotificationPort.savePatientNotification(patientNotification);
			}
		}
		
		return patientNotifications;
	}
	
	@Override
	public void setSendPatientPort(final SendPatientPort sendPatientPort) {
		this.sendPatientPort = sendPatientPort;
	}
}
