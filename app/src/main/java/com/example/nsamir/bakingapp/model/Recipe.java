package com.example.nsamir.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable{

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private ArrayList<Ingredients> ingredients;

    @SerializedName("steps")
    private ArrayList<Steps> steps;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;


     public Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        steps = in.createTypedArrayList(Steps.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public Recipe(int id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }


    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
