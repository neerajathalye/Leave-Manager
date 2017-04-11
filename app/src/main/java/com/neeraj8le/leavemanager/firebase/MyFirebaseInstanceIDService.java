package com.neeraj8le.leavemanager.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.neeraj8le.leavemanager.SharedPrefManager;

/**
 * Created by Neeraj Athalye on 11-Apr-17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String TOKEN_BROADCAST = "broadcasttoken";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MYFIREBASEID", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.



        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
    }

}
