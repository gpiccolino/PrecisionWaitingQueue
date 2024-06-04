package com.precisionpos.tv.waitingqueue.beans;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * TVWaitQueueStationProfile
 * @since 1/4/23
 * @author Kate
 */
public class StationProfileBean implements Serializable {

    public StationProfileBean() { }

    public StationProfileBean(boolean opsuccess, String opreturnmessage, boolean opautologout,
                              String opredirecttopanelid, int businessID, int storeFrontCD,
                              int transcode, String profileName, String storeName,
                              String wifiMacAddress, String lanMacAddress, int stationCode,
                              String androidID, boolean isActive, int waitingQueueProfileType,
                              boolean displayOLOOrders, boolean displayKioskOrders,
                              boolean displayThirdPartyOrders, int rowFontSize, int numberOfRows,
                              String tvMessage, int orderIDPreference, String image1, String image2,
                              String image3, String image4, String image5, String image6, String image7,
                              String image8, String image9, String image10, String video1,
                              String video2, String video3, String video4, String video5, String video6,
                              String video7, String video8, String video9, String video10,
                              long timestampUpdated, long timestampNewInsert) {
        this.opsuccess = opsuccess;
        this.opreturnmessage = opreturnmessage;
        this.opautologout = opautologout;
        this.opredirecttopanelid = opredirecttopanelid;
        this.businessID = businessID;
        this.storeFrontCD = storeFrontCD;
        this.transcode = transcode;
        this.profileName = profileName;
        this.storeName = storeName;
        this.wifiMacAddress = wifiMacAddress;
        this.lanMacAddress = lanMacAddress;
        this.stationCode = stationCode;
        this.androidID = androidID;
        this.isActive = isActive;
        this.waitingQueueProfileType = waitingQueueProfileType;
        this.displayOLOOrders = displayOLOOrders;
        this.displayKioskOrders = displayKioskOrders;
        this.displayThirdPartyOrders = displayThirdPartyOrders;
        this.rowFontSize = rowFontSize;
        this.numberOfRows = numberOfRows;
        this.tvMessage = tvMessage;
        this.orderIDPreference = orderIDPreference;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.image7 = image7;
        this.image8 = image8;
        this.image9 = image9;
        this.image10 = image10;
        this.video1 = video1;
        this.video2 = video2;
        this.video3 = video3;
        this.video4 = video4;
        this.video5 = video5;
        this.video6 = video6;
        this.video7 = video7;
        this.video8 = video8;
        this.video9 = video9;
        this.video10 = video10;
        this.timestampUpdated = timestampUpdated;
        this.timestampNewInsert = timestampNewInsert;
    }

    //----------------------- Not part of JSON --------------------------------------//

    /**
     * Getter for the licenseid
     * @return
     */
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

    /**
     * Is this the default profile
     * @return
     */
    public boolean getIsDefaultProfile() {
        return this.isDefaultProfile;
    }

    /**
     * Set as default profile
     * @return
     */
    public void setIsDefaultProfile(boolean isDefaultProfile) {
        this.isDefaultProfile = isDefaultProfile;
    }

    boolean isDefaultProfile = false;

    /**
     * Getter for the username
     * @return
     */
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
    String username = "";

    /**
     * Getter for the password
     * @return
     */
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
    String password = "";

