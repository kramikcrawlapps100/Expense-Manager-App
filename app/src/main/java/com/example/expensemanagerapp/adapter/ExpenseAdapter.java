package com.example.expensemanagerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.model.Expense;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{

    private final Context context;
    private ArrayList<Expense> expenseArrayList;
    private final DatabaseHelper databaseHelper;
    private final TextView totalTextView;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseArrayList,TextView totalTextView) {
        this.context = context;
        this.expenseArrayList = expenseArrayList;
        databaseHelper = new DatabaseHelper(this.context);
        this.totalTextView = totalTextView;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
    Expense expense = expenseArrayList.get(position);
    holder.amountItemTextview.setText(String.valueOf(expense.getAmount()));
    holder.noteItemTextview.setText(expense.getNote());

    if(expense.getTransactionType() == 1){
        holder.amountItemTextview.setTextColor(ContextCompat.getColor(context,R.color.red));
    }
    else {
        holder.amountItemTextview.setTextColor(ContextCompat.getColor(context,R.color.green));
    }

    holder.openMenuButton.setOnClickListener(v->{
        PopupMenu popup = new PopupMenu(context, v);
        popup.getMenuInflater().inflate(R.menu.sample_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> mainMenuItemClick(item, expense));
        popup.show();
    });
    }

    @SuppressLint("NonConstantResourceId")
    private boolean mainMenuItemClick(MenuItem item, Expense expense) {
        switch (item.getItemId()) {
            case R.id.edit_expense:  {
                editExpense(expense);
                return true;
            }
            case R.id.delete_expense: {
                deleteExpense(expense);
                return true;
            }
            default:
                return false;
        }
    }

    private void editExpense(@NonNull Expense expense) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.fragment_insert);
        EditText amountEditText = dialog.findViewById(R.id.amountEditText);
        EditText noteEditText = dialog.findViewById(R.id.noteEditText);
        Button saveButton = dialog.findViewById(R.id.saveButton);
        RadioButton debitButton = dialog.findViewById(R.id.debitButton);
        RadioButton creditButton = dialog.findViewById(R.id.creditButton);
        assert amountEditText != null;
        amountEditText.setText(String.valueOf(expense.getAmount()));
        assert noteEditText != null;
        noteEditText.setText(String.valueOf(expense.getNote()));
        if(expense.getTransactionType() == 1){
            assert debitButton != null;
            debitButton.setChecked(true);
        }
        else if(expense.getTransactionType() == 0){
            assert creditButton != null;
            creditButton.setChecked(true);
        }
        assert saveButton != null;
        saveButton.setOnClickListener(v1 -> {
            assert debitButton != null;
            databaseHelper.updateExpense(new Expense(expense.getId(),Integer.parseInt(amountEditText.getText().toString()),noteEditText.getText().toString(),debitButton.isChecked() ? 1 : 0));
            dialog.dismiss();
        });
        dialog.show();
    }
    private void deleteExpense(@NonNull Expense expense){
        databaseHelper.deleteExpense(expense);
    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<Expense> newExpense) {
        this.expenseArrayList = newExpense;
        notifyDataSetChanged();
    }

    public void updateTextView(String text) {
        totalTextView.setText(text);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView amountItemTextview,noteItemTextview;
        Button openMenuButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountItemTextview = itemView.findViewById(R.id.amountItemTextview);
            noteItemTextview = itemView.findViewById(R.id.noteItemTextview);
            openMenuButton = itemView.findViewById(R.id.openMenuButton);
        }
    }
}
