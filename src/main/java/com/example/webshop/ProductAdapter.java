package com.example.webshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Item> itemList = new ArrayList<>();
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<Item> items) {
        this.itemList = items;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, quantity, description;
        Button editItemButton, addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.titleText);
            price = itemView.findViewById(R.id.priceText);
            quantity = itemView.findViewById(R.id.quantityText);
            description = itemView.findViewById(R.id.descriptionText);
            editItemButton = itemView.findViewById(R.id.editItemButton);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("Ár: " + item.getPrice());
        holder.quantity.setText("Mennyiség: " + item.getAmount());
        holder.description.setText(item.getDescription());

        String imageName = item.getImageUrl();
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if (imageResId != 0) {
            holder.imageView.setImageResource(imageResId);
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }


        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (isAdmin) {
            holder.editItemButton.setVisibility(View.VISIBLE);
            holder.editItemButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, Itemupgrade.class);
                intent.putExtra("itemId", item.getId());
                context.startActivity(intent);
            });
        } else {
            holder.editItemButton.setVisibility(View.GONE);
        }

        if (item.getAmount() == 1) {
            NotificationHelper.showNotification(
                    context,
                    "Utolsó darab!",
                    "A(z) " + item.getName() + " termékből már csak 1 db van!"
            );
        }
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

