package com.example.nsamir.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.nsamir.bakingapp.activity.RecipeDetailsActivity;
import com.example.nsamir.bakingapp.model.Steps;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.nsamir.bakingapp.MatchToolBarTest.matchToolbarTitle;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> recipeDetailsActivityTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class , true, false);


    @Test
    public void onRecipeDetailsActivityStart() {
        Intent intent = new Intent();

        ArrayList<Steps> steps = new ArrayList<>();
        Steps step1 = new Steps(1 , "Mix" , "Mix dry ingredents" , "" , "");
        steps.add(step1);


        intent.putExtra(RecipeDetailsActivity.STEPS_LIST, steps);
        intent.putExtra(RecipeDetailsActivity.STEP_NUM , 0);

        recipeDetailsActivityTestRule.launchActivity(intent);

        onView(withId(R.id.recipe_description_text)).check(matches(isDisplayed()));
        matchToolbarTitle(step1.getShortDescription());
    }

}