    /**
     * Getter for the endpoint
     * @return
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Setter for the endpoint
     * @param endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    String endpoint;
    //----------------------- END Not part of JSON -----------------------------------//

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

    @JsonProperty("businessID")
    public int getBusinessID() {
        return this.businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }

    int businessID;

    @JsonProperty("storeFrontCD")
    public int getStoreFrontCD() {
        return this.storeFrontCD;
    }

    public void setStoreFrontCD(int storeFrontCD) {
        this.storeFrontCD = storeFrontCD;
    }

    int storeFrontCD;

    @JsonProperty("transcode")
    public int getTranscode() {
        return this.transcode;
    }

    public void setTranscode(int transcode) {
        this.transcode = transcode;
    }

    int transcode;

    @JsonProperty("profileName")
    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    String profileName;

    @JsonProperty("storeName")
    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    String storeName;

    @JsonProperty("wifiMacAddress")
    public String getWifiMacAddress() {
        return this.wifiMacAddress;
    }

    public void setWifiMacAddress(String wifiMacAddress) {
        this.wifiMacAddress = wifiMacAddress;
    }

    String wifiMacAddress;

    @JsonProperty("lanMacAddress")
    public String getLanMacAddress() {
        return this.lanMacAddress;
    }

    public void setLanMacAddress(String lanMacAddress) {
        this.lanMacAddress = lanMacAddress;
    }

    String lanMacAddress;

    @JsonProperty("stationCode")
    public long getStationCode() {
        return this.stationCode;
    }

    public void setStationCode(long stationCode) {
        this.stationCode = stationCode;
    }

    long stationCode;

    @JsonProperty("androidID")
    public String getAndroidID() {
        return this.androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }

    String androidID;

    @JsonProperty("isActive")
    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    boolean isActive;

    @JsonProperty("waitingQueueProfileType")
    public int getWaitingQueueProfileType() {
        return this.waitingQueueProfileType;
    }

    public void setWaitingQueueProfileType(int waitingQueueProfileType) {
        this.waitingQueueProfileType = waitingQueueProfileType;
    }

    int waitingQueueProfileType;

    @JsonProperty("displayOLOOrders")
    public boolean getDisplayOLOOrders() {
        return this.displayOLOOrders;
    }

    public void setDisplayOLOOrders(boolean displayOLOOrders) {
        this.displayOLOOrders = displayOLOOrders;
    }

    boolean displayOLOOrders;

    @JsonProperty("displayKioskOrders")
    public boolean getDisplayKioskOrders() {
        return this.displayKioskOrders;
    }

    public void setDisplayKioskOrders(boolean displayKioskOrders) {
        this.displayKioskOrders = displayKioskOrders;
    }

    boolean displayKioskOrders;

    @JsonProperty("displayThirdPartyOrders")
    public boolean getDisplayThirdPartyOrders() {
        return this.displayThirdPartyOrders;
    }

    public void setDisplayThirdPartyOrders(boolean displayThirdPartyOrders) {
        this.displayThirdPartyOrders = displayThirdPartyOrders;
    }

    boolean displayThirdPartyOrders;

    @JsonProperty("rowFontSize")
    public int getRowFontSize() {
        return this.rowFontSize;
    }

    public void setRowFontSize(int rowFontSize) {
        this.rowFontSize = rowFontSize;
    }

    int rowFontSize;

    @JsonProperty("numberOfRows")
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    int numberOfRows;

    @JsonProperty("tvMessage")
    public String getTvMessage() {
        return this.tvMessage;
    }

    public void setTvMessage(String tvMessage) {
        this.tvMessage = tvMessage;
    }

    String tvMessage;

    @JsonProperty("orderIDPreference")
    public int getOrderIDPreference() {
        return this.orderIDPreference;
    }

    public void setOrderIDPreference(int orderIDPreference) {
        this.orderIDPreference = orderIDPreference;
    }

    int orderIDPreference;

    @JsonProperty("image1")
    public String getImage1() {
        return this.image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    String image1;

    @JsonProperty("image2")
    public String getImage2() {
        return this.image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    String image2;

    @JsonProperty("image3")
    public String getImage3() {
        return this.image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    String image3;

    @JsonProperty("image4")
    public String getImage4() {
        return this.image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    String image4;

    @JsonProperty("image5")
    public String getImage5() {
        return this.image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    String image5;

    @JsonProperty("image6")
    public String getImage6() {
        return this.image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    String image6;

    @JsonProperty("image7")
    public String getImage7() {
        return this.image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    String image7;

    @JsonProperty("image8")
    public String getImage8() {
        return this.image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    String image8;

    @JsonProperty("image9")
    public String getImage9() {
        return this.image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    String image9;

    @JsonProperty("image10")
    public String getImage10() {
        return this.image10;
    }

    public void setImage10(String image10) {
        this.image10 = image10;
    }

    String image10;

    @JsonProperty("video1")
    public String getVideo1() {
        return this.video1;
    }

    public void setVideo1(String video1) {
        this.video1 = video1;
    }

    String video1;

    @JsonProperty("video2")
    public String getVideo2() {
        return this.video2;
    }

    public void setVideo2(String video2) {
        this.video2 = video2;
    }

    String video2;

    @JsonProperty("video3")
    public String getVideo3() {
        return this.video3;
    }

    public void setVideo3(String video3) {
        this.video3 = video3;
    }

    String video3;

    @JsonProperty("video4")
    public String getVideo4() {
        return this.video4;
    }

    public void setVideo4(String video4) {
        this.video4 = video4;
    }

    String video4;

    @JsonProperty("video5")
    public String getVideo5() {
        return this.video5;
    }

    public void setVideo5(String video5) {
        this.video5 = video5;
    }

    String video5;

    @JsonProperty("video6")
    public String getVideo6() {
        return this.video6;
    }

    public void setVideo6(String video6) {
        this.video6 = video6;
    }

    String video6;

    @JsonProperty("video7")
    public String getVideo7() {
        return this.video7;
    }

    public void setVideo7(String video7) {
        this.video7 = video7;
    }

    String video7;

    @JsonProperty("video8")
    public String getVideo8() {
        return this.video8;
    }

    public void setVideo8(String video8) {
        this.video8 = video8;
    }

    String video8;

    @JsonProperty("video9")
    public String getVideo9() {
        return this.video9;
    }

    public void setVideo9(String video9) {
        this.video9 = video9;
    }

    String video9;

    @JsonProperty("video10")
    public String getVideo10() {
        return this.video10;
    }

    public void setVideo10(String video10) {
        this.video10 = video10;
    }

    String video10;

    @JsonProperty("timestampUpdated")
    public long getTimestampUpdated() {
        return this.timestampUpdated;
    }

    public void setTimestampUpdated(long timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    long timestampUpdated;

    @JsonProperty("timestampNewInsert")
    public long getTimestampNewInsert() {
        return this.timestampNewInsert;
    }

    public void setTimestampNewInsert(long timestampNewInsert) {
        this.timestampNewInsert = timestampNewInsert;
    }

    long timestampNewInsert;
}
