package com.example.expensemanagerapp.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.expensemanagerapp.fragments.InsertFragment;
import com.example.expensemanagerapp.model.Expense;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Closeable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExpenseViewModel extends AndroidViewModel {

    private SharedPreferences sharedPreferences;
    private String expenseListString;
    private final Gson gson = new Gson();
    private ArrayList<Expense> expenseList = null;
    private int total;


    public ExpenseViewModel(Application application) {
        super(application);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public ArrayList<Expense> getExpenseList() {
        expenseListString = sharedPreferences.getString("expenseList", "");
        Type type = new TypeToken<ArrayList<Expense>>(){}.getType();
        expenseList = gson.fromJson(expenseListString, type);
        return expenseList;
    }

    public void setExpenseList(Expense expense) {
        expenseListString = sharedPreferences.getString("expenseList", "");
        if(expenseListString.equals("")){
            expenseList = new ArrayList<>();
            expenseList.add(expense);
        }
        else {
            expenseList = getExpenseList();
            expenseList.add(expense);
        }
        sharedPreferences.edit().putString("expenseList", gson.toJson(expenseList)).apply();
    }

    public int getTotal() {
        total = 0;
        expenseList = getExpenseList();
        if(expenseList == null){
            return 0;
        }
        for(Expense item : expenseList){
            total += item.getAmount();
        }
        return total;
    }
}
