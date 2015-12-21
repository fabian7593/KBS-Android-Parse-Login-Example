package com.keybellsoft.parseexample;
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
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
