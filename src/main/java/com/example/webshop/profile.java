package com.example.webshop;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    Spinner profileSpinner;
    TextView nameText, emailText, avatarText;
    Button homeButton;

    private FirebaseUser User;
    private  static final String Log_tag=profile.class.getName();
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        User = FirebaseAuth.getInstance().getCurrentUser();

        if(User != null){
            Log.d(LOG_TAG, "Hitelesített felhasználó");
        }else {
            Log.d(LOG_TAG, "Nincs hitelesített felhasználó");
            finish();
        }
        profileSpinner = findViewById(R.id.profileSpinner);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        avatarText = findViewById(R.id.avatarText);
        homeButton = findViewById(R.id.homeButton);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        profileSpinner.setAdapter(adapter);

        profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(profile.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


}
