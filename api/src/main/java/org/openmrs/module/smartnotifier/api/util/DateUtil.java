/**
 *
 */
package org.openmrs.module.smartnotifier.api.util;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author Stélio Moiane
 */
public class DateUtil {
	
	public static LocalDate toLocalDate(final Timestamp timestamp) {
		return timestamp.toLocalDateTime().toLocalDate();
	}
	
	public static Timestamp toTimestamp(final LocalDate localDate) {
		return Timestamp.valueOf(localDate.atStartOfDay());
	}
}
