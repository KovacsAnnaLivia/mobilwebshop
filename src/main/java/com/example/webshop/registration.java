package com.example.webshop;

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

public class registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    Button registerButton, loginButton;

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
        loginButton = findViewById(R.id.loginButton);

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

            // Itt hívhatod meg a Firebase regisztrációt vagy más logikát
            Toast.makeText(registration.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(registration.this, login.class);
            startActivity(intent);
            finish();
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

        // Itt hívhatod meg a Firebase regisztrációt vagy más logikát
        Toast.makeText(this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "Felhasználó létre lett hozva");
                    //profile();
                }else{
                    Log.d(LOG_TAG, "Felhasználó létre lett hozva");
                    Toast.makeText(registration.this, "Regisztráció sikertelen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void loginAsGuest(View view) {
//        Intent intent = new Intent(this, login.class);
//        startActivity(intent);
//        finish();
    }
}
