package com.example.expensemanagerapp.model;

public class Expense {

    private int amount;
    private String note;
    private Boolean transactionType;

    public Expense(int amount, String note, Boolean transactionType) {
        this.amount = amount;
        this.note = note;
        this.transactionType = transactionType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Boolean transactionType) {
        this.transactionType = transactionType;
    }
}
