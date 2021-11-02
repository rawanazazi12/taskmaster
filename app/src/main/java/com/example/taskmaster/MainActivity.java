package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Button button = findViewById(R.id.add_task);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent1 = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent1);
            }
        });

        Button button2 = findViewById(R.id.all_tasks);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, AllTasks.class);
                startActivity(intent2);
            }
        });

        Button button3 = findViewById(R.id.task1_btn);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
             public void onClick(View view ){
                Intent intent3 = new Intent(MainActivity.this , TaskDetail.class);
                TextView text = findViewById(R.id.task1_btn);
                String data = text.getText().toString();
                intent3.putExtra("title",data);
                startActivity(intent3);

            }

        });

        Button button4 = findViewById(R.id.task2_btn);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view ){
                Intent intent4 = new Intent(MainActivity.this , TaskDetail.class);
                TextView text = findViewById(R.id.task2_btn);
                String data = text.getText().toString();
                intent4.putExtra("title",data);
                startActivity(intent4);

            }

        });

        Button button5 = findViewById(R.id.task3_btn);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view ){
                Intent intent5 = new Intent(MainActivity.this , TaskDetail.class);
                TextView text = findViewById(R.id.task3_btn);
                String data = text.getText().toString();
                intent5.putExtra("title",data);
                startActivity(intent5);

            }

        });

        Button button6 = findViewById(R.id.settings_btn);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(MainActivity.this , Settings.class);
                startActivity(intent6);
            }
        });

        // Lab 28 create data to use it in the view
        ArrayList<Task> taskData = new ArrayList<>();
        taskData.add(new Task("Task 1" , "Linked List revision", "In progress"));
        taskData.add(new Task("Task 2 ", "Review Stacks and Queues" ,"New"));
        taskData.add(new Task("Task 3 ", "Solve the Lab" ,"Complete"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(taskData,  new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intentTaskDetails = new Intent(getApplicationContext(), TaskDetail.class);
                intentTaskDetails.putExtra("title", taskData.get(position).title);
                intentTaskDetails.putExtra("body", taskData.get(position).body);
                intentTaskDetails.putExtra("state", taskData.get(position).state);
                startActivity(intentTaskDetails);

            }
        }));


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

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String USER = sharedPreferences.getString("USERNAME","");
        TextView intro = findViewById(R.id.userTasks);
        intro.setText(USER+"'s " + "TASKS");
    }

}