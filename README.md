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