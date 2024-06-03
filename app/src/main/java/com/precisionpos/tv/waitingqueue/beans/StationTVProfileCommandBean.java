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
public class StationTVProfileCommandBean implements Serializable {


	public static final int COMMAND_REFRESH	 	= 87212211;
	public static final int COMMAND_SETACTIVE	= 87212221;
	public static final int COMMAND_SETINACTIVE	= 87212231;

	// Command type
	private int commandType					   = 0;

	// Username / Password
	private String encryptedConnectionUsername 	= "";
	private String encryptedConnectionPassword 	= "";

	public int getCommandType() {
		return commandType;
	}

	public void setCommandType(int commandType) {
		this.commandType = commandType;
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
}