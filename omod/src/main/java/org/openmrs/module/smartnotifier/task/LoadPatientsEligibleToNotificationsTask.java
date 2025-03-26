/**
 *
 */
package org.openmrs.module.smartnotifier.task;

import java.time.LocalDate;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.smartnotifier.api.exception.BusinessException;
import org.openmrs.module.smartnotifier.api.service.PatientsOnArtLessThanSixMonthsService;
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
		
		final PatientsOnArtLessThanSixMonthsService patientsOnArtLessThanSixMonthsService = Context
		        .getService(PatientsOnArtLessThanSixMonthsService.class);
		final Location location = Context.getLocationService().getDefaultLocation();
		final LocalDate now = LocalDate.now();
		
		try {
			LoadPatientsEligibleToNotificationsTask.log.info("The load patients task started.....");
			
			patientsOnArtLessThanSixMonthsService.process(now, location);
			
			LoadPatientsEligibleToNotificationsTask.log.info("The load patients task finished.....");
		}
		catch (final BusinessException e) {
			LoadPatientsEligibleToNotificationsTask.log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
