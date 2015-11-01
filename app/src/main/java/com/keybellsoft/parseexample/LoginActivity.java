package com.keybellsoft.parseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import General.ConnectParseClass;
import General.StaticsEntities;

/**
 *  Copyright (C) 2015 KeyBellSoft. All rights reserved.
 *  Created by Fabian Rosales  on 25/10/2015.
 *  www.frosquivel.com
 *
 *
 *  Use for example
 *  https://github.com/rey5137/material
 *  www.parse.com
 */
public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{
    private String tableUser = StaticsEntities.USER;
    private EditText editTextViewUsername;
    private EditText editTextViewPassword;
    private Button buttonViewLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
        try {
            connectionUser();
        }catch (Exception io)
        {}
        ParseUser.logOut();
        // PushService.setDefaultPushCallback(this,LoginActivity.class);
    }

    private void connectionUser() {
        ConnectParseClass connect = new ConnectParseClass();
        connect.onCreateObject(tableUser);
        connect.onInitialParse(this);
    }

    private void setView() {
        editTextViewUsername = (EditText) findViewById(R.id.userLogin);
        editTextViewPassword = (EditText) findViewById(R.id.passwordLogin);
        buttonViewLogin = (Button) findViewById(R.id.buttonLogin);
        buttonViewLogin.setOnClickListener(this);


        buttonViewLogin.setOnTouchListener(new View.OnTouchListener() {
            long thenMilisecond = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    thenMilisecond = (Long) System.currentTimeMillis();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (((Long) System.currentTimeMillis() - thenMilisecond) > 5000) {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });
    }


    private void callLogin() {
        ParseUser.logInInBackground(editTextViewUsername.getText().toString(), editTextViewPassword.getText()
                .toString(), new LogInCallback() {

            public void done(ParseUser user, ParseException e) {
                if (user != null) {

                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(tableUser);
                    query.whereEqualTo(StaticsEntities.USER_USERNAME, editTextViewUsername.getText().toString());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> usersList, ParseException e) {
                            if (e == null) {
                                for (int x = 0; x < usersList.size(); x++) {
                                    ParseObject objectUser = usersList.get(x);
                                    Object isActive = objectUser.get(StaticsEntities.USER_ISACTIVE);
                                    if (isActive != null) {
                                        if ((boolean) isActive) {
                                            Toast.makeText(LoginActivity.this, "Listo entro!!! ", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(LoginActivity.this, R.string.userNotActive, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.errorConnect, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, R.string.userNotExist, Toast.LENGTH_LONG).show();
                }
            }
        });
    }





    @Override
    public void onClick(View v) {

        if (v == buttonViewLogin) {
            callLogin();
        }
    }

}
