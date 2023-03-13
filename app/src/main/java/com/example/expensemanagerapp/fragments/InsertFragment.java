package com.example.expensemanagerapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.databinding.FragmentInsertBinding;
import com.example.expensemanagerapp.model.Expense;

public class InsertFragment extends Fragment {
    private Activity activity;
    private DatabaseHelper databaseHelper;
    private FragmentInsertBinding binding;

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
        binding = FragmentInsertBinding.inflate(getLayoutInflater(),container,false);
        initialization();
        binding.saveButton.setOnClickListener(v->{
            if(!validation(binding.amountEditText, binding.noteEditText)){
                return;
            }
            insertExpense(Integer.parseInt(binding.amountEditText.getText().toString()),binding.noteEditText.getText().toString(),binding.debitButton.isChecked() ? 1 : 0);
            clearFields();
        });
        return binding.getRoot();
    }

    private boolean validation(EditText amountEditText, EditText noteEditText) {
        if(amountEditText.getText().toString().equals("")){
            amountEditText.setError(getString(R.string.amount_cannot_be_empty));
            return false;
        }
        if(noteEditText.getText().toString().equals("")){
            noteEditText.setError(getString(R.string.note_cannot_be_empty));
            return false;
        }
        return true;
    }

    private void clearFields() {
        binding.amountEditText.setText("");
        binding.noteEditText.setText("");
    }

    private void initialization() {
        databaseHelper = new DatabaseHelper(activity);
    }

    private void insertExpense(int amount, String note, int transactionType) {
        Expense expense = new Expense(amount, note, transactionType);
        databaseHelper.insertExpense(expense);
    }

}