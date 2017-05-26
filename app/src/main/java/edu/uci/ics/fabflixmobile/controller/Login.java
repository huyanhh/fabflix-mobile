package edu.uci.ics.fabflixmobile.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.fabflixmobile.R;

public final class Login extends AppCompatActivity
{
    private static final String TAG = "Login";
    private static SharedPreferences ps;
    private static SharedPreferences.Editor pe;
    private static String Username;
    private static String Password;
    private int mStatusCode = 0;

    Button bLogin;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        ps = getPreferences(0);
        pe = ps.edit();

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin = (Button) findViewById(R.id.bLogin);

        String spUsername = ps.getString("Username","");
        String spPassword = ps.getString("password", "");

        //LoginActivity process
        if (bLogin != null) {
            //when Login button is clicked, store username and password to a String
            bLogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final String username = etUsername.getText().toString(); // should be email
                    final String password = etPassword.getText().toString();
                    if(!username.isEmpty() || !password.isEmpty()) {
                        login(username, password);
                    }
                    //if username||password is empty display this
                    else
                    {
                        AlertDialog.Builder Alert = new AlertDialog.Builder(Login.this);
                        Alert.setMessage("Username or Password cannot be blank");
                        Alert.setPositiveButton("OK", null);
                        Alert.create().show();
                    }

                }
            });
        }
    }


    public void login(final String email, final String password){

        final String tempEmail = email;
        final String tempPassword = password;

        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
//        String url = "http://54.183.252.246:8080/servlet/Login";
        String url = "http://192.168.1.157:8080/servlet/Login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        if (response.contains("success")) {
                            Intent userIntent = new Intent(Login.this, Search.class);
                            userIntent.putExtra("Username", tempEmail);
                            userIntent.putExtra("password", tempPassword);
                            Username = tempEmail;
                            Password = tempPassword;
                            startActivity(userIntent);
                        }
                        else {
                            AlertDialog.Builder Alert = new AlertDialog.Builder(Login.this);
                            Alert.setMessage("Incorrect Username or Password");
                            Alert.setPositiveButton("OK", null);
                            Alert.create().show();
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
                        params.put("email", email);
                        params.put("password", password);
                        Log.d("params", params.get("email"));
                        Log.d("params", params.get("password"));
                        return params;
                    }
        };

        // Add the request to the RequestQueue.
        queue.add(postRequest);
    }

    public static void clearUsername()
    {
        pe.clear();
        pe.commit();
        // sign out
    }
}
