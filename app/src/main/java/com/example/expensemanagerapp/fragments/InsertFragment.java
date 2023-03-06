package com.example.expensemanagerapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensemanagerapp.MainActivity;
import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.model.Expense;
import com.example.expensemanagerapp.viewmodel.ExpenseViewModel;

public class InsertFragment extends Fragment {

    View view;
    private EditText amountEditText,noteEditText;
    private RadioButton debitButton,creditButton;
    private Button saveButton;
    private ExpenseViewModel viewModel;

    private int amount = 0;
    private String note = "";
    private boolean transactionType = false;

    private Activity a;
    private int total;

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
        view = inflater.inflate(R.layout.fragment_insert, container, false);
        initialization();
        total = viewModel.getTotal();

        saveButton.setOnClickListener(v->{
            if(!validation(amountEditText, noteEditText)){
                return;
            }

            amount = Integer.parseInt(amountEditText.getText().toString());
            note = noteEditText.getText().toString();
            transactionType = debitButton.isChecked();

            if(transactionType){
                amount = -amount;
            }
            if((total+amount) < 0) {
                Toast.makeText(a, "First Credit Some Amount", Toast.LENGTH_SHORT).show();
                return;
            }

            insertExpense(amount,note,transactionType);
            clearFields();
            ((MainActivity)a).navigateListFragment();
        });
        return view;
    }

    private boolean validation(EditText amountEditText, EditText noteEditText) {
        if(amountEditText.getText().toString().equals("")){
            amountEditText.setError("Amount cannot be empty");
            return false;
        }
        if(noteEditText.getText().toString().equals("")){
            noteEditText.setError("Note cannot be empty");
            return false;
        }
        return true;
    }

    private void clearFields() {
        amountEditText.setText("");
        noteEditText.setText("");
    }

    public boolean validateEditTexts(EditText[] editTexts) {
        boolean allValid = true;
        for (EditText editText : editTexts) {
            String text = editText.getText().toString();
            if (text.isEmpty()) {
                editText.setError("This field cannot be empty");
                allValid = false;
            }
            // Add additional validation checks here
        }
        return allValid;
    }

    private void initialization() {
        requireActivity().setTitle("Add");
        amountEditText = view.findViewById(R.id.amountEditText);
        noteEditText = view.findViewById(R.id.noteEditText);
        debitButton = view.findViewById(R.id.debitButton);
        creditButton = view.findViewById(R.id.creditButton);
        saveButton = view.findViewById(R.id.saveButton);
        viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
    }

    private void insertExpense(int amount, String note, boolean transactionType) {
        Expense expense = new Expense(amount,note,transactionType);
        viewModel.setExpenseList(expense);
    }
}