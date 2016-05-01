package com.example.gabrielbl.androidstudyjam.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gabrielbl.androidstudyjam.Domain.CineserclaCrowler;
import com.example.gabrielbl.androidstudyjam.DataBase.DataBase;
import com.example.gabrielbl.androidstudyjam.Domain.MoviesRepository;
import com.example.gabrielbl.androidstudyjam.Models.Movie;
import com.example.gabrielbl.androidstudyjam.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Gabriel on 30/04/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "com.example.gabrielbl.androidstudyjam.Models.Movie";
    public static final String EXTRA_SEARCH_QUERY = "com.example.gabrielbl.androidstudyjam.SEARCH_QUERY";

    protected Toolbar myToolbar;
    protected DataBase dataBase;
    protected MoviesRepository moviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBase = new DataBase(this);
        moviesRepository = new MoviesRepository(dataBase.getWritableDatabase());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    public void refreshMoviesData() {
        new CineserclaCrowler(this).execute();
    }

    protected void setupTabs() {
        //getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // Faz nada
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Movie> movies = moviesRepository.getFilmes(query);

                Context context = getApplicationContext();
                if (movies.size() == 0) {
                    String msg = "No results";
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else if (movies.size() == 1) {
                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra(EXTRA_MOVIE, movies.get(0));
                    startActivity(intent);
                } else {
                    // Update listed movies
                    onSearchMultipleResults();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshMoviesData();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void onSearchMultipleResults();
}
