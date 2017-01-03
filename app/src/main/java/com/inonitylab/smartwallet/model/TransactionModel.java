package com.inonitylab.smartwallet.model;

/**
 * Created by user on 12/28/16.
 */

public class TransactionModel {
    int _id,userId,categoryId,transactionId;
    Double amount;
    String note,date,categoryType,time;

    public TransactionModel(int userId, int categoryId, int transactionId,String categoryType ,Double amount, String note, String date) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }

    public TransactionModel(int _id, int userId, int categoryId, int transactionId, Double amount, String note, String date, String categoryType) {
        this._id = _id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.categoryType = categoryType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TransactionModel() {
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
