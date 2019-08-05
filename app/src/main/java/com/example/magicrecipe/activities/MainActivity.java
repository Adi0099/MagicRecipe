package com.example.magicrecipe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.magicrecipe.R;
import com.example.magicrecipe.adapters.RecipesAdapter;
import com.example.magicrecipe.model.Recipe;


import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    final Activity context = this;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    ListView myListView;
    TextView textViewInit;
    private int preLast;
    String search;
    int i ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInit = findViewById(R.id.textViewInit);
        myListView = findViewById(R.id.listViewRecipes);
        myListView.setOnScrollListener(this);
        myListView.setAdapter(new RecipesAdapter(context, R.layout.list_item, recipes));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                intent.putExtra("image",recipes.get(position).image);
                intent.putExtra("title",recipes.get(position).title);
                intent.putExtra("ingredients",recipes.get(position).ingredients);
                intent.putExtra("web",recipes.get(position).web);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String searchText) {
                if (!searchText.equals("")){
                    recipes.clear();
                    i=1;
                    makeRequest(searchText, String.valueOf(i));
                    search = searchText;
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void makeRequest(String searchText, String page) {

        textViewInit.setVisibility(View.INVISIBLE);
        String url = "http://www.recipepuppy.com/api/?q=" + searchText +"&p="+page;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    receiveResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "No results", Toast.LENGTH_SHORT).show();

                }

            });
        queue.add(stringRequest);
    }

    void receiveResponse (String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String tit, ing, ima, w;
            for (int i=0; i<jsonObject.getJSONArray("results").length(); i++) {
                tit = jsonObject.getJSONArray("results").getJSONObject(i).getString("title");
                ing = jsonObject.getJSONArray("results").getJSONObject(i).getString("ingredients");
                ima = jsonObject.getJSONArray("results").getJSONObject(i).getString("thumbnail");
                w = jsonObject.getJSONArray("results").getJSONObject(i).getString("href");
                recipes.add(new Recipe(tit,ing,ima,w));
            }
            myListView.setAdapter(new RecipesAdapter(context, R.layout.list_item, recipes));
        }catch (Exception e){
            Toast.makeText(this, "Parse Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        {

            switch (view.getId()) {
                case R.id.listViewRecipes:

                    // Make your calculation stuff here. You have all your
                    // needed info from the parameters of this function.

                    // Sample calculation to determine if the last
                    // item is fully visible.
                    final int lastItem = firstVisibleItem + visibleItemCount;

                    if (lastItem == totalItemCount) {
                        if (preLast != lastItem) {
                            //to avoid multiple calls for last item
                            Log.d("Last", "Last");
                            i++;
                            makeRequest(search, String.valueOf(i));
                            Toast.makeText(context, getResources().getString(R.string.pswait), Toast.LENGTH_SHORT).show();
                            preLast = lastItem;
                        }
                    }
            }
        }
    }
}
