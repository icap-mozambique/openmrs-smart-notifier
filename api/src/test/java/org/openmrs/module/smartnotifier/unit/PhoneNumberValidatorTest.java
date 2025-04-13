/**
 *
 */
package org.openmrs.module.smartnotifier.unit;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.module.smartnotifier.api.common.PhoneNumberValidator;
import org.openmrs.module.smartnotifier.util.AbstractUnitTest;

/**
 * @author Stélio Moiane
 */
public class PhoneNumberValidatorTest extends AbstractUnitTest {
	
	@Test
	public void shouldValidatePhoneNumber() {
		final Boolean isValid = PhoneNumberValidator.isValidate("840546824");
		
		Assert.assertTrue(isValid);
	}
}
