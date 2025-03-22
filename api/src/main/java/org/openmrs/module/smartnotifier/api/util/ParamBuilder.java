/**
 *
 */
package org.openmrs.module.smartnotifier.api.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stélio Moiane
 */
public class ParamBuilder {
	
	Map<String, Object> params;
	
	public ParamBuilder() {
		this.params = new HashMap<String, Object>();
	}
	
	public ParamBuilder add(final String key, final Object value) {
		this.params.put(key, value);
		return this;
	}
	
	public Map<String, Object> getParams() {
		return this.params;
	}
}
