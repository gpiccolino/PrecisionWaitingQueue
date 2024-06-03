package com.precisionpos.tv.waitingqueue.communication;

import java.io.BufferedReader;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

/**
 * This class is used to listen for printer events
 * @author Gino Piccolino
 */
public class POSLANListener extends BroadcastMultipleSocketServer {

    /* Holds an input stream open for the listener */
    private BufferedReader bufInputStreamReader;
    private static POSLANListener singleton;
    private Context context;
    /**
     * Constructor
     * @param port
     */
    public POSLANListener(int port, Context context) {
        super(port);
        this.context = context;
    }

    /**
     * Factory to get the singleton
     * @param port
     * @return
     */
    public static POSLANListener getInstance(int port, Context context) {
        if(singleton == null) {
            singleton = new POSLANListener(port, context);
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
    public static POSLANListener getExistingInstance() {
        return singleton;
    }

    /**
     * Override the processRequest method of the parent. This method is called
     * when a request comes in
     */
    @Override
    public String processRequest(String request) {
        try {
           // For Debug
            Log.e("POS Message TEST", request);
            System.out.println("POS MESSAGE TEST: " + request);
        }
        catch(Exception e) {
            Log.e("UDPSocketServer Exception ", e.getMessage());
        }
        return null;
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     */
    @Override
    protected void finalize() throws Throwable  {
        try {
            Log.e("UDPSocketServer Message ", "finalize message");
            this.bufInputStreamReader.close();
        } catch (IOException e) {
            Log.e("UDPSocketServer Exception ", e.getMessage());
        }
        super.finalize();
    }
}