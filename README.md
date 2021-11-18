# taskmaster

# LAB26

## Overview

Building a basic android app that contains three activities :

1. Main Activity
- text view
- image view 
- add task button => redirecting to add task activity on click
- all tasks button => redirecting to all tasks activity on click
- back button on the top bar

2. Add Task Activity

- text view 
- plain text
- add task button => showing a pop up message on click
- back button on the top bar

3. All Tasks Activity

- text view
- image view
- back button on the top bar


## Screenshots :

1. Main Activity

![main activity](/screenshots/mainActivity.PNG)

2. Add Task Activity

![add task activity](/screenshots/addTaskActivity.PNG)

3. All Tasks Activity

![all tasks activity](/screenshots/allTasksActivity.PNG)


# LAB27 - Adding Data to TaskMaster

## Overview

Modifying the main Activity and create new activities (Task Detail and Settings) to send data among these activities in the application using SharedPreferences and Intents.


1. Main Activity
- text view
- image view 
- add task button => redirecting to add task activity on click
- all tasks button => redirecting to all tasks activity on click
- Settings button => redirecting to Settings activity on click
- back button on the top bar
- task1 button
- task2 button
- task3 button

2. Detail Activity

- task title => it will be changed based on the button clicked in the main page
- task description
- Home button 
- back button on the top bar

3. Settings Activity
 Allows the user to input it's name and it will be stored by hitting save button

- Username Input
- save button
- home button
- back button on the top bar


## Screenshots :

1. Main Activity

![main](/screenshots/mainAct.PNG)

2. Task Detail - Task 1

![task1](/screenshots/task1.PNG)

3. Task Detail - Task 2

![task2](/screenshots/task2.PNG)

4. Task Detail - Task 3

![task3](/screenshots/task3.PNG)

5. Settings 

![settings](/screenshots/settingsActivity.PNG)


# LAB28 - RecyclerView

## Overview

Refactor the homepage to use a RecyclerView for displaying Task data. 

1. Main Activity 

![main](/screenshots/mainAct-lab28.PNG)


2. Task Detail Activity

![detail Activity](/screenshots/taskDetail-lab28.PNG)


# LAB29 - Saving Data with Room

## Overview

- Refactoring the model layer to store Task data in a local database.

- Modifying Add Task form to save the data entered in as a Task in local database.

- Refactoring  homepage’s RecyclerView to display all Task entities in database.


1. Main Activity 

![main activity](/screenshots/mainActivity-lab29.PNG)


2. Add Task Activity

![add Task](/screenshots/addTaskActivity-lab29.PNG)


3. Task Detail Activity 

![Task Detail](/screenshots/taskDetails-lab29.PNG)


# Lab: 31 - Espresso and Polish

Testing the code using Espresso Test

  ## Tests :

-  addTaskActTest()

   Testing add Task button and the textView (Add Task)


- settingActTest()    

  Testing the ability of input any username and retrive it in the main activity. 

- taskDetailActTest()      

  Testing when the user tap on a task, and then assert that the resulting activity displays the title, body and state of that task in task detail page.

- addTaskAndCheckItInTheRecyclerView()

  Testing if the user can add a new task then when tapping on this task, and assert that the resulting activity displays the title, body and state of that task in task detail page.



![Espresso Testing](/screenshots/espressoTesting.PNG)


# Lab 32: Amplify and DynamoDB

implementing AWS amplify to access the data in DynamoDB insted of Room.

- ## DynamoDB

![DynamoDB](/screenshots/dynamoDB_tasks.PNG)


- ## Main Activity

![Main Activity](/screenshots/taskApi.PNG)


# Lab: 33 - Related Data

## Overview

- Creating a second entity for a team, which has a name and a list of tasks. Update your tasks to be owned by a team.

- Saving teams to dynamoDB

![Teams DynamoDB](/screenshots/teamsApi.PNG)

- Modifying Add Task form to include either a Radio Buttons for three teams to select team that task belongs to.

- Modifying Setting Activity to allow the users to choose their team on the Settings page. then Using that Team to display only that team’s tasks on the homepage.

- ## Add Task Activity
![add task](/screenshots/lab33_addTask.PNG)

- ## Settings Activity
![Settings](/screenshots/settings.PNG)

- ## Main Activity
![main activity](/screenshots/lab33_mainAct.PNG)

# Lab: 34 - Publishing to the Play Store

Building an APK for your taskMater App

![APK](/screenshots/apk.PNG)

[APK](/app/APK/output-metadata.json)



# Lab: 36 - Cognito

## Overview 

Updating Taskmaster app to allow users to sign up and log in using Cognito.


- ## SignUp Activity

![signUp activity](/screenshots/signup.PNG)


- ## Verification Activity 

Send the users an email with the verification code after they signed up to taskmaster app

![verification Activity](/screenshots/verificationActivity.PNG)

- ## SignIn Activity

When users are verified, they will be accessed to signIn page 

![SignIn Activity](/screenshots/signinActivity.PNG)


- ## Main Activity

I've used email in auth configurations, so each user can view their email in the top bar in main Activity after they signedIn .

![Main Activity](/screenshots/mainActivity_lab36.PNG)



- ## Cognito Board

![cognito](/screenshots/cognito.PNG)


# Lab: 37 - S3 Uploads

Modify Taskmaster app to allow users to upload files related to tasks.

- ## Add Task Activity             

  Allow users to optionally select a file to attach to that task. If a user attaches a file to a task, that file should be uploaded to S3, and associated with that task.

1. Updating Add task activity to allow users to upload file to a task they want to add.

![add task](/screenshots/add_task_lab37.PNG)


2. Getting the file from the device when the user hit the upload file button.

![get file](/screenshots/select_from_device.PNG)

3. Once the task added, the file will be uploaded to S3 storage and the file name will be added to the task in DynamoDB

- **S3 Storage**
![S3 storage](/screenshots/S3_storage.PNG)


- **DynamoDB**

![dynamoDB](/screenshots/dynamoDB_lab37.PNG)



- ## Task Detail Activity

   On Task detail activity, if there is a file that is an image associated with a particular Task, that image should be displayed within that activity.

![task detail](/screenshots/taskDetail_lab37.PNG)


# Lab 38 - Notifications

Adding the ability for push notifications to be delivered to TaskMaster app from the cloud.


![notifications](/screenshots/lab38.PNG)


![Campaign](/screenshots/lab_38.PNG)


# Lab: 39 - Adding Analytics

- Tracking Users                                                
  Add Analytics to Taskmaster app . Create and send an Event whenever the user hit add task button and visited Add Task Activity .


![analytics](/screenshots/lab_39.PNG)


![analytics](/screenshots/lab39.PNG)