package com.example.expensemanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagerapp.R;
import com.example.expensemanagerapp.model.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{

    Context context;
    ArrayList<Expense> expenseArrayList;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseArrayList) {
        this.context = context;
        this.expenseArrayList = expenseArrayList;
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

    if(expense.getTransactionType()){
        holder.amountItemTextview.setTextColor(context.getResources().getColor(R.color.red));
    }
    else {
        holder.amountItemTextview.setTextColor(context.getResources().getColor(R.color.green));
    }

    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView amountItemTextview,noteItemTextview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amountItemTextview = itemView.findViewById(R.id.amountItemTextview);
            noteItemTextview = itemView.findViewById(R.id.noteItemTextview);
        }
    }
}
