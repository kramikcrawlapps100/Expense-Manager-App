package com.example.expensemanagerapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import com.example.expensemanagerapp.MyContentProvider;
import com.example.expensemanagerapp.model.Expense;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "expenses_db";

    private Context mContext;

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
        ContentValues values = new ContentValues();
        values.put(Expense.COLUMN_AMOUNT, expense.getAmount());
        values.put(Expense.COLUMN_NOTE, expense.getNote());
        values.put(Expense.COLUMN_TRANSACTIONTYPE, expense.getTransactionType());
        String selection = Expense.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(expense.getId())};

        mContext.getContentResolver().update(MyContentProvider.CONTENT_URI,values,selection,selectionArgs);
    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Expense.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(expense.getId())};

        mContext.getContentResolver().delete(MyContentProvider.CONTENT_URI,selection,selectionArgs);
    }
    public int getTotal(ArrayList<Expense> expenses){
        int total = 0;
        for(Expense expense : expenses){
            total += expense.getAmount();
        }
        return total;
    }

}
