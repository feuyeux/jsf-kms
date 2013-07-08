package org.feuyeux.air.jsf.kms.dao.util;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class KmsConditionPair {
	private String parameter;
	private Object value;

	public KmsConditionPair(String parameter, Object value) {
		this.parameter = parameter;
		this.value = value;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
