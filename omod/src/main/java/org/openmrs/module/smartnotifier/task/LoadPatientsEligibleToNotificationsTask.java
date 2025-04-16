/**
 *
 */
package org.openmrs.module.smartnotifier.task;

import java.time.LocalDate;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsDefaultersForFiveDaysUseCase;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase;
import org.openmrs.module.smartnotifier.api.application.in.ProcessPatientsOnArtLessThanSixMonthsUseCase;
import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stélio Moiane
 */
public class LoadPatientsEligibleToNotificationsTask extends AbstractTask {
	
	private static final Logger log = LoggerFactory.getLogger(LoadPatientsEligibleToNotificationsTask.class);
	
	@Override
	public void execute() {
		
		final ProcessPatientsOnArtLessThanSixMonthsUseCase processPatientsOnArtLessThanSixMonthsService = Context
		        .getService(ProcessPatientsOnArtLessThanSixMonthsUseCase.class);
		
		final ProcessPatientsDefaultersForFiveDaysUseCase processPatientsDefaultersForFiveDaysUseCase = Context
		        .getService(ProcessPatientsDefaultersForFiveDaysUseCase.class);
		
		final ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase processPatientsDefaultersButNotifiedThreeDaysAgoUseCase = Context
		        .getService(ProcessPatientsDefaultersButNotifiedThreeDaysAgoUseCase.class);
		
		final ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase processPatientsOnArtEligibleForViralLoadCollectionUseCase = Context
		        .getService(ProcessPatientsOnArtEligibleForViralLoadCollectionUseCase.class);
		
		final Location location = Context.getLocationService().getDefaultLocation();
		final LocalDate now = LocalDate.now();
		
		try {
			LoadPatientsEligibleToNotificationsTask.log.info("The load patients task started.....");
			
			processPatientsOnArtLessThanSixMonthsService.process(now, location);
			processPatientsDefaultersForFiveDaysUseCase.process(now, location);
			processPatientsDefaultersButNotifiedThreeDaysAgoUseCase.process(now, location);
			processPatientsOnArtEligibleForViralLoadCollectionUseCase.process(now, location);
			
			LoadPatientsEligibleToNotificationsTask.log.info("The load patients task finished.....");
		}
		catch (final BusinessException e) {
			LoadPatientsEligibleToNotificationsTask.log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
