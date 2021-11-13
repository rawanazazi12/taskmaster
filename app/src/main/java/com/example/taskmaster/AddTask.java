package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private static final String TAG = "AddTask";
    Handler handler;
    private String teamId = "";

    private final List<Team> teams = new ArrayList<>();
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

                RadioButton team1Btn = findViewById(R.id.team1_id);
                RadioButton team2Btn = findViewById(R.id.team2_id);
                RadioButton team3Btn = findViewById(R.id.team3_id);

                String id = "0";
                if(team1Btn.isChecked()){
                    id="1";
                }
                else if(team2Btn.isChecked()){
                    id="2";
                }
                else if(team3Btn.isChecked()){
                    id="3";
                }


                dataStore(taskTitle, taskDescription, taskState,id);

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
                intent.putExtra("Configured", "Already configured");
                startActivity(intent);
            }
        });
    }

//    private void dataStore(String taskTitle, String taskDescription, String taskState) {
//    }

    private void dataStore(String taskTitle, String taskBody, String taskState, String id) {
        com.amplifyframework.datastore.generated.model.Task task  =  com.amplifyframework.datastore.generated.model
                .Task.builder()
                .teamId(id).title(taskTitle).body(taskBody).state(taskState).build();


        Amplify.API.mutate(
                ModelMutation.create(task), result ->{
                    Log.i(TAG, "Task Saved");
                }, error ->{
                    Log.i(TAG, "Task Not Saved");
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


}