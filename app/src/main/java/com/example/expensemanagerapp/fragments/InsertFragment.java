package com.example.expensemanagerapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.expensemanagerapp.MainActivity;
import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.model.Expense;
import com.example.expensemanagerapp.viewmodel.ExpenseViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InsertFragment extends Fragment {

    EditText amountEditText,noteEditText;
    RadioButton debitButton,creditButton;
    Button saveButton;
    SharedPreferences sharedPreferences;

    int amount = 0;
    String note = "";
    boolean transactionType = false;
    int total;

    Activity a;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            a = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, container, false);
        requireActivity().setTitle("Add");
        amountEditText = view.findViewById(R.id.amountEditText);
        noteEditText = view.findViewById(R.id.noteEditText);
        debitButton = view.findViewById(R.id.debitButton);
        creditButton = view.findViewById(R.id.creditButton);
        saveButton = view.findViewById(R.id.saveButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        saveButton.setOnClickListener(v->{

            if(amountEditText.getText().toString().equals("")){
                amountEditText.setError("Amount cannot be empty");
                return;
            }
            if(noteEditText.getText().toString().equals("")){
                noteEditText.setError("Note cannot be empty");
                return;
            }
            amount = Integer.parseInt(amountEditText.getText().toString());
            note = noteEditText.getText().toString();
            transactionType = debitButton.isChecked();
            if(transactionType){
                amount = -amount;
            }

            insertExpense(amount,note,transactionType);
            amountEditText.setText("");
            noteEditText.setText("");

            Toast.makeText(requireActivity(), "expense inserted.", Toast.LENGTH_SHORT).show();
            ((MainActivity)a).performStreamClick();

        });
        return view;
    }

    private void insertExpense(int amount, String note, boolean transactionType) {
        Expense expense = new Expense(amount,note,transactionType);

        Gson gson = new Gson();
        if(sharedPreferences.getString("expenseList", "").equals("")){
            ArrayList<Expense> expenseList = new ArrayList<>();
            expenseList.add(expense);
            String expenseListString = gson.toJson(expenseList);
            sharedPreferences.edit().putString("expenseList", expenseListString).apply();
        }
        else{
            ExpenseViewModel viewModel = new ExpenseViewModel(sharedPreferences);
            ArrayList<Expense> expenseArrayList = viewModel.getExpenseList();
            expenseArrayList.add(expense);
            sharedPreferences.edit().putString("expenseList", gson.toJson(expenseArrayList)).apply();
            viewModel.setExpenseList(expense);
        }

    }
}