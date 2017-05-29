package edu.uci.ics.fabflixmobile.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.fabflixmobile.R;

/**
 * Created by huyanh on 2017. 5. 16..
 */

public final class Search extends AppCompatActivity {

    private EditText mSearchBox;
    private Button mButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mSearchBox = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);

        mSearchBox.requestFocus();

        mButton.setOnClickListener(new View.OnClickListener() {
            boolean valid = true;

            @Override
            public void onClick(View view) {
                final String title = mSearchBox.getText().toString();

                if(title.isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
                    builder.setMessage("Title cannot be blank").setNegativeButton("Retry", null).create().show();
                    mSearchBox.requestFocus();
                    valid = false;
                }

                if(valid)
                {
                    doSearch(title);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_red, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doSearch(String text) {

        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String url = "http://13.58.77.131:8080/servlet/AjaxSearch?movieTitle=" + text;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        String [] titles = getTitles(response);
                        if (response.equals("")) {
                            AlertDialog.Builder Alert = new AlertDialog.Builder(Search.this);
                            Alert.setMessage("No Results found");
                            Alert.setPositiveButton("OK", null);
                            Alert.create().show();
                        } else {
                            Intent i = new Intent(Search.this, Titles.class);
                            i.putExtra("titles", titles);
                            startActivity(i);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("security.error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(postRequest);
    }

    public String[] getTitles(String string){
        String[] splitTitle = new String[0];

        try {
            // Load data
            splitTitle = string.split("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return splitTitle;
    }

}
