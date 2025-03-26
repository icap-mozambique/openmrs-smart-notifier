/**
 *
 */
package org.openmrs.module.smartnotifier.task;

import org.openmrs.api.context.Context;
import org.openmrs.module.smartnotifier.adapter.SendPatientsAdapter;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.service.SendPatientsToBeNotifiedService;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stélio Moiane
 */
public class SendPatientsToNotifyTask extends AbstractTask {
	
	private static final Logger log = LoggerFactory.getLogger(SendPatientsToNotifyTask.class);
	
	@Override
	public void execute() {
		final SendPatientsToBeNotifiedService sendPatientsToBeNotifiedService = Context
		        .getService(SendPatientsToBeNotifiedService.class);
		
		try {
			SendPatientsToNotifyTask.log.info("Send patients to notify list process started");
			
			sendPatientsToBeNotifiedService.setSendPatientPort(new SendPatientsAdapter());
			
			sendPatientsToBeNotifiedService.send();
			
			SendPatientsToNotifyTask.log.info("Send patients to notify list process finished");
		}
		catch (final BusinessException e) {
			SendPatientsToNotifyTask.log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
