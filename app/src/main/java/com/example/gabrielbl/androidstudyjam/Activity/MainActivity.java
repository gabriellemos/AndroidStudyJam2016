package com.example.gabrielbl.androidstudyjam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.gabrielbl.androidstudyjam.Models.Movie;
import com.example.gabrielbl.androidstudyjam.R;

import java.util.List;

public class MainActivity extends BaseActivity {

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshMoviesData();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setupTabs();

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(BaseActivity.EXTRA_SEARCH_QUERY);

        if (searchQuery == null) {
            movies = moviesRepository.getFilmes();
        } else {
            movies = moviesRepository.getFilmes(searchQuery);
        }
        System.out.println(movies);
    }

    @Override
    protected void onSearchMultipleResults() {
        System.out.println("onSearchMultipleResults");
    }

}
