package com.example.expensemanagerapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.model.Expense;

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.expensemanagerapp.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Expense.TABLE_NAME);
    public static final String ACTION_DATA_CHANGED = "com.example.expensemanagerapp.ACTION_DATA_CHANGED";
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = db.query(Expense.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db.insert(Expense.TABLE_NAME, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(newUri, null);
        getContext().sendBroadcast(new Intent(ACTION_DATA_CHANGED));
        return newUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = db.update(Expense.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        getContext().sendBroadcast(new Intent(ACTION_DATA_CHANGED));
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(Expense.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        getContext().sendBroadcast(new Intent(ACTION_DATA_CHANGED));
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.example.expensemanagerapp.expenses";
    }
}

