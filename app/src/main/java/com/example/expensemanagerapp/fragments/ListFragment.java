package com.example.expensemanagerapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.adapter.ExpenseAdapter;
import com.example.expensemanagerapp.model.Expense;
import com.example.expensemanagerapp.viewmodel.ExpenseViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class ListFragment extends Fragment {

    TextView totalTextView;
    RecyclerView recyclerView;
    ExpenseAdapter adapter;
    ArrayList<Expense> expenseArrayList;
    SharedPreferences sharedPreferences;
    int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        requireActivity().setTitle("Expenses");
        recyclerView = view.findViewById(R.id.recycler_view);
        totalTextView = view.findViewById(R.id.total_text_view);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());


//        expenseArrayList = new ArrayList<>();
//        expenseArrayList.add(new Expense(100,"Item 1",true));
//        expenseArrayList.add(new Expense(200,"Item 2",true));
//        expenseArrayList.add(new Expense(500,"Item 3",true));
//        expenseArrayList.add(new Expense(200,"Item 4",false));
//        expenseArrayList.add(new Expense(30,"Item 5",true));
//        expenseArrayList.add(new Expense(50,"Item 6",false));
//        expenseArrayList.add(new Expense(1000,"Item 7",true));


        ExpenseViewModel viewModel = new ExpenseViewModel(sharedPreferences);
        expenseArrayList = viewModel.getExpenseList();

//        if(expenseArrayList.size() == 0) {
//            Toast.makeText(requireActivity(), "enter your first expense", Toast.LENGTH_SHORT).show();
//        }


        int total = 0;
        if(expenseArrayList != null){

            for(Expense item : expenseArrayList){
                total += item.getAmount();
            }

            totalTextView.setText(String.valueOf(total));

            adapter = new ExpenseAdapter(requireActivity(),expenseArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}