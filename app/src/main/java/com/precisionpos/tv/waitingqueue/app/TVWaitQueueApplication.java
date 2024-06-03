package com.precisionpos.tv.waitingqueue.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.precisionpos.tv.waitingqueue.beans.StationProfileBean;
import com.precisionpos.tv.waitingqueue.communication.TVWaitQueueLANListener;
import com.precisionpos.tv.waitingqueue.service.TVWaitQueueService;
import com.precisionpos.tv.waitingqueue.utils.UpdateViewUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main TV Wait Queue Application
 * @since 1/5/23
 * @author Kate, Gino
 */
public class TVWaitQueueApplication extends Application {

    public static boolean isRunning;
    public static boolean isTerminated;
    private static Context context;
    private static Activity currentActivity;
    private UpdateViewUtil util;
    public Timer timer;

    // Broadcast receiver for services
    private BroadcastReceiver mServiceReceiver;

    // Callback event for service
    public static final String BROADCAST_TVWAITINGQUEUELISTENER_DESTROY   = "broadcast-tvwaitingqueue-destroy";

    // Constructor
    public TVWaitQueueApplication() {
        super();
    }

    /**
     * Create Application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        // Start listener service
        TVWaitQueueLANListener poslanListener = new TVWaitQueueLANListener(3588, this.getApplicationContext());
        poslanListener.startServer();

        // Timer to check for new orders
        util = new UpdateViewUtil();

        // Create new timer task to update TV
        updateTV();

        // Start the tv waiting queue service
        startTVWaitQueueService();
    }

    /**
     * Timer task to update TV
     */
    public void updateTV() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isRunning) { // Don't update tv if already in progress
                    util.updateTV();
                }
            }
            @Override
            public void finalize() throws Throwable {
                super.finalize();
                if(!isTerminated) {
                    updateTV(); // call new timer task if GC cleaned it up
                }
            }
        }, 5000,5000); // delay 5 seconds
    }

    /**
     * Start the listener for events
     */
    private void setBroadcastReceiver() {
        // Create the receiver
        mServiceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // TV waiting queue service was destroyed
                // New online ordering
                if (intent.getAction().equals(BROADCAST_TVWAITINGQUEUELISTENER_DESTROY)) {
                    startTVWaitQueueService();
                }
            }
        };

        // Add the intent filter
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BROADCAST_TVWAITINGQUEUELISTENER_DESTROY);

        try {
            registerReceiver(mServiceReceiver, mIntentFilter);
        } catch (Exception e) {
        }
    }

    /**
     * Start the waiting queue service
     */
    private void startTVWaitQueueService() {
        try {
            StationProfileBean sdb = null; // StationConfigSession.getStationDetailsBean();

            Object service = (Object) getRunningService(TVWaitQueueService.class);
            if (service == null || !isMyServiceRunning(TVWaitQueueService.class)) {
                Intent tvWaitQueueService = new Intent(this, TVWaitQueueService.class);
                startService(tvWaitQueueService);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Terminate activity
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        timer.cancel(); // cancel timer thread
        isTerminated = true; // set flag

        // Unregister the receiver
        if(mServiceReceiver != null) {
            try {
                unregisterReceiver(mServiceReceiver);
            }
            catch(Exception e) {}
        }

        try {
            // Stop the the LAN listener
            TVWaitQueueLANListener tvWaitingQueueLANListener = TVWaitQueueLANListener.getExistingInstance();

            // If a lan listener is running
            if(tvWaitingQueueLANListener != null) {
                tvWaitingQueueLANListener.stopServer();
            }

            // Stop the LAN listener service
            stopService(new Intent(this, TVWaitQueueService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to determine if service is running
     *
     * @param serviceClass
     * @return
     */
    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the running service class
     *
     * @param serviceClass
     * @return
     */
    private Object getRunningService(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return serviceClass;
            }
        }
        return null;
    }

    /**
     * Get app context
     * @return Context context
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * Get current activity
     * @return Activity currentActivity
     */
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Set current activity
     * @param currentActivity Current activity reference
     */
    public static void setCurrentActivity(Activity currentActivity) {
        TVWaitQueueApplication.currentActivity = currentActivity;
    }
}
