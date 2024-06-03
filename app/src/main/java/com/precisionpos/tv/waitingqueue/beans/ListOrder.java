package com.precisionpos.tv.waitingqueue.beans;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * TVWaitQueueOrderBean
 * @since 1/4/23
 * @author Kate
 */
public class ListOrder {

    public ListOrder() { }

    public ListOrder(int ticketNumber, int orderNumber, long orderDateAsLong, long settleDateAsLong,
                     int orderType, int oloType, long orderStationCD, String dineInTabName,
                     long updateTimeStamp, String guestName, String storeName, long readyForAsLong,
                     long orderFulfilledDateAsLong, long waitingTimeEnd, long waitingTimeStart,
                     boolean isOrderFromWeb, boolean isOrderFromKiosk) {
        this.ticketNumber = ticketNumber;
        this.orderNumber = orderNumber;
        this.orderDateAsLong = orderDateAsLong;
        this.settleDateAsLong = settleDateAsLong;
        this.orderType = orderType;
        this.oloType = oloType;
        this.orderStationCD = orderStationCD;
        this.dineInTabName = dineInTabName;
        this.updateTimeStamp = updateTimeStamp;
        this.guestName = guestName;
        this.storeName = storeName;
        this.readyForAsLong = readyForAsLong;
        this.orderFulfilledDateAsLong = orderFulfilledDateAsLong;
        this.waitingTimeEnd = waitingTimeEnd;
        this.waitingTimeStart = waitingTimeStart;
        this.isOrderFromWeb = isOrderFromWeb;
        this.isOrderFromKiosk = isOrderFromKiosk;
    }

    @JsonProperty("ticketNumber")
    public int getTicketNumber() {
        return this.ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
    int ticketNumber;

    @JsonProperty("orderNumber")
    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    int orderNumber;

    @JsonProperty("orderDateAsLong")
    public long getOrderDateAsLong() {
        return this.orderDateAsLong;
    }

    public void setOrderDateAsLong(long orderDateAsLong) {
        this.orderDateAsLong = orderDateAsLong;
    }
    long orderDateAsLong;

    @JsonProperty("settleDateAsLong")
    public long getSettleDateAsLong() {
        return this.settleDateAsLong;
    }

    public void setSettleDateAsLong(long settleDateAsLong) {
        this.settleDateAsLong = settleDateAsLong;
    }
    long settleDateAsLong;

    @JsonProperty("orderType")
    public int getOrderType() {
        return this.orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    int orderType;

    @JsonProperty("oloType")
    public int getOloType() {
        return this.oloType;
    }

    public void setOloType(int oloType) {
        this.oloType = oloType;
    }
    int oloType;

    @JsonProperty("orderStationCD")
    public long getOrderStationCD() {
        return this.orderStationCD;
    }

    public void setOrderStationCD(long orderStationCD) {
        this.orderStationCD = orderStationCD;
    }
    long orderStationCD;

    @JsonProperty("dineInTabName")
    public String getDineInTabName() {
        return this.dineInTabName;
    }

    public void setDineInTabName(String dineInTabName) {
        this.dineInTabName = dineInTabName;
    }
    String dineInTabName;

    @JsonProperty("updateTimeStamp")
    public long getUpdateTimeStamp() {
        return this.updateTimeStamp;
    }

    public void setUpdateTimeStamp(long updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
    long updateTimeStamp;

    @JsonProperty("guestName")
    public String getGuestName() {
        return this.guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    String guestName;

    @JsonProperty("storeName")
    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    String storeName;

    @JsonProperty("readyForAsLong")
    public long getReadyForAsLong() {
        return this.readyForAsLong;
    }

    public void setReadyForAsLong(long readyForAsLong) {
        this.readyForAsLong = readyForAsLong;
    }
    long readyForAsLong;

    @JsonProperty("orderFulfilledDateAsLong")
    public long getOrderFulfilledDateAsLong() {
        return this.orderFulfilledDateAsLong;
    }

    public void setOrderFulfilledDateAsLong(long orderFulfilledDateAsLong) {
        this.orderFulfilledDateAsLong = orderFulfilledDateAsLong;
    }
    long orderFulfilledDateAsLong;

    @JsonProperty("waitingTimeEnd")
    public long getWaitingTimeEnd() {
        return this.waitingTimeEnd;
    }

    public void setWaitingTimeEnd(long waitingTimeEnd) {
        this.waitingTimeEnd = waitingTimeEnd;
    }
    long waitingTimeEnd;

    @JsonProperty("waitingTimeStart")
    public long getWaitingTimeStart() {
        return this.waitingTimeStart;
    }

    public void setWaitingTimeStart(long waitingTimeStart) {
        this.waitingTimeStart = waitingTimeStart;
    }
    long waitingTimeStart;

    @JsonProperty("isOrderFromWeb")
    public boolean getIsOrderFromWeb() {
        return this.isOrderFromWeb;
    }

    public void setIsOrderFromWeb(boolean isOrderFromWeb) {
        this.isOrderFromWeb = isOrderFromWeb;
    }
    boolean isOrderFromWeb;

    @JsonProperty("isOrderFromKiosk")
    public boolean getIsOrderFromKiosk() {
        return this.isOrderFromKiosk;
    }

    public void setIsOrderFromKiosk(boolean isOrderFromKiosk) {
        this.isOrderFromKiosk = isOrderFromKiosk;
    }
    boolean isOrderFromKiosk;
}