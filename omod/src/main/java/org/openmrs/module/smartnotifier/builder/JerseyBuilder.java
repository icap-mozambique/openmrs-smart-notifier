/**
 *
 */
package org.openmrs.module.smartnotifier.builder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * @author Stélio Moiane
 */
public class JerseyBuilder {
	
	private ClassLoader classLoader;
	
	public Client createClient() {
		this.classLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		
		return ClientBuilder.newClient();
	}
	
	public void closeClient(final Client client) {
		client.close();
		Thread.currentThread().setContextClassLoader(this.classLoader);
	}
}
