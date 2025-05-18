package com.example.webshop;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    public boolean admin = false;
    EditText emailEditText, passwordEditText;
    Button loginButton, registerButton, homeButton;
    private FirebaseAuth mAuth;

    public boolean isAdmin;
    public boolean isLoggedIn;
    private static final String ADMIN_EMAIL = "admin@gmail.com";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, registration.class);
            startActivity(intent);
        });

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Kérlek, tölts ki minden mezőt!", Toast.LENGTH_SHORT).show();
            } else {
                login(v);
            }
        });


    }

    public void login(View view){
        String userName = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putBoolean("isAdmin", userName.equals(ADMIN_EMAIL));
                    editor.apply();

                    if (userName.equals(ADMIN_EMAIL)) {

                        Intent intent = new Intent(login.this, admin.class);
                        intent.putExtra("isAdmin", true);
                        startActivity(intent);
                        finish();
                    } else {

                        Intent intent = new Intent(login.this, profile.class);
                        startActivity(intent);
                        intent.putExtra("isAdmin", false);
                        finish();
                    }
                } else {
                    Log.d(LOG_TAG, "Felhasználó nem lett jól létrehozva");
                    Toast.makeText(login.this, "Hibás jelszó vagy felhasználónév. ", Toast.LENGTH_LONG).show();
                }


            }

        });
    }
}
