/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  Boolean signUpModeIsActive = true;
  TextView changeSignupTextView;
  EditText passwordEditText;


  @Override
  public void onClick(View v) { // see onClickListener below...
    if (v.getId() == R.id.changeSignupTextView) {

      Button signupButton = (Button) findViewById(R.id.signupButton);


      if (signUpModeIsActive == true) {
        signUpModeIsActive = false;
        signupButton.setText("Login");
        changeSignupTextView.setText("Sign Up");
      } else {
        signUpModeIsActive = true;
        signupButton.setText("Sign Up");
        changeSignupTextView.setText("Log In");
      }
    }
  }

  public void signUp (View view) {

    EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);


    if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

      Toast.makeText(this, "You need to enter a username/password.", Toast.LENGTH_SHORT).show();
    } else {

      if (signUpModeIsActive) {

        ParseUser user = new ParseUser();

        user.setPassword(passwordEditText.getText().toString());
        user.setUsername(usernameEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("SignUp ", "Was a success");
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                new LogInCallback() {
                  @Override
                  public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                      Log.i("The User ", " Is signed in");
                    } else {
                      Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                  }
                });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    changeSignupTextView = (TextView) findViewById(R.id.changeSignupTextView);
    changeSignupTextView.setOnClickListener(this);
    passwordEditText = (EditText) findViewById(R.id.passwordEditText);

    RelativeLayout mainBackground = (RelativeLayout) findViewById(R.id.mainBackground);

    changeSignupTextView.setOnClickListener(this);

    passwordEditText.setOnKeyListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


}