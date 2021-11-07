package com.example.taskmaster;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class EspressoTesting {

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void addTaskActTest(){
        try (ActivityScenario<MainActivity> ignored= ActivityScenario.launch(MainActivity.class)){
            onView(withId(R.id.add_task)).perform(click());
            onView(withId(R.id.add_task_header)).check(matches(withText("Add Task")));
        }
    }

    @Test
    public void settingActTest() {
        try (ActivityScenario<MainActivity> ignored= ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.settings_btn)).perform(click());
            onView(withId(R.id.username)).perform(typeText("Rawan"), closeSoftKeyboard());
            onView(withId(R.id.save_btn)).perform(click());
            onView(withId(R.id.back_to_home_btn)).perform(click());
            onView(withId(R.id.userTasks)).check(matches(withText("Rawan's TASKS")));
        }
    }

    @Test
    public void taskDetailActTest() {
        try (ActivityScenario<MainActivity> ignored= ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.TASK_TITLE)).check(matches(withText("Task Num 1")));
            onView(withId(R.id.DESCRIPTION)).check(matches(withText("Solving Challenge 29")));
            onView(withId(R.id.state)).check(matches(withText("Completed")));
        }
    }

    @Test
    public void addTaskAndCheckItInTheRecyclerView() {
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.add_task)).perform(click());
            onView(withId(R.id.task_input)).perform(typeText("Solving lab 30"), closeSoftKeyboard());
            onView(withId(R.id.task_description_input)).perform(typeText("Testing Views with Espresso"), closeSoftKeyboard());
            onView(withId(R.id.task_state_input)).perform(typeText("In progress"), closeSoftKeyboard());
            onView(withId(R.id.add_task_btn)).perform(click());
            onView(withId(R.id.home_button)).perform(click());
            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));
            onView(withId(R.id.TASK_TITLE)).check(matches(withText("Solving lab 30")));
            onView(withId(R.id.DESCRIPTION)).check(matches(withText("Testing Views with Espresso")));
            onView(withId(R.id.state)).check(matches(withText("In progress")));
        }
    }
}
