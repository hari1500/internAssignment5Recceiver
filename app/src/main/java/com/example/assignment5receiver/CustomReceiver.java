package com.example.assignment5receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class CustomReceiver extends BroadcastReceiver {
    public static final String intentAction = "com.assignment.ACTION_SEND_MESS";
    public static final String messageKey = "com.assignment.ACTION_SEND_MESS.MESS";
    public static final String fileName = "com.assignment.RECEIVED_BROADCAST_MESS";
    public static final String receivedMessagesKey = "com.assignment.RECEIVED_BROADCAST_MESS.MESSAGES";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intentAction.equals(intent.getAction())) {
            String receivedMessage = intent.getStringExtra(messageKey);

            SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            Set<String> receivedMessages = sharedPreferences.getStringSet(receivedMessagesKey, new HashSet<String>());
            receivedMessages.add(receivedMessage);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(receivedMessagesKey, receivedMessages);
            editor.apply();
        }
    }
}
