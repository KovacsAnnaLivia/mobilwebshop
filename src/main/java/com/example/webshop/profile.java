package com.example.webshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView nameText, emailText;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    Button  homeButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ImageView profileImage = findViewById(R.id.profileImage);
        Animation flipAnimation = AnimationUtils.loadAnimation(this, R.anim.spin);
        profileImage.setOnClickListener(v -> profileImage.startAnimation(flipAnimation));

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(profile.this, MainActivity.class);
            startActivity(intent);
        });
        //emailText = findViewById(R.id.emailText);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String firstName = snapshot.child("firstName").getValue(String.class);
                        String lastName = snapshot.child("lastName").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);

                        Log.d("profile", "Retrieved data: firstName=" + firstName + ", lastName=" + lastName + ", email=" + email);

                        if (firstName != null && lastName != null && email != null) {
                            nameText.setText(firstName + " " + lastName); // Uncommented to display name
                            emailText.setText(email);
                        } else {
                            Toast.makeText(profile.this, "Hiányzó adatok a profilban!", Toast.LENGTH_SHORT).show();
                            Log.e("profile", "Missing fields: firstName, lastName, or email is null.");
                        }
                    } else {
                        Toast.makeText(profile.this, "Nincsenek elérhető adatok!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(profile.this, "Hiba az adatok lekérésekor!", Toast.LENGTH_SHORT).show();
                    Log.e("profile", "Database error: " + error.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Nincs bejelentkezett felhasználó!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}