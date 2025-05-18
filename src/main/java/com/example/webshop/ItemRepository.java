package com.example.webshop;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemRepository {
    private final ItemDao itemDao;
    private final LiveData<List<Item>> allItems;
    private final ExecutorService executorService;

    public ItemRepository(ItemDao itemDao) {
        this.itemDao = itemDao;
        allItems = itemDao.getAllItems();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void insert(Item item) {
        executorService.execute(() -> itemDao.insert(item));
    }

    public void update(Item item) {
        executorService.execute(() -> itemDao.update(item));
    }

    public void delete(Item item) {
        executorService.execute(() -> itemDao.delete(item));
    }

    public LiveData<Item> getItemById(int id) {
        return itemDao.getItemById(id);
    }
}
