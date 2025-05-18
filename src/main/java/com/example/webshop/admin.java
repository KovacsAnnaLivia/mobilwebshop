package com.example.webshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class admin extends AppCompatActivity {

    private EditText editTextName, editTextPrice, editTextQuantity, editTextDescription, editTextImageUrl;
    private ItemViewModel itemViewModel;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlayout);


        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextImageUrl = findViewById(R.id.editTextImageUrl);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonViewAll = findViewById(R.id.buttonViewAll);
        homeButton = findViewById(R.id.homeButton);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);


        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            double price = Double.parseDouble(editTextPrice.getText().toString());
            int quantity = Integer.parseInt(editTextQuantity.getText().toString());
            String description = editTextDescription.getText().toString();
            String imageUrl = editTextImageUrl.getText().toString();

            Item item = new Item(name, price, quantity, description, imageUrl);
            itemViewModel.insert(item);
            Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
        });



        buttonViewAll.setOnClickListener(v -> {
            Intent intent = new Intent(admin.this, products.class);
            startActivity(intent);
        });


        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(admin.this, MainActivity.class);
            startActivity(intent);
        });
    }
}