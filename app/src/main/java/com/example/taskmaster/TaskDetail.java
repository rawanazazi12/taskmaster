package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.net.URL;

public class TaskDetail extends AppCompatActivity implements OnMapReadyCallback  {

    private static final String TAG = "TaskDetail";
    private URL url =null;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
         Intent intent = getIntent();

         TextView title =(TextView) findViewById(R.id.TASK_TITLE);
         title.setText(intent.getExtras().getString("task_title"));

         TextView body =(TextView) findViewById(R.id.DESCRIPTION);
         body.setText(intent.getExtras().getString("task_body"));

         TextView state =(TextView) findViewById(R.id.state);
         state.setText(intent.getExtras().getString("task_state"));

         // LAB 37
         String fileName = intent.getStringExtra("task_file");
         ImageView imageView = findViewById(R.id.task_img);
         


        ActionBar actionBar = getSupportActionBar();
         actionBar.setDisplayHomeAsUpEnabled(true);

         Button home = findViewById(R.id.home_Btn);
         home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TaskDetail.this , MainActivity.class);
                startActivity(intent1);
            }

        });

         // Get file from S3

//
        handler = new Handler(Looper.getMainLooper(),
                message -> {
                    Glide.with(getBaseContext())
                            .load(url.toString())
                            .error(R.drawable.ic_clipboard)
                            .centerCrop()
                            .into(imageView);
                    return true;
                });

        getFileFromS3Storage(fileName);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Intent intent2 = getIntent();
//        String taskTitle = (String) intent2.getExtras().get("title");
//        TextView text = findViewById(R.id.TASK_TITLE);
//        text.setText(taskTitle);
        String fileLink = String.format("<a href=\"%s\">download File</a> ", url);
        TextView link = findViewById(R.id.file_link);
        link.setText(Html.fromHtml(fileLink));
        link.setMovementMethod(LinkMovementMethod.getInstance());

        // LAB42

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Intent intent = getIntent();
        Log.i("YOUR LOCATION ", "LOCATION DATA  : " + intent.getExtras().getDouble("lat")+"  -  "+   intent.getExtras().getDouble("lon")  );
        LatLng loc = new LatLng( intent.getExtras().getDouble("lat"),intent.getExtras().getDouble("lon"));
        googleMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void getFileFromS3Storage(String key) {
        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir() + key),
                result -> {
                    Log.i(TAG, "Successfully downloaded: " + result.getFile().getAbsoluteFile());
                },
                error -> Log.e(TAG,  "Download Failure", error)
        );

        Amplify.Storage.getUrl(
                key,
                result -> {
                    Log.i(TAG, "Successfully generated: " + result.getUrl());
                    url= result.getUrl();
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, "URL generation failure", error)
        );
    }

}