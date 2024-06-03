package com.precisionpos.tv.waitingqueue.communication;

import android.util.Log;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * This class servers as a multiple socket server
 *
 * @author Gino Piccolino
 *
 */
public class BroadcastMultipleSocketServer {

    /* The default port */
    private int port = 7777;

    /* Kills the socket listener */
    protected boolean socketActive = false;

    /**
     * Constructor with PORT
     *
     * @param port
     */
    public BroadcastMultipleSocketServer(int port) {
        this.port = port;
    }

    /**
     * Starts the server
     */
    public boolean startServer() {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                DatagramSocket socket = null;
                try {

                    Log.e("Start Broadcast Server", "SERVER STARTED");
                    socketActive = true;
                    socket = new DatagramSocket(port);

                    while (true && socketActive) {
                        byte[] receiveData = new byte[1024];

                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        socket.receive(receivePacket);

                        String broadcastMessage = new String( receivePacket.getData()).trim();

                        // Handle the response in a thread
                        Runnable runnable = new BroadcastServerSocketHandler(broadcastMessage);
                        Thread thread = new Thread(runnable);
                        thread.start();
                    }
                } catch (Exception e) {
                    Log.e("Start Braodcast Error", e.getMessage());
                    socketActive = false;
                }

                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        Log.e("Start Broadcast Error2", e.getMessage());
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
            msg.sendAsyncMessage("-1", 5000);
        }
    }

    /**
     * Processes the request
     *
     * @param request
     * @return
     */
    public String processRequest(String request) {
        System.out.println("RECEIVED - Must Overide processRequest: " + request);
        return null;
    }

    /**
     * This class handles the response
     *
     * @author Gino Piccolino
     *
     */
    private class BroadcastServerSocketHandler implements Runnable {
        private String message;

        /**
         * Default constructor
         * @param broadcastMessage
         */
        public BroadcastServerSocketHandler(String broadcastMessage) {
            this.message = broadcastMessage;
        }

        /**
         * Handles the request
         */
        public void run() {
            BroadcastMultipleSocketServer.this.processRequest(message.toString());
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
     * @param port
     *            the port value
     */
    public void setPort(int port) {
        this.port = port;
    }
}
