package com.example.nsamir.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nsamir.bakingapp.R;
import com.example.nsamir.bakingapp.model.Ingredients;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private ArrayList<Ingredients> mIngredients;

    IngredientsAdapter(ArrayList<Ingredients> mIngredients) {
        this.mIngredients = mIngredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Ingredients ingredient = mIngredients.get(position);
        String ingredientText = ingredient.getQuantity() + " " + ingredient.getMeasure() +
                " - " + ingredient.getIngredient();
        holder.ingredientText.setText(ingredientText);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.ingredientText)
        TextView ingredientText;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this , itemView);
        }
    }
}
