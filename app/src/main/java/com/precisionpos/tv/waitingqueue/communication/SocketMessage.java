package com.precisionpos.tv.waitingqueue.communication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Gino
 */
public class SocketMessage {

    /* The port of the server */
    private int port = 7777;
    private String host = "localhost";

    /**
     * Default constructor
     * @param host
     * @param port
     */
    public SocketMessage(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sends a message
     * @param message
     */
    public synchronized SocketReturnMessage sendAsyncMessage(String message, int timeout) {
        Socket connection 		= null;
        OutputStreamWriter osw 	= null;
        InetAddress address		= null;

        SocketReturnMessage srm = SocketReturnMessage.getSuccessMessage();

        try {
            /* Obtain an address object of the server */
            address = InetAddress.getByName(host);

            /* Establish a socket connection */
            connection = new Socket(address, port);

            // Set a timeout if necessary
            if(timeout > 0) {
                connection.setSoTimeout(timeout);
            }

            /*
             * Instantiate an OutputStreamWriter object with the optional
             * character encoding.
             */
            osw = new OutputStreamWriter(new BufferedOutputStream(connection
                    .getOutputStream()), "US-ASCII");

            /* Write across the socket connection and flush the buffer */
            StringBuffer msg = new StringBuffer();
            msg.append(message);
            msg.append(SocketConstants.EOT);

            osw.write(msg.toString());
            osw.flush();

            /* Help out the GC */
            msg = null;

        } catch (Exception e) {
            srm = new SocketReturnMessage();
            srm.setSuccess(false);
            srm.setMessageString(e.getMessage());

            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                if(osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* Help out the GC */
            connection 	= null;
            osw 		= null;
            address		= null;
        }
        return srm;
    }

    /**
     * Send a message
     * @param obj
     * @param timeout
     * @return
     */
    public SocketReturnMessage sendAsyncObjectMessage(Object obj, int timeout) {
        Socket connection 		= null;
        OutputStreamWriter osw 	= null;
        InetAddress address		= null;

        SocketReturnMessage srm = SocketReturnMessage.getSuccessMessage();

        try {
            /* Obtain an address object of the server */
            address = InetAddress.getByName(host);

            /* Establish a socket connection */
            connection = new Socket(address, port);

            // Set a timeout if necessary
            if(timeout > 0) {
                connection.setSoTimeout(timeout);
            }

            ObjectOutputStream  oos = new
                    ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(obj);
            oos.flush();
            oos.close();

        } catch (Exception e) {
            srm = new SocketReturnMessage();
            srm.setSuccess(false);
            srm.setMessageString(e.getMessage());

            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                if(osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* Help out the GC */
            connection 	= null;
            osw 		= null;
            address		= null;
        }
        return srm;
    }


    /**
     * Sends a message
     * @param message
     */
    public String sendSynchMessage(String message) {

        /* Our return value */
        StringBuffer response = new StringBuffer();

        /* Our sockets and readers */
        Socket connection 		= null;
        OutputStreamWriter osw 	= null;
        BufferedReader isr		= null;
        InetAddress address		= null;

        try {
            /* Obtain an address object of the server */
            address = InetAddress.getByName(host);

            /* Establish a socket connection */
            connection = new Socket(address, port);

            /*
             * Instantiate an OutputStreamWriter object with the optional
             * character encoding.
             */
            osw = new OutputStreamWriter(new BufferedOutputStream(connection
                    .getOutputStream()), "US-ASCII");

            /* Write across the socket connection and flush the buffer */
            StringBuffer msg = new StringBuffer();
            msg.append(message);
            msg.append(SocketConstants.EOT);

            osw.write(msg.toString());
            osw.flush();

            /* Get the response */
            isr = new BufferedReader (new InputStreamReader(
                    connection.getInputStream()));

            /* Read in all the characters */
            int c;
            while ((c = isr.read()) != SocketConstants.IEOT && c != -1) {
                response.append((char) c);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(isr != null) {
                    isr.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            /* Help out the GC */
            connection 	= null;
            osw 		= null;
            isr			= null;
            address		= null;
        }

        /* Return our results */
        return response.toString();
    }


    public static void main(String[] args) {

        SocketMessage msg = new SocketMessage("localhost", 7799);

        for(int x = 0; x < 100; x++) {
            try {

                msg.sendAsyncMessage(String.valueOf("1"), -1);


//				Thread.sleep(2000);
            } catch (Exception e) {
                System.err.println("errrrorrrrr " + x);
            }
        }

    }
}
