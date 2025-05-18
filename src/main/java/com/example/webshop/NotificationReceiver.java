package com.example.webshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showNotification(
                context,
                "Nézz vissza a webshopba!",
                "Talán ma találsz valami újat!"
        );
    }
}