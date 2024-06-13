package com.precisionpos.tv.waitingqueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.precisionpos.tv.waitingqueue.beans.ListOrder;
import com.precisionpos.tv.waitingqueue.beans.StationProfileBean;
import com.precisionpos.tv.waitingqueue.utils.StationProfileConfigSession;
import com.precisionpos.tv.waitingqueue.utils.TVWaitQueueListViewAdapter;
import com.precisionpos.tv.waitingqueue.utils.TVWaitQueueReadyListViewAdapter;
import com.precisionpos.tv.waitingqueue.utils.UpdateViewUtil;

import java.util.List;

/**
 * @author Kate
 * @since 10/14/22
 */
public class TVWaitQueueSetupActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Main tv activity
        setContentView(R.layout.activity_tv_activate);
    }


    /**
     * Override to set station settings
     */
    @Override
    public void onResume() {
        super.onResume();

        // Set IP Address
        String ipAddress = UpdateViewUtil.getIPAddress(true);
        if(ipAddress == null || ipAddress.trim().length() == 0) {
            ((TextView)findViewById(R.id.setup_instr1)).setText(R.string.activate_step_1_label);
        }
        else {
            ((TextView)findViewById(R.id.setup_instr1)).setText(R.string.activate_step_1a_label);
        }
        setIPAddress(ipAddress);

        // Set the build version code name
        setVersionCD("Version " + UpdateViewUtil.getVersionCodeName());

        // Set the station code
        StationProfileBean profileBean = StationProfileConfigSession.getProfileDetailsBean();
        setStationCD("CD: " + profileBean.getLicenseid());

    }
    /**
     * Open up the android settings
     */
    @Override
    public void onBackPressed() {
        this.processAndroidSetting();
    }

    /**
     * Go to the android settings
     */
    public void processAndroidSetting() {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);

        try {
            // Start the activity, the intent will be populated with the speech text
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            e.printStackTrace();;
        }
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
        if(ipAddress == null || ipAddress.trim().length() == 0) {
            findViewById(R.id.tv_ip_address).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.tv_ip_address).setVisibility(View.VISIBLE);
            runOnUiThread(() -> ((TextView) findViewById(R.id.tv_ip_address)).setText(ipAddress));
        }
    }

}