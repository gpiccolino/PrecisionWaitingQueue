package com.precisionpos.tv.waitingqueue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.precisionpos.tv.waitingqueue.beans.ListOrder;
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
}