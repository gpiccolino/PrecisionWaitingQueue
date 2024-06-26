package com.precisionpos.tv.waitingqueue.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.precisionpos.tv.waitingqueue.MainActivity;
import com.precisionpos.tv.waitingqueue.TVWaitQueueSetupActivity;
import com.precisionpos.tv.waitingqueue.app.TVWaitQueueApplication;
import com.precisionpos.tv.waitingqueue.beans.ListOrder;
import com.precisionpos.tv.waitingqueue.beans.StationProfileBean;
import com.precisionpos.tv.waitingqueue.beans.TVWaitQueueRequestBean;
import com.precisionpos.tv.waitingqueue.beans.TVWaitQueueResponseBean;
import com.precisionpos.tv.waitingqueue.communication.CommunicationUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Utility class for updating tv and order details
 * @since 1/4/23
 * @author Kate
 */
public class UpdateViewUtil {

//    private String endpointURL = "https://olo2.o-ez.com/PrecisionAppConnector/apptvwaitingqueuedata";
    private String endpointURL = "http://192.168.5.136:8075/PrecisionAppConnector/apptvwaitingqueuedata";
    //private final String endpointURL = "https://34.138.42.223/PrecisionAppConnector/apptvwaitingqueuedata";
    private String requestBeanStr;
    private String responseMessage;

    private int profileType;
    private int currentReadyPage = 0;
    private int currentWaitPage = 0;
    private int totalReadyPages;
    private int totalWaitPages;

    private boolean displayKioskOrders;
    private boolean displayOnlineOrders;
    private boolean displayThirdPartyOrders;

    private TVWaitQueueResponseParser parser;
    private TVWaitQueueResponseBean responseBean;

    private List<ListOrder> orderList = new ArrayList<ListOrder>(); // All orders
    private List<ListOrder> filteredOrderList = new ArrayList<>();
    private List<ListOrder> orderWaitList = new ArrayList<ListOrder>();
    private List<ListOrder> orderReadyList = new ArrayList<ListOrder>();

    private long lastFulfilledTime;
    private Context context;

    // Keep track of running task
//    private boolean isRunning = false;

    // Credentials to call the webservice
    private TVWaitQueueRequestBean requestBean = new TVWaitQueueRequestBean();

    private PaginatorUtil paginatorWait = new PaginatorUtil();
    private PaginatorUtil paginatorReady = new PaginatorUtil();

    // Our singleton
    private static UpdateViewUtil singleton;
    private Timer timer;

    /**
     * Private constructor enforces singleton
     */
    private UpdateViewUtil() {}

    /**
     * Factory method to get singleton
     * @return
     */
    public static UpdateViewUtil getInstance() {
        if (singleton == null) {
            singleton = new UpdateViewUtil();
            singleton.loadCredentials();
            singleton.startTimer();
        }
        return singleton;
    }


