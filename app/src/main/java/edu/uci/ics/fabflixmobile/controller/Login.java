package edu.uci.ics.fabflixmobile.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uci.ics.fabflixmobile.R;

public final class Login extends AppCompatActivity
{
    private static final String TAG = "Login";
    private static SharedPreferences ps;
    private static SharedPreferences.Editor pe;
    private static String Username;
    private static String Password;
    String mChildCode;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        ps = getPreferences(0);
        pe = ps.edit();

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);

        String spUsername = ps.getString("Username","");
        String spPassword = ps.getString("password", "");

        //auto-login
//        if (spUsername.length() != 0 || spPassword.length() != 0)
//        {
//            etUsername.setText(spUsername);
//            etPassword.setText(spPassword);
//            //create an intent to store Username information for UserActivity
//            Intent userIntent = new Intent(LoginActivity.this, HomeParentActivity.class);
//            userIntent.putExtra("Username", spUsername);
//            Username = spUsername;
//            userIntent.putExtra("password", spPassword);
//            Password = spPassword;
//            //start activity to UserActivity.class
//            LoginActivity.this.startActivity(userIntent);
//        }


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
                        signIn(username, password);
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

    private void signIn(String email, final String password) {
        Log.d(TAG, "signIn:" + email);
        final String tempEmail = email;
        final String tempPassword = password;

        // [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            AlertDialog.Builder Alert = new AlertDialog.Builder(Login.this);
//                            Alert.setMessage("Incorrect Username or Password");
//                            Alert.setPositiveButton("OK", null);
//                            Alert.create().show();
//                        } else {
////                            create an intent to store Username information for UserActivity
//                            Intent userIntent = new Intent(Login.this, Search.class);
//                            userIntent.putExtra("Username", tempEmail);
//                            userIntent.putExtra("password", tempPassword);
//                            Username = tempEmail;
//                            Password = tempPassword;
//                            startActivity(userIntent);
//                        }
//                    }
//                });
    }


    public static void clearUsername()
    {
        pe.clear();
        pe.commit();
        // sign out
    }
}
