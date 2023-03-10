package com.example.expensemanagerapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensemanagerapp.model.Expense;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "expenses_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Expense.COLUMN_AMOUNT, expense.getAmount());
        values.put(Expense.COLUMN_NOTE, expense.getNote());
        values.put(Expense.COLUMN_TRANSACTIONTYPE, expense.getTransactionType());

        db.insert(Expense.TABLE_NAME, null, values);
//        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Expense.TABLE_NAME + " ORDER BY " +
                Expense.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

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

//        db.close();
        return expenses;
    }

    public void updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Expense.COLUMN_AMOUNT, expense.getAmount());
        values.put(Expense.COLUMN_NOTE, expense.getNote());
        values.put(Expense.COLUMN_TRANSACTIONTYPE, expense.getTransactionType());

        db.update(Expense.TABLE_NAME, values, Expense.COLUMN_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});

    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Expense.TABLE_NAME, Expense.COLUMN_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
    }

}
