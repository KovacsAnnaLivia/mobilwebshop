package com.example.webshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private Button webshopButton, ideasButton, loginButton;

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
                Toast.makeText(this, "Webshop menüpont kiválasztva", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_ideas) {
                Toast.makeText(this, "Ötletek menüpont kiválasztva", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_login) {
                Toast.makeText(this, "Bejelentkezés menüpont kiválasztva", Toast.LENGTH_SHORT).show();
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
