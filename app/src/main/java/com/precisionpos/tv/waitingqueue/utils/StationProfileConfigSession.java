/*******************************************************************************
 * Copyright (c)  PrecisionPOS Corporation
 * All rights reserved. This program's source code
 * and the accompanying materials are exclusive rights
 * of Gino Piccolino (i.e. PrecisionPOS).  Modification,
 * copying, or altering source files is strictly prohibited.
 *
 * Contributors:
 *     Gino Piccolino - API, implementation, graphics, documentation
 *******************************************************************************/
package com.precisionpos.tv.waitingqueue.utils;

import com.precisionpos.tv.waitingqueue.beans.StationProfileBean;

import java.io.Serializable;


/**
 * Holds the details about the current application session
 * @author Gino
 *
 */
public class StationProfileConfigSession implements Serializable {

	private static final long serialVersionUID = 0L;
	public static final String STATION_CONFIG_FILENAME = "TVWaitingQueueProfile.oez";

	// Singleton method
	private static StationProfileBean profileDetailsBean;
		

	/**
	 * Enforces the singleton
	 */
	private StationProfileConfigSession() {}


	/**
	 * Method to get the station bean from the file system if not 
	 * already in the session
	 * 
	 * @return
	 */
	public static StationProfileBean getProfileDetailsBean() {

		if(profileDetailsBean == null || profileDetailsBean.getIsDefaultProfile()) {
			FileSystemObjectSerializer fsor = FileSystemObjectSerializer.getInstance();

			profileDetailsBean = (StationProfileBean)fsor.retrieveObjectFromFileSystem(STATION_CONFIG_FILENAME);

			if(profileDetailsBean == null) {
				profileDetailsBean = new StationProfileBean();
				profileDetailsBean.setIsDefaultProfile(true);
			}
		}
		return profileDetailsBean;
	}


	/**
	 * Persist the station bean to the file system
	 * @param myProfile
	 */
	public static void persistStationBean(StationProfileBean myProfile) {
		// Save the latest in static
		profileDetailsBean = myProfile;

		// Persist to the database
		FileSystemObjectSerializer fsor = FileSystemObjectSerializer.getInstance();
		fsor.persistObjectToFileSystem(profileDetailsBean,  STATION_CONFIG_FILENAME);
	}

}
