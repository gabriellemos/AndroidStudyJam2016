package com.example.gabrielbl.androidstudyjam.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabrielbl.androidstudyjam.Models.Movie;
import com.example.gabrielbl.androidstudyjam.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MovieActivity extends BaseActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setupTabs();

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(MainActivity.EXTRA_MOVIE);

        setMoviesInformation();
    }

    private void setMoviesInformation() {
        ImageView poster = (ImageView) findViewById(R.id.moviePoster);

        ImageLoader.getInstance().displayImage(movie.getImgURL(), poster, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                System.out.println("Finished loading: " + imageUri);
            }
        });

        // Main info
        TextView title = (TextView) findViewById(R.id.movieTitle);
        title.setText(movie.getTitulo());

        TextView premier = (TextView) findViewById(R.id.moviePremiere);
        premier.setText(movie.getDataLancamento() == "" ? "---------" : movie.getDataLancamento());

        TextView length = (TextView) findViewById(R.id.movieLength);
        length.setText(movie.getDuracao() == "" ? "---------" : movie.getDuracao());

        TextView rating = (TextView) findViewById(R.id.movieRating);
        rating.setText(movie.getClassificacao());

        TextView genre = (TextView) findViewById(R.id.movieGenre);
        genre.setText(movie.getGenero());

        // Summary
        TextView summary = (TextView) findViewById(R.id.movieSummary);
        summary.setText(movie.getDescricao());

        // Other info
        TextView direction = (TextView) findViewById(R.id.movieDirection);
        direction.setText(movie.getDirecao());

        TextView cast = (TextView) findViewById(R.id.movieCast);
        cast.setText(movie.getElenco());

        ImageButton button = (ImageButton) findViewById(R.id.movieTrailer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerURL())));
            }
        });
    }

    @Override
    protected void onSearchMultipleResults() {
        System.out.println("onSearchMultipleResults");
    }
}
