package com.example.nsamir.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.nsamir.bakingapp.activity.RecipeListActivity;
import com.example.nsamir.bakingapp.model.Ingredients;
import com.example.nsamir.bakingapp.model.Recipe;
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
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> recipeListActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class , true, false);


    @Test
    public void onRecipeListActivityStart() {
        Intent intent = new Intent();

        Ingredients ingredientItem_1 = new Ingredients(2 , "cup" , "heavy cream");
        Ingredients ingredientItem_2 = new Ingredients(2 , "G" , "vanila");
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        ingredients.add(ingredientItem_1);
        ingredients.add(ingredientItem_2);

        ArrayList<Steps> steps = new ArrayList<>();
        Steps step1 = new Steps(1 , "Mix" , "Mix dry ingredents" , "" , "");
        steps.add(step1);

        Recipe recipe = new Recipe(1 , "Nutella" ,ingredients , steps, 2 , "");

        intent.putExtra("recipe", recipe);
        recipeListActivityTestRule.launchActivity(intent);

        onView(withId(R.id.recipedetails_list)).check(matches(isDisplayed()));
        matchToolbarTitle(recipe.getName());
    }
}
