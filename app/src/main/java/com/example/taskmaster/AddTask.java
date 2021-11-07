package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddTask extends AppCompatActivity {
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