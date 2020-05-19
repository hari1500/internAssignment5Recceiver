package com.example.assignment5receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    CustomReceiver customReceiver = new CustomReceiver();
    IntentFilter intentFilter = new IntentFilter(CustomReceiver.intentAction);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(customReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(CustomReceiver.fileName, Context.MODE_PRIVATE);
        Set<String> receivedMessages = sharedPreferences.getStringSet(CustomReceiver.receivedMessagesKey, null);
        if (receivedMessages != null) {
            receivedMessages.forEach(new Consumer<String>() {
                @Override
                public void accept(String msg) {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(CustomReceiver.receivedMessagesKey, new HashSet<String>());
            editor.apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(customReceiver);
    }
}
