package com.example.expensemanagerapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.database.DatabaseHelper;
import com.example.expensemanagerapp.model.Expense;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{

    private final Context context;
    private ArrayList<Expense> expenseArrayList;
    private DatabaseHelper databaseHelper;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseArrayList) {
        this.context = context;
        this.expenseArrayList = expenseArrayList;
        databaseHelper = new DatabaseHelper(this.context);
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
    Expense expense = expenseArrayList.get(position);
    holder.amountItemTextview.setText(String.valueOf(expense.getAmount()));
    holder.noteItemTextview.setText(expense.getNote());

    if(expense.getTransactionType() == 1){
        holder.amountItemTextview.setTextColor(context.getResources().getColor(R.color.red));
    }
    else {
        holder.amountItemTextview.setTextColor(context.getResources().getColor(R.color.green));
    }

    holder.openMenuButton.setOnClickListener(v->{
        PopupMenu popup = new PopupMenu(context, v);
        popup.getMenuInflater().inflate(R.menu.sample_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit_expense:  {

                    BottomSheetDialog dialog = new BottomSheetDialog(context);
                    dialog.setContentView(R.layout.fragment_insert);
                    EditText amountEditText = dialog.findViewById(R.id.amountEditText);
                    EditText noteEditText = dialog.findViewById(R.id.noteEditText);
                    Button saveButton = dialog.findViewById(R.id.saveButton);
                    RadioButton debitButton = dialog.findViewById(R.id.debitButton);
                    RadioButton creditButton = dialog.findViewById(R.id.creditButton);

                    amountEditText.setText(String.valueOf(expense.getAmount()));
                    noteEditText.setText(String.valueOf(expense.getNote()));

                    if(expense.getTransactionType() == 1){
                        debitButton.setChecked(true);
                    }
                    else if(expense.getTransactionType() == 0){
                        creditButton.setChecked(true);
                    }

                    assert saveButton != null;
                    saveButton.setOnClickListener(v1 -> {
                        int transactionType;
                        int amount = Integer.parseInt(amountEditText.getText().toString());
                        Boolean transactionTypeBoolean = debitButton.isChecked();
                        if(transactionTypeBoolean){
                            transactionType = 1;
                        }
                        else {
                            transactionType = 0;
                        }
                        if(transactionType == 1){
                            amount = -amount;
                        }
                        databaseHelper.updateExpense(new Expense(expense.getId(),amount,noteEditText.getText().toString(),transactionType));
                        dialog.dismiss();
                    });

                    dialog.show();
                    return true;
                }
                case R.id.delete_expense: {
                    databaseHelper.deleteExpense(expense);
                    notifyDataSetChanged();
                    return true;
                }
                default:
                    return false;
            }
        });
        popup.show();
    });

    }

    private boolean mainMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_expense:  {
                Toast.makeText(context, "edit_expense", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.delete_expense: {
                Toast.makeText(context, "delete_expense", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
