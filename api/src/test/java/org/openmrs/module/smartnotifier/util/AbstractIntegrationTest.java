/**
 *
 */
package org.openmrs.module.smartnotifier.util;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.TestUtil;

/**
 * @author Stélio Moiane
 */
public abstract class AbstractIntegrationTest extends BaseModuleContextSensitiveTest {
	
	@Override
	public Boolean useInMemoryDatabase() {
		final Properties properties = TestUtil.getRuntimeProperties(this.getWebappName());
		
		System.setProperty("databaseUrl", properties.getProperty("connection.url"));
		System.setProperty("databaseUsername", properties.getProperty("connection.username"));
		System.setProperty("databasePassword", properties.getProperty("connection.password"));
		
		System.setProperty("databaseDriver", "com.mysql.jdbc.Driver");
		System.setProperty("databaseDialect", "org.hibernate.dialect.MySQLDialect");
		
		return false;
	}
	
	protected abstract String username();
	
	protected abstract String password();
	
	@Before
	public void initialize() throws ContextAuthenticationException {
		Context.openSession();
		Context.authenticate(this.username(), this.password());
	}
	
	@After
	public void close() {
		Context.closeSession();
	}
	
	@Override
	public void deleteAllData() {
		System.out.println("Do not delete DATA...");
	}
	
	@Override
	public void baseSetupWithStandardDataAndAuthentication() throws SQLException {
		System.out.println("Do not add DATA...");
	}
}
