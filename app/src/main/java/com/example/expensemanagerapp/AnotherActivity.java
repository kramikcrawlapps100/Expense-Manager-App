package com.example.expensemanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.expensemanagerapp.model.Expense;
import com.example.expensemanagerapp.viewmodel.ExpenseViewModel;

import java.util.ArrayList;

public class AnotherActivity extends AppCompatActivity {

    private ExpenseViewModel viewModel;
    private ArrayList<Expense> expenseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseArrayList = viewModel.getExpenseList();

        for (Expense expense : expenseArrayList) {
            Log.d("kramik", "Expense: " + expense.getAmount() + " " + expense.getNote());
        }
    }
}