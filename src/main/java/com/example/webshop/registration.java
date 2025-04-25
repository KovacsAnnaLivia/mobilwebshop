package com.example.webshop;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    Button registerButton, homeButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        emailEditText = findViewById(R.id.emailEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(registration.this, MainActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String fname = firstNameEditText.getText().toString();
            String lname = lastNameEditText.getText().toString();
            String pass = passwordEditText.getText().toString();
            String confirmPass = confirmPasswordEditText.getText().toString();

            if (email.isEmpty() || fname.isEmpty() || lname.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(registration.this, "Minden mezőt tölts ki!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirmPass)) {
                Toast.makeText(registration.this, "A jelszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    String userId = mAuth.getCurrentUser().getUid();
                    User user = new User(fname, lname, email);

                        Log.d("Registration", "Navigating to login page");
                        Toast.makeText(registration.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registration.this, login.class);
                        startActivity(intent);

                } else {
                    Toast.makeText(registration.this, "Regisztráció sikertelen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });


        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String email = emailEditText.getText().toString();
        String Vname = firstNameEditText.getText().toString();
        String Kname = lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        String pass;
        if (email.isEmpty() || Vname.isEmpty() || Kname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Minden mezőt tölts ki!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "A jelszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Felhasználó létre lett hozva");
                    Toast.makeText(registration.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registration.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d(LOG_TAG, "Regisztráció sikertelen");
                    Toast.makeText(registration.this, "Regisztráció sikertelen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    class User {
        public String firstName;
        public String lastName;
        public String email;

        public User(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }
}
