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
}
