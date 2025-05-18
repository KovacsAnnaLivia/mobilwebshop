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
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

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




        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        } else {
            Toast.makeText(this, "Nincs bejelentkezett felhasználó!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}