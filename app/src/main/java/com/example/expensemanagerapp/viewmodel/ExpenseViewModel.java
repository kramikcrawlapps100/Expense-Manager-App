package com.example.expensemanagerapp.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.example.expensemanagerapp.model.Expense;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends ViewModel {

    private SharedPreferences sharedPreferences;
    Gson gson = new Gson();

    private ArrayList<Expense> expenseList = new ArrayList<>();

    public ExpenseViewModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public ArrayList<Expense> getExpenseList() {
        String expenseListString = sharedPreferences.getString("expenseList", "");
        Type type = new TypeToken<ArrayList<Expense>>(){}.getType();
        expenseList = gson.fromJson(expenseListString, type);
        return expenseList;
    }

    public void setExpenseList(Expense expense) {
        expenseList = getExpenseList();
        expenseList.add(expense);
    }
}
