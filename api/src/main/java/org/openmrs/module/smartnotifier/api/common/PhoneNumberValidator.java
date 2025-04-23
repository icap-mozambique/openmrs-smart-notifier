/**
 *
 */
package org.openmrs.module.smartnotifier.api.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stélio Moiane
 */
public class PhoneNumberValidator {
	
	private static final String PHONE_NUMBER_PATTERN = "^[0-9]{9}$";
	
	public static Boolean isValid(final String phoneNumber) {
		
		if (phoneNumber == null) {
			return Boolean.FALSE;
		}
		
		final Pattern pattern = Pattern.compile(PhoneNumberValidator.PHONE_NUMBER_PATTERN);
		
		final Matcher matcher = pattern.matcher(phoneNumber.trim());
		
		return matcher.matches();
	}
}
