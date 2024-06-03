package com.precisionpos.tv.waitingqueue;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.precisionpos.tv.waitingqueue.beans.ListOrder;
import com.precisionpos.tv.waitingqueue.utils.TVWaitQueueListViewAdapter;
import com.precisionpos.tv.waitingqueue.utils.TVWaitQueueReadyListViewAdapter;
import com.precisionpos.tv.waitingqueue.utils.UpdateViewUtil;

import java.util.List;

/**
 * @author Kate
 * @since 10/14/22
 */
public class MainActivity extends BaseActivity {

    private TVWaitQueueListViewAdapter waitListAdapter;
    private TVWaitQueueReadyListViewAdapter readyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Authenticate station code
        // TODO If auth returns false, display QR setup

        // Main tv activity
        setContentView(R.layout.activity_tv_main);

        // Set IP Address
        setIPAddress(UpdateViewUtil.getIPAddress(true));

        // Set the build version code name
        setVersionCD("Version " + UpdateViewUtil.getVersionCodeName());

        // Set Wait list adapter
        waitListAdapter = new TVWaitQueueListViewAdapter(this, this.getApplicationContext());
        ListView waitListView = findViewById(R.id.tv_waitlist);
        waitListView.setAdapter(waitListAdapter);

        // Set Ready list adapter
        readyListAdapter = new TVWaitQueueReadyListViewAdapter(this, this.getApplicationContext());
        ListView readyListView = findViewById(R.id.tv_readylist);
        readyListView.setAdapter(readyListAdapter);
    }

    /**
     * Show toast in footer
     * @param ticketNumber The ticket number that was marked ready
     * @param guestName The guest name on the ticket number that was marked ready
     */
    public void showToast(int ticketNumber, String guestName) {
        runOnUiThread(() -> {
            // Set toast text
            Toast toast = Toast.makeText(MainActivity.this, "Ticket #" + ticketNumber +
                    " for " + guestName + " is ready for pickup!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 15, 15);
            // Get toast view
            View view = toast.getView();
            // Set toast background
            view.getBackground().setColorFilter(Color.parseColor("#c9f1fd"), PorterDuff.Mode.DARKEN);
            // Show toast
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(toast::cancel, 8000); // Close toast after 8 seconds
        });
    }

    /**
     * Set the tv custom store message
     * @param storeMessage A custom message to display on the top left of the app
     */
    public void setStoreMessage(String storeMessage) {
        runOnUiThread(() -> ((TextView)findViewById(R.id.tv_store_note)).setText(storeMessage));
    }

    /**
     * Set the tv Station Code
     * @param stationCD A unique code to identify a station.
     *                  This will be the time in ms when the app is installed.
     */
    public void setStationCD(String stationCD) {
        runOnUiThread(() -> ((TextView)findViewById(R.id.tv_station_code)).setText(stationCD));
    }

    /**
     * Set the tv Version Code
     * @param versionCD The build version of this app (ie. Version 2.0)
     */
    public void setVersionCD(String versionCD) {
        runOnUiThread(() -> ((TextView)findViewById(R.id.tv_version_code)).setText(versionCD));
    }

    /**
     * Set the IP Address
     * @param ipAddress The IPv4 address of the running application
     */
    public void setIPAddress(String ipAddress) {
        runOnUiThread(() -> ((TextView)findViewById(R.id.tv_ip_address)).setText(ipAddress));
    }

    /**
     * Set the store name
     * @param storeName The name of the restaurant
     */
    public void setStoreName(String storeName) {
        runOnUiThread(() -> ((TextView)findViewById(R.id.tv_store_name)).setText(storeName));
    }

    /**
     * Set the wait list adapter
     * @param orderWaitList List of orders where "customer is waiting"
     */
    public void setWaitListAdapter(List<ListOrder> orderWaitList) {
        runOnUiThread(() -> waitListAdapter.setOrderList(orderWaitList));
    }

    /**
     * Get wait list adapter
     * @return waitListAdapter
     */
    public TVWaitQueueListViewAdapter getWaitListAdapter() {
        return waitListAdapter;
    }

    /**
     * Set the ready list adapter
     * @param orderReadyList List of orders with status "ready"
     */
    public void setReadyListAdapter(List<ListOrder> orderReadyList) {
        runOnUiThread(() -> readyListAdapter.setOrderList(orderReadyList));
    }

    /**
     * Get ready list adapter
     * @return readyListAdapter
     */
    public TVWaitQueueReadyListViewAdapter getReadyListAdapter() {
        return readyListAdapter;
    }

    /**
     * Update the wait list counter
     * @param orderWaitList List of orders where "customer is waiting"
     */
    public void updateWaitListCounter(List<ListOrder> orderWaitList) {
        runOnUiThread(() -> {
            TextView tvWaitCounter = findViewById(R.id.tv_wait_counter);
            tvWaitCounter.setText(String.valueOf(orderWaitList.size()));
        });
    }

    /**
     * Update the ready list counter
     * @param orderReadyList List of orders with status "ready"
     */
    public void updateReadyListCounter(List<ListOrder> orderReadyList) {
        runOnUiThread(() -> {
            TextView tvWaitCounter = findViewById(R.id.tv_ready_counter);
            tvWaitCounter.setText(String.valueOf(orderReadyList.size()));
        });
    }
}