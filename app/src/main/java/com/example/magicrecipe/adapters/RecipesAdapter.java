package com.example.magicrecipe.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.magicrecipe.R;
import com.example.magicrecipe.model.Recipe;
import com.example.magicrecipe.tools.GetImageToURL;

import java.util.ArrayList;

public class RecipesAdapter extends ArrayAdapter<Recipe> {

    public RecipesAdapter(Context context, int itemLayout, ArrayList<Recipe> items) {
        super(context, itemLayout, items);
    }

    @NonNull
    public View getView(int position, View itemView, @NonNull ViewGroup parent) {
        if (itemView == null) {
            itemView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.list_item, parent, false);
        }
        ImageView imageViewRecipe = (ImageView) itemView.findViewById(R.id.imageViewRecipe);
        new GetImageToURL(imageViewRecipe).execute(this.getItem(position).image);
        TextView textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(this.getItem(position).title);
        TextView textViewIngredients = (TextView) itemView.findViewById(R.id.textViewIngredients);
        textViewIngredients.setText(this.getItem(position).ingredients);
        TextView textViewWeb = (TextView) itemView.findViewById(R.id.textViewWeb);
        textViewWeb.setText(this.getItem(position).web);
        return itemView;
    }
}


