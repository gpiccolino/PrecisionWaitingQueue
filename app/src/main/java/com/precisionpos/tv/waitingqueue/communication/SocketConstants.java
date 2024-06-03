package com.precisionpos.tv.waitingqueue.communication;

/**
 * @author Gino
 */
public class SocketConstants {

    // The QR code generator uses the HEX EOT 0x04 to set the size
    // Unfortunately it KILLS the stream
    //	public static int IEOT		= 4;
    public static int IEOT		= -1;
    public static char EOT 		= (char)IEOT;
    public static String SEOT 	= String.valueOf(EOT);
}