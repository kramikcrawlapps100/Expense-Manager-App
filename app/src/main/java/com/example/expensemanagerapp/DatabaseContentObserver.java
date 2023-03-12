package com.example.expensemanagerapp;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.util.Log;

import com.example.expensemanagerapp.adapter.ExpenseAdapter;

public class DatabaseContentObserver extends ContentObserver {
    private final Context context;
    private ExpenseAdapter adapter;

    public DatabaseContentObserver(Context context, ExpenseAdapter adapter) {
        super(null);
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onChange(boolean selfChange) {

//        Log.d("kramik","database changes");
//        adapter.notifyDataSetChanged();
        Intent intent = new Intent("com.example.expensemanagerapp.DATABASE_CHANGED");
        context.sendBroadcast(intent);
    }
}
