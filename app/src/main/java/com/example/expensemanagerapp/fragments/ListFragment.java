package com.example.expensemanagerapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.adapter.ExpenseAdapter;
import com.example.expensemanagerapp.model.Expense;
import com.example.expensemanagerapp.viewmodel.ExpenseViewModel;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private View view;
    private TextView totalTextView;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private ArrayList<Expense> expenseArrayList;
    private ExpenseViewModel viewModel;
    private int total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        initialization();
        expenseArrayList = viewModel.getExpenseList();
        total = viewModel.getTotal();
        if(expenseArrayList != null){
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
        requireActivity().setTitle("Expenses");
        recyclerView = view.findViewById(R.id.recycler_view);
        totalTextView = view.findViewById(R.id.total_text_view);
        viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
    }
}