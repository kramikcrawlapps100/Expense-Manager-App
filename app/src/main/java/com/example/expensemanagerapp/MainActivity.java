package com.example.expensemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.expensemanagerapp.databinding.ActivityMainBinding;
import com.example.expensemanagerapp.fragments.InsertFragment;
import com.example.expensemanagerapp.fragments.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private ListFragment listFragment;
    private InsertFragment insertFragment;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        setDefaultNavigation();
    }

    private void setDefaultNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigateListFragment();
    }

    private void initialization() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        listFragment = new ListFragment();
        insertFragment = new InsertFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.list_open:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();
                return true;

            case R.id.insert_open:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, insertFragment).commit();
                return true;
        }
        return false;
    }

    public void navigateListFragment(){
        binding.bottomNavigationView.setSelectedItemId(R.id.list_open);
    }
}