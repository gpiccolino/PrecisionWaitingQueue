package com.precisionpos.tv.waitingqueue.beans;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @since 1/3/23
 * @author ktelavelle
 */
public class TVWaitQueueRequestBean {


    public TVWaitQueueRequestBean() {
    }

    public TVWaitQueueRequestBean(long stationcode, long lastUpdateTimestamp) {
        this.stationcode = stationcode;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    @JsonProperty("stationcode")
    public long getStationcode() {
        return this.stationcode;
    }

    public void setStationcode(long stationcode) {
        this.stationcode = stationcode;
    }
    long stationcode;

    @JsonProperty("lastUpdateTimestamp")
    public long getLastUpdateTimestamp() {
        return this.lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }
    long lastUpdateTimestamp;


    /**
     * Getter for the username
     * @return
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username
     * @return
     */
    public void setUsername(String username) {
        this.username = username;
    }
    private String username = "";

    /**
     * Getter for the password
     * @return
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password
     * @return
     */
    public void setPassword(String password) {
        this.password = password;
    }
    private String password = "";


    /**
     * Getter for the licenseid
     * @return
     */
    @JsonProperty("licenseid")
    public long getLicenseid() {
        return this.licenseid;
    }

    /**
     * Setter for the licenseid
     * @return
     */
    public void setLicenseid(long licenseid) {
        this.licenseid = licenseid;
    }

    long licenseid;

    @JsonProperty("businessid")
    public int getBusinessID() {
        return this.businessid;
    }

    public void setBusinessid(int businessID) {
        this.businessid = businessid;
    }

    int businessid;

    @JsonProperty("storefrontcd")
    public int getStoreFrontCD() {
        return this.storefrontcd;
    }

    public void setStoreFrontCD(int storeFrontCD) {
        this.storefrontcd = storefrontcd;
    }

    int storefrontcd;
}
