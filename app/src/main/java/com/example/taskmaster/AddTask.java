package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "AddTask";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final int[] counter = {1};
        Button button = findViewById(R.id.add_task_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText taskTitleField = findViewById(R.id.task_input);
                String taskTitle = taskTitleField.getText().toString();

                EditText taskDescriptionField = findViewById(R.id.task_description_input);
                String taskDescription = taskDescriptionField.getText().toString();

                EditText taskStateField = findViewById(R.id.task_state_input);
                String taskState = taskStateField.getText().toString();

                dataStore(taskTitle, taskDescription, taskState);

                Task task = new Task(taskTitle, taskDescription , taskState);

                Long addedTaskId = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
                System.out.println("*************************" + "Task Id = "+ addedTaskId);

                TextView total = findViewById(R.id.total_tasks);
                total.setText("Total Tasks : " + counter[0]++);

                Toast submitted = Toast.makeText(getApplicationContext(),"Submitted!",Toast.LENGTH_SHORT);
                submitted.show();
            }
        });

        Button backToHome = findViewById(R.id.home_button);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTask.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dataStore(String taskTitle, String taskBody,String taskState) {
        com.amplifyframework.datastore.generated.model.Task task = com.amplifyframework.datastore.generated.model
                .Task.builder().title(taskTitle).body(taskBody).state(taskState).build();

        // save with the datastore
        Amplify.DataStore.save(task, result -> {
            Log.i(TAG, "Task Saved");
        }, error -> {
            Log.i(TAG, "Task Not Saved");
        });

        // query with the datastore
//        Amplify.DataStore.query(
//                Task.class,
//                queryMatches -> {
//                    while (queryMatches.hasNext()) {
//                        Log.i(TAG, "Successful query, found tasks.");
//                        Task foundExpense = queryMatches.next();
//                        Log.i(TAG, foundExpense.getTitle());
//                        label.setText(foundExpense.getTitle());
//                    }
//                },
//                error -> {
//                    Log.i(TAG,  "Error retrieving expenses", error);
//                });
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


}