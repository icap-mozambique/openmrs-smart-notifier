/**
 *
 */
package org.openmrs.module.smartnotifier.task;

import java.time.LocalDate;

import org.openmrs.api.context.Context;
import org.openmrs.module.smartnotifier.adapter.SendPatientsToViamoAdapter;
import org.openmrs.module.smartnotifier.api.application.in.SendPatientsToBeNotifiedUseCase;
import org.openmrs.module.smartnotifier.api.application.out.SendPatientPort;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
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
		final SendPatientsToBeNotifiedUseCase sendPatientsToBeNotifiedService = Context
				.getService(SendPatientsToBeNotifiedUseCase.class);

		final SendPatientPort sendPatientPort = new SendPatientsToViamoAdapter();

		try {
			SendPatientsToNotifyTask.log.info("Send patients to notify list process started");

			sendPatientsToBeNotifiedService.setSendPatientPort(sendPatientPort);

			final LocalDate sendDate = LocalDate.now();
			sendPatientsToBeNotifiedService.send(sendDate);

			SendPatientsToNotifyTask.log.info("Send patients to notify list process finished");
		} catch (final BusinessException e) {
			SendPatientsToNotifyTask.log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
