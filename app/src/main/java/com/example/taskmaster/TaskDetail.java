package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetail extends AppCompatActivity {

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