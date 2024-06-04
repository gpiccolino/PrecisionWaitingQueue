package com.precisionpos.tv.waitingqueue.communication;

import android.content.Context;
import android.util.Log;

import com.precisionpos.tv.waitingqueue.beans.StationProfileBean;
import com.precisionpos.tv.waitingqueue.beans.StationTVProfileCommandBean;
import com.precisionpos.tv.waitingqueue.beans.StationTVProfileSetterBean;
import com.precisionpos.tv.waitingqueue.utils.EncryptString;
import com.precisionpos.tv.waitingqueue.utils.StationProfileConfigSession;
import com.precisionpos.tv.waitingqueue.utils.UpdateViewUtil;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class is used to listen for TV Waiting Queue Events
 */
public class TVWaitQueueLANListener extends MultipleSocketObjectServer {

    /* Holds an input stream open for the listener */
    private BufferedReader bufInputStreamReader;
    private static TVWaitQueueLANListener singleton;
    private Context context;
    /**
     * Constructor
     * @param port
     */
    public TVWaitQueueLANListener(int port, Context context) {
        super(port);
        this.context = context;
    }

    /**
     * Factory to get the singleton
     * @param port
     * @return
     */
    public static TVWaitQueueLANListener getInstance(int port, Context context) {
        if(singleton == null) {
            singleton = new TVWaitQueueLANListener(port, context);
        }

        /* Return the singleton */
        return singleton;
    }

    /**
     * Nullifies the singleton
     */
    public static void nullifySingleton() {
        singleton = null;
    }

    /**
     * Getter for the existing instance
     * @return
     */
    public static TVWaitQueueLANListener getExistingInstance() {
        return singleton;
    }

    /**
     * Override the processRequest method of the parent. This method is called
     * when a request comes in
     */
    @Override
    public void processRequest(Object obj) {

        // Sent from the point of sale to set the tv station
        if(obj instanceof StationTVProfileSetterBean) {
            try {

                // @TODO
                // Call the service
                StationProfileBean stationProfileBean = StationProfileConfigSession.getProfileDetailsBean(); // REPLACE WITH JSON CALL

                // The profile data sent from the POS
                StationTVProfileSetterBean profile = (StationTVProfileSetterBean) obj;
                String sBusinessID      = EncryptString.getInstance().decrypt(String.valueOf(profile.getEncryptedBusinessID()));
                String sStoreFrontCD    = EncryptString.getInstance().decrypt(String.valueOf(profile.getEncryptedStoreFrontCD()));

                // The credentials to call the webservice
                int businessID      = Integer.valueOf(sBusinessID);
                int storeFrontCD    = Integer.valueOf(sStoreFrontCD);

                // Username and password
                String userName     = EncryptString.getInstance().decrypt(profile.getEncryptedConnectionUsername());
                String password     = EncryptString.getInstance().decrypt(profile.getEncryptedConnectionPassword());
                String endPoint     = EncryptString.getInstance().decrypt(profile.getEncryptedConnectionURL());

                // Get the license id from the stored license id
                long licenseID      = stationProfileBean.getLicenseid();
                if(stationProfileBean != null) {
                    stationProfileBean.setBusinessID(businessID);
                    stationProfileBean.setStoreFrontCD(storeFrontCD);
                    stationProfileBean.setUsername(userName);
                    stationProfileBean.setPassword(password);
                    stationProfileBean.setEndpoint(endPoint);
                    stationProfileBean.setLicenseid(licenseID);

                    // Persist the results to the private file system
                    StationProfileConfigSession.persistStationBean(stationProfileBean);

                    // Load the updated credentials in the UpdateViewUtil class
                    UpdateViewUtil.getInstance().loadCredentials();
                }

                // Now call the webservice
                UpdateViewUtil.getInstance().updateTV();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        // Commands sent to the tv profile
        else if(obj instanceof StationTVProfileCommandBean) {
            // The profile data sent from the POS
            StationTVProfileCommandBean scb = (StationTVProfileCommandBean)obj;
            // Passed in Username and password
            String inUserName     = EncryptString.getInstance().decrypt(scb.getEncryptedConnectionUsername());
            String inPassword     = EncryptString.getInstance().decrypt(scb.getEncryptedConnectionPassword());

            // Compare to the ones saved in session
            StationProfileBean profileBean = StationProfileConfigSession.getProfileDetailsBean();

            // Username and password
            String existingUserName     = profileBean.getUsername();
            String existingPassword     = profileBean.getPassword();

            // Make sure the username and password from the point of sale
            // matches the one saved in the session
            if(!existingUserName.equals(inUserName) ||
                !existingPassword.equals(inPassword)) {

                return; // failed so return
            }

            // @TODO
            // Refresh the content
            if(scb.getCommandType() == StationTVProfileCommandBean.COMMAND_REFRESH) {
                // Now call the webservice
                UpdateViewUtil.getInstance().updateTV();
            }
            // @TODO
            // Set the TV Waiting Queue as Active
            else if(scb.getCommandType() == StationTVProfileCommandBean.COMMAND_SETACTIVE) {

            }
            // @TODO
            // Set the TV Waiting Queue as Inactive
            else if(scb.getCommandType() == StationTVProfileCommandBean.COMMAND_SETINACTIVE) {

            }
        }

        try {
           // For Debug
            Log.e("POS Message TEST", obj.getClass().getName());
            System.out.println("POS MESSAGE TEST: " + obj.getClass().getName());
        }
        catch(Exception e) {
            Log.e("ObjectServer Exception ", e.getMessage());
        }
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     */
    @Override
    protected void finalize() throws Throwable  {
        try {
            Log.e("ObjectServer Message ", "finalize message");
            this.bufInputStreamReader.close();
        } catch (IOException e) {
            Log.e("ObjectServer Exception ", e.getMessage());
        }
        super.finalize();
    }


}