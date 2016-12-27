package com.inonitylab.smartwallet.model;

/**
 * Created by user on 12/28/16.
 */

public class Categories {

    int _id,category_id,user_id;

    public Categories(int _id, int category_id, int user_id, String categoryName, String categoryType) {
        this._id = _id;
        this.category_id = category_id;
        this.user_id = user_id;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    String categoryName,categoryType;
}
