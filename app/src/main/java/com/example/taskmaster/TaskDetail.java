package com.example.taskmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ((TextView) findViewById(R.id.TASK_TITLE)).setText(intent.getExtras().getString("title"));
        ((TextView) findViewById(R.id.DESCRIPTION)).setText(intent.getExtras().getString("body"));
        ((TextView) findViewById(R.id.state)).setText(intent.getExtras().getString("state"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_task_detail);
        Button home = findViewById(R.id.home_Btn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TaskDetail.this , MainActivity.class);
                startActivity(intent1);
            }

        });

//        Intent intent2 = getIntent();
//        String taskTitle = (String) intent2.getExtras().get("title");
//        TextView text = findViewById(R.id.TASK_TITLE);
//        text.setText(taskTitle);


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
}