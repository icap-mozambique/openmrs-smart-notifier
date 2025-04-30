/**
 *
 */
package org.openmrs.module.smartnotifier.builder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * @author Stélio Moiane
 */
public class JerseyClient {
	
	private static Client client;
	
	private static ClassLoader classLoader;
	
	public static Client getClient() {
		
		if (JerseyClient.client == null) {
			JerseyClient.classLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(JerseyClient.class.getClassLoader());
			
			JerseyClient.client = ClientBuilder.newClient();
		}
		
		return JerseyClient.client;
	}
	
	public static void closeClient() {
		JerseyClient.client.close();
		JerseyClient.client = null;
		
		Thread.currentThread().setContextClassLoader(JerseyClient.classLoader);
	}
}
