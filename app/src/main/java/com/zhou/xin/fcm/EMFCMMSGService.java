package com.zhou.xin.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zhou.xin.utils.EMNotificationManager;

/**
 * Created by zhou on 2017/12/21.
 */

public class EMFCMMSGService extends FirebaseMessagingService {
    private static final String TAG = "EMFCMMSGService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            String message = remoteMessage.getData().get("alert");
            Log.i(TAG, "onMessageReceived: " + message);
            EMNotificationManager.getInstance(this).sendNotification(message);
        }
    }
}
