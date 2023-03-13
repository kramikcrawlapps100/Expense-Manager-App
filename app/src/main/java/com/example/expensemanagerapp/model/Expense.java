package com.example.expensemanagerapp.model;

public class Expense {

    public static final String TABLE_NAME = "expenses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TRANSACTIONTYPE = "transactionType";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_AMOUNT + " INTEGER,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_TRANSACTIONTYPE + " INTEGER"
                    + ")";
    private int id;
    private int amount;
    private String note;
    private int transactionType;

    public Expense() {
    }

    public Expense(int amount, String note, int transactionType) {
        this.amount = amount;
        this.note = note;
        this.transactionType = transactionType;
    }

    public Expense(int id, int amount, String note, int transactionType) {
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.transactionType = transactionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }
}
