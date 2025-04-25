package com.example.webshop;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private Button webshopButton, loginButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_webshop) {
                Intent intent = new Intent(MainActivity.this, products.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_login) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }else if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawers();
            return true;
        });

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, login.class);
                   startActivity(intent);
       });

        webshopButton = findViewById(R.id.webshopButton);
        webshopButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, products.class);
            startActivity(intent);
        });

    }
}
