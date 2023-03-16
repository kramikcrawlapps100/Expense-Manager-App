package com.example.expensemanagerapp.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanagerapp.MyContentProvider;
import com.example.expensemanagerapp.adapter.ExpenseAdapter;
import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.databinding.FragmentListBinding;
import com.example.expensemanagerapp.model.Expense;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private ExpenseAdapter adapter;
    private ArrayList<Expense> expenseArrayList;
    private DatabaseHelper databaseHelper;
    private Activity activity;
    FragmentListBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(getLayoutInflater(),container,false);
        initialization();
        expenseArrayList = databaseHelper.getAllExpenses();
        if(expenseArrayList != null){
            binding.totalTextView.setText(String.valueOf(databaseHelper.getTotal(expenseArrayList)));
            setRecyclerView();
        }
        return binding.getRoot();
    }

    private void setRecyclerView() {
        adapter = new ExpenseAdapter(requireActivity(),expenseArrayList, binding.totalTextView);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setAdapter(adapter);
        requireContext().registerReceiver(receiver, new IntentFilter(MyContentProvider.ACTION_DATA_CHANGED));
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            databaseHelper = new DatabaseHelper(context);
            ArrayList<Expense> newExpense = databaseHelper.getAllExpenses();
            adapter.updateData(newExpense);
            adapter.updateTextView(String.valueOf(databaseHelper.getTotal(newExpense)));
        }
    } ;

    private void initialization() {
        databaseHelper = new DatabaseHelper(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireContext().unregisterReceiver(receiver);
    }
}