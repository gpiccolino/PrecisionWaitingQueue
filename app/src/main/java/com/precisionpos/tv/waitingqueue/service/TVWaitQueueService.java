package com.precisionpos.tv.waitingqueue.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.precisionpos.tv.waitingqueue.app.TVWaitQueueApplication;
import com.precisionpos.tv.waitingqueue.communication.TVWaitQueueLANListener;

/**
 * Service to control the tv waiting queue
 */
public class TVWaitQueueService extends Service {

	// LAN listener
	private TVWaitQueueLANListener listener;
	public static final int PORT = 9102;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, Service.START_STICKY, startId);

		// Get the listener
		this.getTVWaitingQueueListener().startServer();

		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	/**
	 * Was the task removed
	 * @param rootIntent
	 */
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		super.onTaskRemoved(rootIntent);

		Intent secondaryScreenService = new Intent(this, TVWaitQueueService.class);
		startService(secondaryScreenService);

		PendingIntent service = PendingIntent.getService(
				getApplicationContext(),
				1001,
				new Intent(getApplicationContext(), TVWaitQueueService.class),
				PendingIntent.FLAG_ONE_SHOT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, service);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't provide binding, so return null
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// This will send a message to the main application
		// and will restart if necessary
		sendBroadcast(new Intent(TVWaitQueueApplication.BROADCAST_TVWAITINGQUEUELISTENER_DESTROY));
	}


	/**
	 * Get the KDS LAN listener
	 * @return
	 */
	public TVWaitQueueLANListener getTVWaitingQueueListener() {
		if (listener == null) {
			listener = TVWaitQueueLANListener.getInstance(PORT, this);
		}
		return listener;
	}


}
