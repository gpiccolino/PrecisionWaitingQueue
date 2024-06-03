/*******************************************************************************
 * Copyright (c) Precision Software Innovations LLC
 * All rights reserved. This program's source code
 * and the accompanying materials are exclusive rights
 * of Gino Piccolino (i.e. PrecisionPOS).  Modification,
 * copying, or altering source files is strictly prohibited.
 *
 * Contributors:
 *     Gino Piccolino - API, implementation, graphics, documentation
 *******************************************************************************/
package com.precisionpos.tv.waitingqueue.beans;

import java.io.Serializable;

/**
 * Station bean for TV waiting queue
 */
public class StationTVProfileSetterBean implements Serializable {


	// Business related - DEFAULT FOR NOW
	private int encryptedBusinessID				= 0;
	private int encryptedStoreFrontCD			= 0;

	// Connection related - DEFAULT FOR NOW
	private String encryptedConnectionURL 		= "https://cloud1.o-ez.com";
	private String encryptedConnectionUsername 	= "";
	private String encryptedConnectionPassword 	= "";

	private long refreshRateInMilliseconds		= 60000;

	public int getEncryptedBusinessID() {
		return encryptedBusinessID;
	}

	public void setEncryptedBusinessID(int encryptedBusinessID) {
		this.encryptedBusinessID = encryptedBusinessID;
	}

	public int getEncryptedStoreFrontCD() {
		return encryptedStoreFrontCD;
	}

	public void setEncryptedStoreFrontCD(int encryptedStoreFrontCD) {
		this.encryptedStoreFrontCD = encryptedStoreFrontCD;
	}

	public String getEncryptedConnectionURL() {
		return encryptedConnectionURL;
	}

	public void setEncryptedConnectionURL(String encryptedConnectionURL) {
		this.encryptedConnectionURL = encryptedConnectionURL;
	}

	public String getEncryptedConnectionUsername() {
		return encryptedConnectionUsername;
	}

	public void setEncryptedConnectionUsername(String encryptedConnectionUsername) {
		this.encryptedConnectionUsername = encryptedConnectionUsername;
	}

	public String getEncryptedConnectionPassword() {
		return encryptedConnectionPassword;
	}

	public void setEncryptedConnectionPassword(String encryptedConnectionPassword) {
		this.encryptedConnectionPassword = encryptedConnectionPassword;
	}

	public long getRefreshRateInMilliseconds() {
		return refreshRateInMilliseconds;
	}

	public void setRefreshRateInMilliseconds(long refreshRateInMilliseconds) {
		this.refreshRateInMilliseconds = refreshRateInMilliseconds;
	}
}