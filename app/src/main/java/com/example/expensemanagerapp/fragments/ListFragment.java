package com.example.expensemanagerapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.adapter.ExpenseAdapter;
import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.model.Expense;
import com.example.expensemanagerapp.viewmodel.ExpenseViewModel;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private View view;
    private TextView totalTextView;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private ArrayList<Expense> expenseArrayList;
    private int total = 0;
    private DatabaseHelper databaseHelper;
    private Activity a;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            a = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        initialization();
        expenseArrayList = databaseHelper.getAllExpenses();



        if(expenseArrayList != null){
            for(Expense expense : expenseArrayList){
                total += expense.getAmount();
            }
            totalTextView.setText(String.valueOf(total));
            setRecyclerView();
        }
        return view;
    }

    private void setRecyclerView() {
        adapter = new ExpenseAdapter(requireActivity(),expenseArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void initialization() {
        recyclerView = view.findViewById(R.id.recycler_view);
        totalTextView = view.findViewById(R.id.total_text_view);
        databaseHelper = new DatabaseHelper(a);
    }
}