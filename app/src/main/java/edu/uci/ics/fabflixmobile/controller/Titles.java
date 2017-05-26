package edu.uci.ics.fabflixmobile.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import edu.uci.ics.fabflixmobile.R;

/**
 * Created by huyanh on 2017. 5. 24..
 */

public final class Titles extends AppCompatActivity {
    private ListView mListView;

    int MAX_ITEMS = 7;
    private String[] movies;
    private ArrayList<String> currentMovies = new ArrayList<>();
    private int currentIndex = 0;
    ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titles);

        mListView = (ListView) findViewById(R.id.titles);

        movies = getIntent().getStringArrayExtra("titles");

        for (int i = 0; i < MAX_ITEMS && i < movies.length; i++)
            currentMovies.add(movies[i]);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                currentMovies);
        mListView.setAdapter(adapter);

        final Button mPrevious = (Button) findViewById(R.id.button);
        final Button mNext = (Button) findViewById(R.id.button2);

        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex - 7 >= 0){
                    currentIndex -= 7;
                    setMovies();
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex + 7 < movies.length) {
                    currentIndex += 7;
                    setMovies();
                }
            }
        });
    }

//    private void setMovies() {
//        currentMovies.clear();
//        int j = currentIndex;
//        for (int i = 0; i < MAX_ITEMS && i < movies.length; i++, j++)
//            currentMovies.add(movies[j]);
//    }

    private void setMovies() {
        adapter.clear();
        int j = currentIndex;
        for (int i = 0; i < MAX_ITEMS && j < movies.length; i++, j++)
            adapter.add(movies[j]);
    }
}
