package com.precisionpos.tv.waitingqueue.communication;

/**
 * @author Gino
 */
public class SocketReturnMessage {

    private boolean success = true;
    private String message = "";
    private static final SocketReturnMessage successMessage = new SocketReturnMessage();

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessageString() {
        return message;
    }
    public void setMessageString(String message) {
        this.message = message;
    }

    public static SocketReturnMessage getSuccessMessage() {
        return successMessage;
    }

}