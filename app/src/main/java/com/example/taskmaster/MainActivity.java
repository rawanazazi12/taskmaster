package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Task> addedTasks;

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return super.openFileOutput(name, mode);
    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("***************ON CREATE ****************");
         Intent intent = getIntent();
         String extra = intent.getStringExtra("Configured");
         System.out.println(extra);
        if (extra == null){
        try {
            System.out.println("**********TRY METHOD************");
            Amplify.addPlugin(new AWSDataStorePlugin());
            // stores records locally
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }
         }

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

//        List<Task> taskData = new ArrayList<>();
//        taskData.add(new Task("Task 1" , "Linked List revision", "In progress"));
//        taskData.add(new Task("Task 2 ", "Review Stacks and Queues" ,"New"));
//        taskData.add(new Task("Task 3 ", "Solve the Lab" ,"Complete"));



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



//    private void configureAmplify()  {
//
//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin()); // stores records locally
//            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
//            Amplify.configure(getApplicationContext());
//
//            Log.i(TAG, "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e(TAG, "Could not initialize Amplify", error);
//        }
//
//    }

//    private List<com.amplifyframework.datastore.generated.model.Task> GetDataFromCloud() {
//
//        List<com.amplifyframework.datastore.generated.model.Task> expenses=new ArrayList<>();
//        Amplify.DataStore.query(
//                com.amplifyframework.datastore.generated.model.Task.class,
//                queryMatches -> {
//                    while (queryMatches.hasNext()) {
//                        Log.i(TAG, "Successful query, found tasks.");
//                        expenses.add( queryMatches.next());
//                        System.out.println(expenses.toString()+"EXPENSESSSSSSSSSSSSS");
//
//                    }
//                },
//                error -> {
//                    Log.i(TAG,  "Error retrieving expenses", error);
//                });
//        return  expenses;
//    }

    @Override
    protected void onResume() {
        System.out.println("******************ON RESUME ****************");

        super.onResume();
// Getting data from the cloud

//        List<com.amplifyframework.datastore.generated.model.Task> expenses=new ArrayList<>();
//        Amplify.DataStore.query(
//                com.amplifyframework.datastore.generated.model.Task.class,
//                queryMatches -> {
//                    while (queryMatches.hasNext()) {
//                        Log.i(TAG, "Successful query, found tasks.");
//                        expenses.add( queryMatches.next());
//                        expenses.toString();
//                        Set<String> set = new HashSet<String>();
//                        set.addAll(expenses);
//                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putStringSet("key", set);
//                        editor.commit();
//
//                        editor.putString("EXPENSES", expenses.toString());
//                        editor.apply();
//
//                    }
//                },
//                error -> {
//                    Log.i(TAG,  "Error retrieving expenses", error);
//                });
//        System.out.println(expenses+"EXPENSESSSSSSSSSSSSS");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String USER = sharedPreferences.getString("USERNAME","");
        TextView intro = findViewById(R.id.userTasks);
        intro.setText(USER+"'s " + "TASKS");

        TaskDao taskDao;
        AppDatabase appDatabase;
//        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
//        addedTasks = (ArrayList<Task>) AppDatabase.getInstance(this).taskDao().getAll();

        addedTasks = new ArrayList<>();
//        getTasksDataFromCloud();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new TaskAdapter(addedTasks));
        System.out.println(addedTasks+"HEREEEEEEEEEEE");
        recyclerView.setAdapter(new TaskAdapter(addedTasks,  new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intentTaskDetails = new Intent(getApplicationContext(), TaskDetail.class);
                intentTaskDetails.putExtra("task_title", addedTasks.get(position).title);
                intentTaskDetails.putExtra("task_body", addedTasks.get(position).body);
                intentTaskDetails.putExtra("task_state", addedTasks.get(position).state);
                startActivity(intentTaskDetails);

            }
        }));
    }

    private void getTasksDataFromCloud() {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {
                        addedTasks.add(task);
                        System.out.println(addedTasks+"BBBBBBBBBBB");
                        Log.i(TAG, "Successfully getting Task Title: " + task.getTitle());
                    }
                },
                error -> Log.e(TAG, "Failed to get Tasks from From Cloud: " + error.toString())
        );

        System.out.println(addedTasks+"AAAAAAAAAAAAAAAAAAA");
    }
}