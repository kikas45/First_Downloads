package com.example.first_downloads;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.euicc.DownloadableSubscription;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieManager;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btn_donload, btn_start;
    TextView textView;
    private  static  int RESULT_CODE = 100;
    public  static final String imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazy-loading-fa843.appspot.com/o/What%20is%20Physics_.mp4?alt=media&token=ba2cca42-4d6d-4cd9-9e7e-d017995c2ece";
    String imageName = "demoimage.png";

    private TextView headingTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headingTV = findViewById(R.id.text_reciver);

        // on below line we are registering our local broadcast manager.
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("custom"));



        btn_donload = findViewById(R.id.btn_download);
        btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BroadcastActivity.class);
                startActivity(intent);
            }
        });

        // for storage runtime pwrmsion

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_CODE);

            }else {
                Toast.makeText(this, "pls enable it", Toast.LENGTH_SHORT).show();
            }
        }


        btn_donload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadImage(imageUrl, imageName);

            }
        });
    }

    public void  downloadImage(String url, String outFileName){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(imageName);
        request.setDescription("Downloading " + imageName );
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outFileName);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);


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