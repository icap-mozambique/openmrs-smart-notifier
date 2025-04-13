/**
 *
 */
package org.openmrs.module.smartnotifier.api.infrastructure.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openmrs.module.smartnotifier.api.common.BusinessException;
import org.openmrs.util.OpenmrsClassLoader;

/**
 * @author Stélio Moiane
 */
public class QueryUtil {
	
	public static String loadQuery(final String filePath) throws BusinessException {

		final InputStream inputStream = OpenmrsClassLoader.getInstance().getResourceAsStream(filePath);

		if (inputStream == null) {
			throw new BusinessException("File not found!");
		}

		final StringBuilder stringBuilder = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line + " \n");
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();

	}
}
