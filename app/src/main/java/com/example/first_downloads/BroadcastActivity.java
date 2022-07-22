package com.example.first_downloads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastActivity extends AppCompatActivity {

    // creating a variable for
    // our text view and button
    private TextView headingTV;
    private Button sendBroadCastBtn;
    private Button btn_new_start;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);


        headingTV = findViewById(R.id.idTVHeading);
        sendBroadCastBtn = findViewById(R.id.idBtnStartBroadCast);
        btn_new_start = findViewById(R.id.btn_new_start);


       LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("custom"));

        sendBroadCastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("custom");
                intent.putExtra("message", "Welcome \n to \n Geeks For Geeks");

                LocalBroadcastManager.getInstance(BroadcastActivity.this).sendBroadcast(intent);
            }
        });



        btn_new_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    // on below line we are creating a new broad cast manager.
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // we will receive data updates in onRecieve method.
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            // on below line we are updating the data in our text view.
            headingTV.setText(message);
        }
    };
}
