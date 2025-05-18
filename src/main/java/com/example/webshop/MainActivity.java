package com.example.webshop;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private Button webshopButton, loginButton, profilButton, itemUpgradeButton;

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
                startActivity(new Intent(MainActivity.this, products.class));
            } else if (item.getItemId() == R.id.nav_login) {
                startActivity(new Intent(MainActivity.this, login.class));
            } else if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });

        loginButton = findViewById(R.id.loginButton);
        profilButton = findViewById(R.id.profilButton);
        webshopButton = findViewById(R.id.webshopButton);
        itemUpgradeButton = findViewById(R.id.itemUpgradeButton);

        itemUpgradeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Itemupgrade.class);
            startActivity(intent);
        });
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale);
        webshopButton.setOnClickListener(v -> {
            v.startAnimation(scaleUp);
            Intent intent = new Intent(MainActivity.this, products.class);
            startActivity(intent);
        });

        updateLoginUI();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }


        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        );

        long triggerTime = System.currentTimeMillis() + 60 * 1000;

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);

    }

    private void updateLoginUI() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (isLoggedIn) {
            loginButton.setText("Kijelentkezés");
            loginButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putBoolean("isAdmin", false);
                editor.apply();
                Toast.makeText(MainActivity.this, "Sikeresen kijelentkeztél", Toast.LENGTH_SHORT).show();
                updateLoginUI();
            });

            profilButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);
            });


            itemUpgradeButton.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        } else {
            loginButton.setText("Bejelentkezés");
            loginButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            });

            profilButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            });

            itemUpgradeButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginUI();
    }



}
