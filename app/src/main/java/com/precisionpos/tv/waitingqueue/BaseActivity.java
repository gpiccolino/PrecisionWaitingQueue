package com.precisionpos.tv.waitingqueue;

import android.app.Activity;

import com.precisionpos.tv.waitingqueue.app.TVWaitQueueApplication;

/**
 * @since 1/4/23
 * @author Kate
 */
public class BaseActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        ((TVWaitQueueApplication) this.getApplicationContext()).setCurrentActivity(this);
    }
}