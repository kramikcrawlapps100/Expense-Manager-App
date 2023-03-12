package com.example.expensemanagerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.expensemanagerapp.adapter.ExpenseAdapter;
import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.model.Expense;

import java.util.ArrayList;

public class DatabaseChangeReceiver extends BroadcastReceiver {

    private ExpenseAdapter mAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<Expense> newExpense;

    public DatabaseChangeReceiver(ExpenseAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseHelper = new DatabaseHelper(context);
        newExpense = databaseHelper.getAllExpenses();
        mAdapter.updateData(newExpense);
        mAdapter.updateTextView(String.valueOf(databaseHelper.getTotal(newExpense)));
    }
}

