package com.example.webshop;
import android.content.Intent;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class products extends AppCompatActivity {

    TextView titleText, subtitleText, descriptionText;
    ImageView productImage;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private Button webshopButton, loginButton;
    Button compareButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlayout);

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
                Intent intent = new Intent(products.this, products.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_login) {
                Intent intent = new Intent(products.this, login.class);
                startActivity(intent);
            }else if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(products.this, MainActivity.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawers();
            return true;
        });


        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
        descriptionText = findViewById(R.id.descriptionText);
        productImage = findViewById(R.id.productImage);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
