package com.example.nsamir.bakingapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.nsamir.bakingapp.activity.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mainActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        //Espresso.registerIdlingResources(mIdlingResource);
    }
    @Test
    public void onRecipeListLoaded()
    {
       //onView(anyOf(withId(R.id.errorTextView), allOf(withId(R.id.recipesRecycler)),isDisplayed())) ;
        onView(allOf(withId(R.id.recipesRecycler), isDisplayed()));
    }
    /*@After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }*/
}
