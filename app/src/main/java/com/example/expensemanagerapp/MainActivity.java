package com.example.expensemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.expensemanagerapp.databinding.ActivityMainBinding;
import com.example.expensemanagerapp.fragments.InsertFragment;
import com.example.expensemanagerapp.fragments.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListFragment listFragment;
    private InsertFragment insertFragment;
    private SharedPreferences sharedPreferences;

    private DatabaseContentObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        observer = new DatabaseContentObserver(this);
        getContentResolver().registerContentObserver(MyContentProvider.URI, true, observer);
    }


    private void initialization() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.show_expenses_fragment, listFragment).commit();
        insertFragment = new InsertFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.add_expenses_fragment, insertFragment).commit();
    }

}