    /**
     * This method is used to pull the credentials saved
     * in the file system and sets them globally to this class
     *
     */
    public void loadCredentials() {
        // Get the station profile bean
        StationProfileBean profileBean = StationProfileConfigSession.getProfileDetailsBean();

        // The credentials to call the webservice
        requestBean.setUsername(profileBean.getUsername());
        requestBean.setPassword(profileBean.getPassword());
        requestBean.setLicenseid(profileBean.getLicenseid());
        requestBean.setStationcode(profileBean.getLicenseid());
        requestBean.setBusinessid(profileBean.getBusinessID());
        requestBean.setStoreFrontCD(profileBean.getStoreFrontCD());

        // Set the endpoint
        endpointURL        = profileBean.getEndpoint();
    }
    /**
     * Method to send request and update orders from response
     */
    public void updateTV() {

        orderList = new ArrayList<>();
        filteredOrderList = new ArrayList<>();
        orderWaitList = new ArrayList<>();
        orderReadyList = new ArrayList<>();


        try {
            // This means there is no license
            if(requestBean.getBusinessID() == 0 ||
                    requestBean.getStoreFrontCD() == 0 ||
                    requestBean.getUsername() == null ||
                    requestBean.getPassword() == null ||
                    requestBean.getUsername().trim().length() == 0 ||
                    requestBean.getPassword().trim().length() == 0) {

                if(TVWaitQueueApplication.getCurrentActivity() != null &&
                        !(TVWaitQueueApplication.getCurrentActivity() instanceof TVWaitQueueSetupActivity)) {
                    Intent i = new Intent(TVWaitQueueApplication.getCurrentActivity(), TVWaitQueueSetupActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    TVWaitQueueApplication.getCurrentActivity().startActivity(i);
                    TVWaitQueueApplication.getCurrentActivity().overridePendingTransition(0, 0);
                    TVWaitQueueApplication.getCurrentActivity().finish();
                    return;
                }
                return;
            }
            // Flag to know if update is already in progress
            else {

                System.out.println("UTIL START : CURWAITPG: " + currentWaitPage);

                if(TVWaitQueueApplication.getCurrentActivity() != null && requestBean != null) {
                    // If wa aren't on the main activity
                    if(!(TVWaitQueueApplication.getCurrentActivity() instanceof  MainActivity)) {
                        Intent i = new Intent(TVWaitQueueApplication.getCurrentActivity(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        TVWaitQueueApplication.getCurrentActivity().startActivity(i);
                        TVWaitQueueApplication.getCurrentActivity().overridePendingTransition(0, 0);
                        TVWaitQueueApplication.getCurrentActivity().finish();
                    }

                    // Set the tv Android ID
                    //((MainActivity) TVWaitQueueApplication.getCurrentActvity()).setAndroidID("ID: " + getAndroidID());

                    // Set the tv station code
                    ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setStationCD("CD: " + requestBean.getLicenseid());

                    // Set the tv IP address
                    ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setIPAddress(getIPAddress(true));

                    // Set the tv version code
                    ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setVersionCD("Version " + getVersionCodeName());

                    // Set the tv connection status
                    //((MainActivity) TVWaitQueueApplication.getCurrentActvity()).setConnectionStatus(getIsWifi(), getIsLAN());

                    // Create JSON request
//                TVWaitQueueRequestBean requestBean = new TVWaitQueueRequestBean(getStationCD(),
//                        getLastUpdateTimeStamp());
                    // Create JSON request
                    requestBean.setLastUpdateTimestamp(getLastUpdateTimeStamp());

                    // Convert request object to String
                    Gson gson = new Gson();
                    requestBeanStr = gson.toJson(requestBean);
                    System.out.println("REQUEST MESSAGE: " + requestBeanStr);

                    // Send request for orders and get response
                    responseMessage = CommunicationUtils.sendRequest(requestBeanStr, endpointURL, 1);

                    // Create parser and process response message
                    parser = new TVWaitQueueResponseParser();
                    responseBean = parser.parse(responseMessage);

                    if (responseBean != null) {
                        // Get the store name from response
                        String storeName = responseBean.getStationProfile().getStoreName();

                        // Set the tv store name
                        if (storeName != null) {
                            ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setStoreName(storeName);
                        }

                        // Get the station code from response
                        Long stationCD = responseBean.getStationProfile().getStationCode();

                        // Set the station code
                        if (stationCD != null) {
                            ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setStationCD("CD: " + stationCD);
                        } else {
                            ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setStationCD("");
                        }

                        // Get the store message from response
                        String storeMessage = responseBean.getStationProfile().getTvMessage();

                        // Set the tv store message
                        if (storeMessage != null) {
                            ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setStoreMessage(storeMessage);
                        }

                        // Get list of orders from response
                        if (responseBean.getListOrders() != null) {
                            orderList = responseBean.getListOrders();
                        }

                        // Get profile filters
                        profileType = responseBean.getStationProfile().getWaitingQueueProfileType();
                        displayKioskOrders = responseBean.getStationProfile().getDisplayKioskOrders();
                        displayOnlineOrders = responseBean.getStationProfile().getDisplayOLOOrders();
                        displayThirdPartyOrders = responseBean.getStationProfile().getDisplayThirdPartyOrders();

                        // Loop through all orders
                        for (ListOrder orderBean : orderList) {

                            // Filter orders based on profile type
                            if (profileType == 0) { // 0 = All Order Types
                                filteredOrderList.add(orderBean);
                            }
                            if (profileType == 1) { // 1 = Pickup only
                                if (orderBean.getOrderType() == 1) {
                                    filteredOrderList.add(orderBean);
                                }
                            }
                            if (profileType == 2) { // 2 = Dine in only
                                if (orderBean.getOrderType() == 3) {
                                    filteredOrderList.add(orderBean);
                                }
                            }
                            if (profileType == 3) { // 3 = Delivery only
                                if (orderBean.getOrderType() == 2) {
                                    filteredOrderList.add(orderBean);
                                }
                            }
                            if (profileType == 4) { // 4 = Pickup & Delivery
                                if (orderBean.getOrderType() == 1 || orderBean.getOrderType() == 2) {
                                    filteredOrderList.add(orderBean);
                                }
                            }
                            if (profileType == 5) { // 5 = Pickup & Dine in
                                if (orderBean.getOrderType() == 1 || orderBean.getOrderType() == 3) {
                                    filteredOrderList.add(orderBean);
                                }
                            }
                            if (profileType == 6) { // 6 = Dine in & Delivery
                                if (orderBean.getOrderType() == 2 || orderBean.getOrderType() == 3) {
                                    filteredOrderList.add(orderBean);
                                }
                            }
                        }

                        // Filter kiosk orders
                        if (!displayKioskOrders) {
                            filteredOrderList = filterKioskOrders(filteredOrderList);
                        }
                        // Filter online orders
                        if (!displayOnlineOrders) {
                            filteredOrderList = filterOnlineOrders(filteredOrderList);
                        }
                        // Filter third party orders
                        if (!displayThirdPartyOrders) {
                            filteredOrderList = filterThirdPartyOrders(filteredOrderList);
                        }

                        // Add orders to appropriate list
                        for (ListOrder order : filteredOrderList) {
                            // If order was marked as fulfilled
                            if (order.getOrderFulfilledDateAsLong() > 0) {
                                // If fulfilled date is greater than the last
                                if (order.getOrderFulfilledDateAsLong() > lastFulfilledTime) {
                                    // Update last fulfilled time
                                    lastFulfilledTime = order.getOrderFulfilledDateAsLong();
                                    // Show toast notification
                                    ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).showToast(order.getTicketNumber(), order.getGuestName());
                                }
                                // Add it to the ready list
                                orderReadyList.add(order);
                            } else if (order.getOrderFulfilledDateAsLong() == 0) { // Add to wait list
                                orderWaitList.add(order);
                            } else {
                                // negative or null
                            }
                        }

                        // Create paginator to separate order lists into pages
//                        PaginatorUtil paginatorWait = new PaginatorUtil(orderWaitList);
//                        PaginatorUtil paginatorReady = new PaginatorUtil(orderReadyList);
                        paginatorWait.setOrderList(orderWaitList);
                        paginatorReady.setOrderList(orderReadyList);

                        // Calculate number of wait pages
                        totalWaitPages = orderWaitList.size() / paginatorWait.getItemsPerPage();
                        if (orderWaitList.size() % paginatorWait.getItemsPerPage() > 0) {
                            totalWaitPages++;
                        }
                        if (totalWaitPages > 0) {
                            totalWaitPages -= 1;
                        }

                        // Calculate number of ready pages
                        totalReadyPages = orderReadyList.size() / paginatorReady.getItemsPerPage();
                        if (orderReadyList.size() % paginatorReady.getItemsPerPage() > 0) {
                            totalReadyPages++;
                        }
                        if (totalReadyPages > 0) {
                            totalReadyPages -= 1;
                        }

                        // Update tv counters
                        ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).updateWaitListCounter(orderWaitList);
                        ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).updateReadyListCounter(orderReadyList);

                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();

//            // No longer running
//            isRunning = false;
        }
    }

    /**
     * Start the timer to display the orders
     */
    private void startTimer() {
        if(timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // When the app first starts there isn't an activity set
                if(TVWaitQueueApplication.getCurrentActivity() != null && TVWaitQueueApplication.getCurrentActivity() instanceof MainActivity) {
                    // Update tv lists
                    if (currentWaitPage <= totalWaitPages) {
                        ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setWaitListAdapter(paginatorWait.generatePage(currentWaitPage));
                        ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).getWaitListAdapter().setOffset(currentWaitPage * paginatorWait.getItemsPerPage());
                        currentWaitPage += 1;
                    }
                    if (currentReadyPage <= totalReadyPages) {
                        ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).setReadyListAdapter(paginatorReady.generatePage(currentReadyPage));
                        ((MainActivity) TVWaitQueueApplication.getCurrentActivity()).getReadyListAdapter().setOffset(currentReadyPage * paginatorReady.getItemsPerPage());
                        currentReadyPage += 1;
                    }

                    // Cancel timer once all pages are displayed
                    if ((currentWaitPage > totalWaitPages || totalWaitPages == 0) &&
                            (currentReadyPage > totalReadyPages || totalReadyPages == 0)) {

                        // Return to show first page of orders
                        currentWaitPage = 0;
                        currentReadyPage = 0;
                    }
                }
            }
        }, 0, 5000); // delay 5 seconds
    }
    /**
     * Remove all kiosk orders from list
     * @param orderList
     * @return filteredOrderList
     */
    public List<ListOrder> filterKioskOrders(List<ListOrder> orderList) {
        List<ListOrder> filteredOrderList = new ArrayList<ListOrder>();
        for (ListOrder order : orderList) {
            if (!order.getIsOrderFromKiosk()) {
                filteredOrderList.add(order);
            }
        }
        return filteredOrderList;
    }

    /**
     * Remove all online orders from list
     * @param orderList
     * @return
     */
    public List<ListOrder> filterOnlineOrders(List<ListOrder> orderList) {
        List<ListOrder> filteredOrderList = new ArrayList<ListOrder>();
        for (ListOrder order : orderList) {
            if (!order.getIsOrderFromWeb()) {
                filteredOrderList.add(order);
            }
        }
        return filteredOrderList;
    }

    /**
     * Remove all third party orders
     * @param orderList
     * @return
     */
    public List<ListOrder> filterThirdPartyOrders(List<ListOrder> orderList) {
        List<ListOrder> filteredOrderList = new ArrayList<ListOrder>();
        for (ListOrder order : orderList) {
            if (order.getOloType() == 0) {
                filteredOrderList.add(order);
            }
        }
        return filteredOrderList;
    }

    /**
     * Get the application's version code name
     * @return
     */
    public static String getVersionCodeName() {
        String versionCD = "";
        try {
            //versionCD = BuildConfig.VERSION_NAME; //There's no BuildConfig file in library module

            Context context = TVWaitQueueApplication.getAppContext().getApplicationContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            versionCD = packageInfo.versionName;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return versionCD;
    }

    /**
     * Get station code
     * @return long stationCD
     */
    public long getStationCD() {
        // TODO First time use currentMillis
        //  After, get station CD from DB
        //responseBean.getStationProfile().getStationCode();
        long stationCD = 1672849310372l;
        return stationCD;
    }

    /**
     * Get LAN IP address from interface
     * @param useIPv4 Should we use the IPv4 or IPv6 address
     * @return String address
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        //boolean isIPv4 = addr instanceof InetAddress;
                        boolean isIPv4 = sAddr.indexOf(':') < 0; // @since 12/30/22
                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Get last updated time stamp
     * @return long lastUpdateTimeStamp
     */
    public long getLastUpdateTimeStamp() {
        // TODO Fix lastUpdateTimeStamp
        long lastUpdateTimeStamp = 0L;
        return lastUpdateTimeStamp;
    }

}
