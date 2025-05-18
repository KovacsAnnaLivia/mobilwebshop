package com.example.webshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class Itemupgrade extends AppCompatActivity {
    private ItemViewModel itemViewModel;
    private EditText nameEditText, priceEditText, quantityEditText, descriptionEditText, imageUrlEditText;
    private Button updateButton, deleteButton, homeButton;
    private int itemId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemupgrade);

        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        imageUrlEditText = findViewById(R.id.imageUrlEditText);
        homeButton = findViewById(R.id.homeButton);

        itemId = getIntent().getIntExtra("itemId", -1);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        itemViewModel.getItemById(itemId).observe(this, item -> {
            if (item != null) {
                nameEditText.setText(item.getName());
                priceEditText.setText(String.valueOf(item.getPrice()));
                quantityEditText.setText(String.valueOf(item.getAmount()));
                descriptionEditText.setText(item.getDescription());
                imageUrlEditText.setText(item.getImageUrl());
            }
        });

        updateButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            double price = Double.parseDouble(priceEditText.getText().toString());
            int quantity = Integer.parseInt(quantityEditText.getText().toString());
            String description = descriptionEditText.getText().toString();
            String imageUrl = imageUrlEditText.getText().toString();
            Item updatedItem = new Item(name, price, quantity, description, imageUrl);
            updatedItem.setId(itemId);
            itemViewModel.update(updatedItem);

            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            itemViewModel.getItemById(itemId).observe(this, item -> {
                if (item != null) {
                    itemViewModel.delete(item);
                    Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });


    }
}