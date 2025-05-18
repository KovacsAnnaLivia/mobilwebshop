package com.example.webshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private final ItemRepository repository;
    private final LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(application);
        repository = new ItemRepository(database.itemDao());
        allItems = repository.getAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public LiveData<Item> getItemById(int id) {
        return repository.getItemById(id);
    }
}
