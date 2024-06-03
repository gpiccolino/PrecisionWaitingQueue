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
package com.precisionpos.tv.waitingqueue.communication;

import android.util.Log;

import com.precisionpos.tv.waitingqueue.beans.StationTVProfileCommandBean;
import com.precisionpos.tv.waitingqueue.beans.StationTVProfileSetterBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Multiple socket server object server
 *
 * @author Gino
 */
//public abstract class MultipleSocketObjectServer {
public abstract class MultipleSocketObjectServer {
    /* The default port */
    private int port = 7778;

    /* Kills the socket listener */
    protected boolean socketActive = false;

    /**
     * Constructor with PORT
     *
     * @param port
     */
    public MultipleSocketObjectServer(int port) {
        this.port = port;
    }

    /**
     * Starts the server
     */
    public boolean startServer() {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                ServerSocket socket = null;
                try {
                    socketActive = true;
                    socket = new ServerSocket(port);

                    while (true && socketActive) {

                        Socket connection = socket.accept();
                        // connection.setReuseAddress(true);
                        Runnable runnable = new ServerSocketResponder(connection);
                        Thread thread = new Thread(runnable);
                        thread.start();
                    }
                } catch (Exception e) {
                    Log.e("Start Server Message", e.getMessage());
                    socketActive = false;
                }

                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        Log.e("Start Server Message2", e.getMessage());
                    }
                }
            }

        });
        thread.start();

        /* Delay to return success or failure */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.e("Start Server Message3", e.getMessage());
        }

        return socketActive;

    }

    /**
     * Stops the server
     */
    public void stopServer() {
        if (this.socketActive) {
            this.socketActive = false;
            /* Send a null message */
            SocketMessage msg = new SocketMessage("localhost", this.getPort());
            msg.sendAsyncObjectMessage(new String("kill"), 5000);
        }
    }

    /**
     * This class handles the response
     *
     * @author Gino Piccolino
     */
    private class ServerSocketResponder implements Runnable {
        private Socket connection;

        /**
         * Default constructor
         *
         * @param socket
         */
        public ServerSocketResponder(Socket socket) {
            this.connection = socket;

        }

        /**
         * Handles the request
         */
        public void run() {

            /* Our input and output streams */
            BufferedReader isr = null;
            ObjectOutputStream oos = null;

            try {

                ObjectInputStream ois =
                        new ObjectInputStream(connection.getInputStream());

                // Read the object
                Object obj = ois.readObject();

                if (obj instanceof StationTVProfileSetterBean) {
                    processRequest(obj);
                }
				else if (obj instanceof StationTVProfileCommandBean) {
                    // Processes the request and sends back a response
                    processRequest(obj);
                }

                connection.close();

            } catch (Exception e) {
                String errorMessage = e.getMessage();
                if (errorMessage == null) {
                    errorMessage = "NO Message but exception thrown for object";
                }
                Log.e("Start Server Responder", errorMessage);
            } finally {
                try {
                    if (isr != null) {
                        isr.close();
                    }
                    if (oos != null) {
                        oos.close();
                    }
                    connection.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * @return Return the value associated with: port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the value related to: port
     *
     * @param port the port value
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Process the request
     *
     * @param obj
     * @return
     */
    public abstract void processRequest(Object obj);

}