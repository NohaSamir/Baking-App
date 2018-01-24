package com.example.nsamir.bakingapp.activity;

import com.example.nsamir.bakingapp.model.Recipe;

import java.util.ArrayList;

public interface MainActivityActions {

    void bindRecipesList(ArrayList<Recipe> recipes);

    void showErrorMsg(int msgId);

    void hideErrorMsg();

    void showProgress();

    void hideProgress();

}
