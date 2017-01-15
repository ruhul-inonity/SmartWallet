package com.inonitylab.smartwallet.model;

/**
 * Created by ruhul on 1/15/17.
 */

public class ReportModel {
    String categoryName;
    Double amount;
    Double spentAmount;

    public ReportModel(String categoryName, Double amount) {
        this.categoryName = categoryName;
        this.amount = amount;
    }

    public ReportModel(String categoryName, Double amount, Double spentAmount) {
        this.categoryName = categoryName;
        this.amount = amount;
        this.spentAmount = spentAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(Double spentAmount) {
        this.spentAmount = spentAmount;
    }
}
