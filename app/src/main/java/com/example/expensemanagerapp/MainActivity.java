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

    ActivityMainBinding binding;
    ListFragment listFragment;
    InsertFragment insertFragment;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listFragment = new ListFragment();
        insertFragment = new InsertFragment();

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.list_open);
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

    public void performStreamClick(){
        binding.bottomNavigationView.setSelectedItemId(R.id.list_open);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
    }
}