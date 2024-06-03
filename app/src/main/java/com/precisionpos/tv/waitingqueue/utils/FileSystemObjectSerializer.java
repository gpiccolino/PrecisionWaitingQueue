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

import android.content.Context;
import android.util.Log;

import com.precisionpos.tv.waitingqueue.app.TVWaitQueueApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Used to save serialized objects to the file system
 * @author Gino
 *
 */
public class FileSystemObjectSerializer {
	
	
	// Singleton instance		
	private static FileSystemObjectSerializer singleton;

	/**
	 * Private constructor to enforce singleton
	 */
	private FileSystemObjectSerializer() {}
	
	/**
	 * Factory method to enforce singleton
	 * @return
	 */
	public static FileSystemObjectSerializer getInstance() {
		if(singleton == null) {
			singleton = new FileSystemObjectSerializer();
		}
		return singleton;
	}

	public boolean persistObjectToFileSystem(Serializable object, String fileName) {
		return (Boolean) performFileOperation(object, fileName, 1);
	}

	
	/**
	 * Synchronize the read / write operations
	 * @param object
	 * @param fileName
	 * @param requestType
	 * @return
	 */
	public synchronized Object performFileOperation(Serializable object, String fileName, int requestType) {
		// Write to the file system		
		if(requestType == 1) {
			// Input and output streams
			BufferedOutputStream bos = null;
			FileOutputStream fos  = null;			
			ObjectOutputStream os = null;

			// @author gpiccolino
			// Save a backup in case serialization fails
			File fileBak = null;

			try {
				// So we could overwrite the file
				File file = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName);

				if(file.exists()) {

					// Backup the file for failover if this is either
					// a station config or kiosk config file
					// Config files have license information
					if(fileName.equalsIgnoreCase(StationProfileConfigSession.STATION_CONFIG_FILENAME)) {
						fileBak = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName + "_bak");
						if(fileBak.exists()) {
							fileBak.delete();
						}
						file.renameTo(fileBak);
					}
					else {
						file.delete();
					}
				}


				// Open the file and write the object
				fos = TVWaitQueueApplication.getAppContext().openFileOutput(fileName, Context.MODE_PRIVATE);
				bos = new BufferedOutputStream(fos);
				os  = new ObjectOutputStream(bos);
				
				// Write the object
				os.writeObject(object);

				// Will this finalize the write
				os.flush();

				// If we got this far that means the object successfully
				// serialized and save to the file system
				// Clean up and remove the backups
				if(fileName.equalsIgnoreCase(StationProfileConfigSession.STATION_CONFIG_FILENAME)) {

					fileBak = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName + "_bak");

					// If the license became invalid for some reason, then try to get the backup
					if(fileBak.exists() && StationProfileConfigSession.getProfileDetailsBean().getBusinessID() == 0) {
						File fileOriginal = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName);
						if(fileOriginal.exists()) {
							fileOriginal.delete();
						}
						fileBak.renameTo(fileOriginal);
					}
					// Station is valid, so we don't need the backup
					else if(fileBak.exists()) {
						fileBak.delete();
					}
				}

				return true;
			}
			catch(Exception e) {
				// There was an error in serialization, as a result please failover
				// by renaming the backup to the original file name
				if(fileBak != null) {
					fileBak = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName + "_bak");
					if(fileBak.exists()) {
						File fileOriginal = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName);
						if(fileOriginal.exists()) {
							fileOriginal.delete();
						}
						fileBak.renameTo(fileOriginal);
					}
				}

				e.printStackTrace();
				Log.e("Serialization Error", e.getMessage());
			}
			finally {

				// @author gpiccolino
				// Different way to close the streams
				FileSystemObjectSerializer.close(os,bos,fos);
			}
			return false;			
		}
		// Read from the file system
		else {
			Object obj = null;
			
			// @since 02/25/18
			// @author gpiccolino
			// Ensure closure
			FileInputStream fis = null;
			ObjectInputStream is = null;
			
			try {
				File file = TVWaitQueueApplication.getAppContext().getFileStreamPath(fileName);
				if(file.exists()) {
					fis = TVWaitQueueApplication.getAppContext().openFileInput(fileName);
					is = new ObjectInputStream(fis);
					
					obj = (Object) is.readObject();
				}
			}
			catch(Exception e) {
				StringWriter sw = new StringWriter();
		        PrintWriter pw = new PrintWriter( sw );
		       
		        /* Print out the exception */
		        e.printStackTrace(pw);
		       
		        /* Our return variable */
		        String trace = sw.toString();
		        
				Log.e("Serialization Error", trace);
			}
			finally {
				// @since 08/06/18
				// @author gpiccolino
				// Different way to close the streams
				FileSystemObjectSerializer.close(is,fis);
			}
			return obj;			
		}
	}

	/**
	 * Close the output streams
	 * @param array
	 */
	public static void close(AutoCloseable... array) {
	    for (AutoCloseable c : array) {
	    	if(c != null) {
		        try {c.close();}
		        catch (IOException e) {
		        	Log.e("ERROR CLOSING STREAM", e.getMessage());
		        }
		        catch (Exception e) {
		        	Log.e("ERROR CLOSING STREAM", e.getMessage());
		        }
	    	}
	    }
	}

	
	public Object retrieveObjectFromFileSystem(String fileName) {
		return performFileOperation(null, fileName, 2);
	}
}
