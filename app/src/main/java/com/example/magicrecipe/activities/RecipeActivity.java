package com.example.magicrecipe.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magicrecipe.R;
import com.example.magicrecipe.tools.GetImageToURL;


public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back in actionbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setTitle("");

        setContentView(R.layout.activity_recipe);

        TextView textViewtitle = findViewById(R.id.textViewTitle);
        TextView textViewIngredients = findViewById(R.id.textViewIngredients);
        TextView textViewWeb = findViewById(R.id.textViewWeb);
        ImageView imageViewRecipe = findViewById(R.id.imageViewRecipe);

        textViewtitle.setText(getIntent().getExtras().getString("title"));
        textViewIngredients.setText(getIntent().getExtras().getString("ingredients"));
        textViewWeb.setText(getIntent().getExtras().getString("web"));
        String imageView = getIntent().getExtras().getString("image");
        new GetImageToURL(imageViewRecipe).execute(imageView);
    }

    // back in actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
