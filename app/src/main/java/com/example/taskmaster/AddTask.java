package com.example.taskmaster;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "AddTask";
    Handler handler;
    private String teamId = "";

// LAB42

    private FusedLocationProviderClient fusedLocationClient;

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.i(TAG, "The location is => " + mLastLocation);
        }
    };


    private GoogleMap googleMap;
    private static final int PERMISSION_ID = 44;
    double longitude;
    double latitude;

    private final List<Team> teams = new ArrayList<>();

    // LAB 37
    static String format = "dd-MM-yyyy";
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    private static String uploadedFileName = simpleDateFormat.format(new Date());
    private static String uploadedFileExtension = null;
    private static File uploadFile = null;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        gettingImageFromDifferentApp();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //  LAB42
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
//
//            boolean x = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//            return;
//        }


        final int[] counter = {1};
        Button button = findViewById(R.id.add_task_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskTitleField = findViewById(R.id.task_input);
                String taskTitle = taskTitleField.getText().toString();

                EditText taskDescriptionField = findViewById(R.id.task_description_input);
                String taskDescription = taskDescriptionField.getText().toString();

                EditText taskStateField = findViewById(R.id.task_state_input);
                String taskState = taskStateField.getText().toString();

                RadioButton team1Btn = findViewById(R.id.team1_id);
                RadioButton team2Btn = findViewById(R.id.team2_id);
                RadioButton team3Btn = findViewById(R.id.team3_id);

                String id = "0";
                if (team1Btn.isChecked()) {
                    id = "1";
                } else if (team2Btn.isChecked()) {
                    id = "2";
                } else if (team3Btn.isChecked()) {
                    id = "3";
                }


                dataStore(taskTitle, taskDescription, taskState, id);

                Task task = new Task(taskTitle, taskDescription, taskState);

                Long addedTaskId = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
                System.out.println("*************************" + "Task Id = " + addedTaskId);

                TextView total = findViewById(R.id.total_tasks);
                total.setText("Total Tasks : " + counter[0]++);

// record event when the user hit add task button
                recordAddTaskButton();

                Toast submitted = Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_SHORT);
                submitted.show();
            }

        });

        Button backToHome = findViewById(R.id.home_button);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddTask.this, MainActivity.class);
                intent.putExtra("Configured", "Already configured");
                startActivity(intent);
            }
        });

        // upload button

        Button uploadBtn = findViewById(R.id.upload_Btn);
        uploadBtn.setOnClickListener(view -> {
            selectFileFromDevice();
        });
    }

//    private void dataStore(String taskTitle, String taskDescription, String taskState) {
//    }

    private void dataStore(String taskTitle, String taskBody, String taskState, String id) {
        com.amplifyframework.datastore.generated.model.Task task = com.amplifyframework.datastore.generated.model
                .Task.builder()
                .teamId(id).title(taskTitle).body(taskBody).state(taskState)
                .fileName(uploadedFileName + "." + uploadedFileExtension.split("/")[1]).lat(latitude)
                .lon(longitude)
                .build();


        Amplify.API.mutate(
                ModelMutation.create(task), result -> {
                    Log.i(TAG, "Task Saved");
                }, error -> {
                    Log.i(TAG, "Task Not Saved");
                }
        );

        // uploading file
        Amplify.Storage.uploadFile(
                uploadedFileName + "." + uploadedFileExtension.split("/")[1],
                uploadFile,
                success -> {
                    Log.i(TAG, "Successfully uploaded:  " + success.getKey());
                },
                error -> {
                    Log.e(TAG, "Upload failed " + error.toString());
                }
        );

        Toast toast = Toast.makeText(getApplicationContext(), "Task added!", Toast.LENGTH_LONG);
        toast.show();
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

// get file from device

    public void selectFileFromDevice() {
        Intent upload = new Intent(Intent.ACTION_GET_CONTENT);
        upload.setType("*/*");
        upload = Intent.createChooser(upload, "Choose a File");
        startActivityForResult(upload, 200);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            uploadedFileExtension = getContentResolver().getType(uri);

            Log.i(TAG, "onActivityResult: file extension is " + uploadedFileExtension);
            Log.i(TAG, "onActivityResult: " + data.getData());
            uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");

            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inputStream, new FileOutputStream(uploadFile));
            } catch (Exception exception) {
                Log.e(TAG, "File Upload FAILED" + exception.toString());
            }

        }
    }

    private void recordAddTaskButton() {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Add Task Button Pressed")
                .addProperty("Channel", "SMS")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .addProperty("UserAge", 120.3)
                .build();

        Amplify.Analytics.recordEvent(event);
    }

    public void gettingImageFromDifferentApp() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        ImageView image = findViewById(R.id.image_from_other_app);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    image.setImageURI(imageUri);
                    image.setVisibility(View.VISIBLE);
                }
            }
        }
    }



    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            boolean x = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
//                            Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
//                            try {
//                                List<Address> potato= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),2);
//                                potato.get(0).getCountryName();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            System.out.println("Latitude: " + latitude + " - " + "Longitude: " + longitude);
                        }
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(10);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // this may or may not be needed
        fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLastLocation();
    }


}

