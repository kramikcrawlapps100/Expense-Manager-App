package com.example.expensemanagerapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensemanagerapp.MyContentProvider;
import com.example.expensemanagerapp.model.Expense;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expenses_db";
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Expense.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Expense.TABLE_NAME);
        onCreate(db);
    }

    public void insertExpense(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(Expense.COLUMN_AMOUNT, expense.getAmount());
        values.put(Expense.COLUMN_NOTE, expense.getNote());
        values.put(Expense.COLUMN_TRANSACTIONTYPE, expense.getTransactionType());
        mContext.getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
    }

    @SuppressLint("Range")
    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        String[] projection = {
                Expense.COLUMN_ID,
                Expense.COLUMN_AMOUNT,
                Expense.COLUMN_NOTE,
                Expense.COLUMN_TRANSACTIONTYPE
        };
        String sortOrder = Expense.COLUMN_ID + " ASC";
        Cursor cursor = mContext.getContentResolver().query(MyContentProvider.CONTENT_URI,projection,null,null,sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(cursor.getInt(cursor.getColumnIndex(Expense.COLUMN_ID)));
                expense.setAmount(cursor.getInt(cursor.getColumnIndex(Expense.COLUMN_AMOUNT)));
                expense.setNote(cursor.getString(cursor.getColumnIndex(Expense.COLUMN_NOTE)));
                expense.setTransactionType(cursor.getInt(cursor.getColumnIndex(Expense.COLUMN_TRANSACTIONTYPE)));

                expenses.add(expense);
            } while (cursor.moveToNext());
        }
        return expenses;
    }

    public void updateExpense(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(Expense.COLUMN_AMOUNT, expense.getAmount());
        values.put(Expense.COLUMN_NOTE, expense.getNote());
        values.put(Expense.COLUMN_TRANSACTIONTYPE, expense.getTransactionType());
        String selection = Expense.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(expense.getId())};
        mContext.getContentResolver().update(MyContentProvider.CONTENT_URI,values,selection,selectionArgs);
    }

    public void deleteExpense(Expense expense) {
        String selection = Expense.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(expense.getId())};
        mContext.getContentResolver().delete(MyContentProvider.CONTENT_URI,selection,selectionArgs);
    }
    public int getTotal(ArrayList<Expense> expenses){
        int total = 0;
        for(Expense expense : expenses){
            if(expense.getTransactionType() == 1){
                total -= expense.getAmount();
            }
            else {
                total += expense.getAmount();
            }
        }
        return total;
    }
}
