package com.example.expensemanagerapp;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;

public class DatabaseContentObserver extends ContentObserver {
    private final Context context;

    public DatabaseContentObserver(Context context) {
        super(null);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        Intent intent = new Intent("com.example.myapp.DATABASE_CHANGED");
        context.sendBroadcast(intent);
    }
}
