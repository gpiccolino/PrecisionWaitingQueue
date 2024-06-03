package com.precisionpos.tv.waitingqueue.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.precisionpos.tv.waitingqueue.communication.POSLANListener;
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
        POSLANListener poslanListener = new POSLANListener(3588, this.getApplicationContext());
        poslanListener.startServer();

        // Timer to check for new orders
        util = new UpdateViewUtil();

        // Create new timer task to update TV
        updateTV();
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
     * Terminate activity
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        timer.cancel(); // cancel timer thread
        isTerminated = true; // set flag
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
