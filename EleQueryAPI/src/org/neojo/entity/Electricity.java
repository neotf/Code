package org.neojo.entity;

import java.io.Serializable;

public final class Electricity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Float normal;
	private Float airConditioner;

	public Electricity() {
	}

	public Electricity(Float normal, Float airConditioner) {
		super();
		this.normal = normal;
		this.airConditioner = airConditioner;
	}

	public Float getNormal() {
		return normal;
	}

	public void setNormal(Float normal) {
		this.normal = normal;
	}

	public Float getAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(Float airConditioner) {
		this.airConditioner = airConditioner;
	}

}
