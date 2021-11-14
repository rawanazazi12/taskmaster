package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Task> addedTasks = new ArrayList<>();
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("***************ON CREATE ****************");
        Intent intent = getIntent();
        String extra = intent.getStringExtra("Configured");
        System.out.println(extra);
//        if (extra == null) {
//
//        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        Button button = findViewById(R.id.add_task);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent1);
            }
        });

        Button button2 = findViewById(R.id.all_tasks);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, AllTasks.class);
                startActivity(intent2);
            }
        });

        Button button3 = findViewById(R.id.task1_btn);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, TaskDetail.class);
                TextView text = findViewById(R.id.task1_btn);
                String data = text.getText().toString();
                intent3.putExtra("title", data);
                startActivity(intent3);

            }

        });

        Button button4 = findViewById(R.id.task2_btn);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, TaskDetail.class);
                TextView text = findViewById(R.id.task2_btn);
                String data = text.getText().toString();
                intent4.putExtra("title", data);
                startActivity(intent4);

            }

        });

        Button button5 = findViewById(R.id.task3_btn);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(MainActivity.this, TaskDetail.class);
                TextView text = findViewById(R.id.task3_btn);
                String data = text.getText().toString();
                intent5.putExtra("title", data);
                startActivity(intent5);

            }

        });

        Button button6 = findViewById(R.id.settings_btn);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(MainActivity.this, Settings.class);
                startActivity(intent6);
            }
        });

        // Lab 28 create data to use it in the view

//        List<Task> taskData = new ArrayList<>();
//        taskData.add(new Task("Task 1" , "Linked List revision", "In progress"));
//        taskData.add(new Task("Task 2 ", "Review Stacks and Queues" ,"New"));
//        taskData.add(new Task("Task 3 ", "Solve the Lab" ,"Complete"));

        saveTeamToApi("Jo Hikers","1");
        saveTeamToApi("React Divers","2");
        saveTeamToApi("Runtime terror Team","3");


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
        System.out.println("******************ON RESUME ****************");

        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String USER = sharedPreferences.getString("USERNAME", "");
        String teamName = sharedPreferences.getString("TeamName", "");
        String name = sharedPreferences.getString("name", "");

        if (!USER.equals("")) {
            TextView intro = findViewById(R.id.userTasks);
            intro.setText(USER + "'s " + "TASKS");
        }

        if (!name.equals("")){
            TextView teamNameField = findViewById(R.id.team_tasks_field);
            teamNameField.setText(name + "'s " + "TASKS");
        }

// Getting loggedin username
        CharSequence loggedInUser = sharedPreferences.getString("email", "");
        setTitle(loggedInUser + " Profile");


// Sign out user
          Button signOutBtn = findViewById(R.id.sign_out_btn);
          signOutBtn.setOnClickListener(view -> {
              signOutUser();
          });


//        TaskDao taskDao;
//        AppDatabase appDatabase;
//        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
//        addedTasks = (List<com.amplifyframework.datastore.generated.model.Task>) AppDatabase.getInstance(this).taskDao().getAll();

        RecyclerView allTasksRecyclerView = findViewById(R.id.recycler_view);
        List<Task> tasks= new ArrayList<>();
        if(teamName.equals("")){
            tasks = getTasksFromApi(allTasksRecyclerView);
        }
        else{
            tasks = getTeamDataFromApi(allTasksRecyclerView);
        }

        Log.i("Rawan",tasks.toString());
        allTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTasksRecyclerView.setAdapter(new TaskAdapter(tasks));

        Log.i(TAG, "onResume: tasks " + tasks);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TaskAdapter(addedTasks));
        List<Task> finalTasks = tasks;
        recyclerView.setAdapter(new TaskAdapter(tasks, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intentTaskDetails = new Intent(getApplicationContext(), TaskDetail.class);
                System.out.println(finalTasks.get(position).getTeamId()+"HEREEEEEEEEEE");
                intentTaskDetails.putExtra("task_title", finalTasks.get(position).getTitle());
                intentTaskDetails.putExtra("task_body", finalTasks.get(position).getBody());
                intentTaskDetails.putExtra("task_state", finalTasks.get(position).getState());
                intentTaskDetails.putExtra("team_id", finalTasks.get(position).getTeamId()+"team");
                startActivity(intentTaskDetails);

            }
        }));
    }

    private  List<Task> getTasksFromApi( RecyclerView recycler_view ){
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                recycler_view.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
        List<Task> addedTasks=new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task task : response.getData()) {
                        addedTasks.add(task);
                        addedTasks.toString();
                        Log.i(TAG, addedTasks.toString());
                        Log.i(TAG, "Successful query, Task Found.");
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, "TASK NOT FOUND.")
        );

        return  addedTasks;
    }


    private  List<Task> getTeamDataFromApi( RecyclerView recycler_view ){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String team = sharedPreferences.getString("TeamName","");
        String name = sharedPreferences.getString("name","");
        System.out.println(team +"TEAAAAAAAAAAMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                recycler_view.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        List<Task> addedTasks=new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Task.class,Task.TEAM_ID.contains(team)),
                response -> {
                    for (Task task : response.getData()) {
                        addedTasks.add(task);
                        addedTasks.toString();
                        Log.i(TAG, addedTasks.toString());
                        Log.i(TAG, "Successful query, Tasks Found.");
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.i(TAG, "TASK NOT FOUND")
        );

        return  addedTasks;
    }

    // saving teams to the cloud
    private void saveTeamToApi(String teamName, String id) {
        Team team = Team.builder().teamName(teamName).id(id).build();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Amplify.API.mutate(
                ModelMutation.create(team), results -> {
                    Log.i(TAG, "Team Saved");
                    System.out.println(team.getId());
                    editor.putString("team_Id", team.getId());
                    editor.apply();
                }, error -> {
                    Log.i(TAG, "Team Not Saved");
                }
        );
    }

    public void signOutUser(){
        Amplify.Auth.signOut(
                () ->{
                    Log.i(TAG, "Signed out successfully");
                    Intent intent = new Intent(MainActivity.this, Signin.class);
                    startActivity(intent);
                },
                error -> Log.i(TAG,"Failed to Sign Out", error)
        );
    }

}