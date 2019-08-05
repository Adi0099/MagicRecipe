package com.example.magicrecipe.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


import com.example.magicrecipe.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImageToURL extends AsyncTask< String, Void, Bitmap > {

    public ImageView imageViewRecipe;

    public GetImageToURL(ImageView imageViewR){
        this.imageViewRecipe = imageViewR;
    }

    @Override
    protected Bitmap doInBackground(String...params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap myBitMap) {
        imageViewRecipe.setImageBitmap(myBitMap);
        if (myBitMap==null){
            imageViewRecipe.setImageResource(R.mipmap.ic_cutlery_foreground);
        }
    }
}
