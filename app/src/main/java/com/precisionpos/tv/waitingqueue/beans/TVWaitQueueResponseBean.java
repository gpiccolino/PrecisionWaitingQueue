package com.precisionpos.tv.waitingqueue.beans;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * TVWaitQueueResponseBean
 * @since 1/3/23
 * @author Kate
 */
public class TVWaitQueueResponseBean {

    public TVWaitQueueResponseBean() { }

    public TVWaitQueueResponseBean(boolean opsuccess, String opreturnmessage, boolean opautologout,
                                   String opredirecttopanelid, StationProfileBean stationProfile,
                                   ArrayList<ListOrder> listOrders) {
        this.opsuccess = opsuccess;
        this.opreturnmessage = opreturnmessage;
        this.opautologout = opautologout;
        this.opredirecttopanelid = opredirecttopanelid;
        this.stationProfile = stationProfile;
        this.listOrders = listOrders;
    }

    @JsonProperty("opsuccess")
    public boolean getOpsuccess() {
        return this.opsuccess;
    }

    public void setOpsuccess(boolean opsuccess) {
        this.opsuccess = opsuccess;
    }

    boolean opsuccess;
    @JsonProperty("opreturnmessage")
    public String getOpreturnmessage() {
        return this.opreturnmessage;
    }

    public void setOpreturnmessage(String opreturnmessage) {
        this.opreturnmessage = opreturnmessage;
    }

    String opreturnmessage;
    @JsonProperty("opautologout")
    public boolean getOpautologout() {
        return this.opautologout;
    }

    public void setOpautologout(boolean opautologout) {
        this.opautologout = opautologout;
    }

    boolean opautologout;
    @JsonProperty("opredirecttopanelid")
    public String getOpredirecttopanelid() {
        return this.opredirecttopanelid;
    }

    public void setOpredirecttopanelid(String opredirecttopanelid) {
        this.opredirecttopanelid = opredirecttopanelid;
    }

    String opredirecttopanelid;
    @JsonProperty("stationProfile")
    public StationProfileBean getStationProfile() {
        return this.stationProfile;
    }

    public void setStationProfile(StationProfileBean stationProfile) {
        this.stationProfile = stationProfile;
    }

    StationProfileBean stationProfile;
    @JsonProperty("listOrders")
    public ArrayList < ListOrder > getListOrders() {
        return this.listOrders;
    }

    public void setListOrders(ArrayList< ListOrder > listOrders) {
        this.listOrders = listOrders;
    }
    ArrayList < ListOrder > listOrders;
}
