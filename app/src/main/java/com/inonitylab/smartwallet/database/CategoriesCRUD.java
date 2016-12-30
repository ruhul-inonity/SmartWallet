package com.inonitylab.smartwallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inonitylab.smartwallet.model.Categories;

import java.util.ArrayList;

/**
 * Created by user on 12/27/16.
 */

public class CategoriesCRUD extends DBHelper {

    private SQLiteDatabase db;

    public CategoriesCRUD(Context context) {
        super(context);
    }

    public void insertCategories() {
        long result = 0;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

            values.put(COLUMN_CATEGORY_ID, 1001); // for expense type id 1###
            values.put(COLUMN_USER_ID, 0); // 0 -> this acc for all users
            values.put(COLUMN_CATEGORY_NAME, "Food"); //account_name
            values.put(COLUMN_CATEGORY_TYPE, "Expense"); //account_type
            result = result + db.insert(TABLE_CATEGORIES, null, values);

            values.put(COLUMN_CATEGORY_ID, 1002);
            values.put(COLUMN_USER_ID, 0);
            values.put(COLUMN_CATEGORY_NAME, "House");
            values.put(COLUMN_CATEGORY_TYPE, "Expense");
            result = result + db.insert(TABLE_CATEGORIES, null, values);

            values.put(COLUMN_CATEGORY_ID, 1003);
            values.put(COLUMN_USER_ID, 0);
            values.put(COLUMN_CATEGORY_NAME, "Eating out");
            values.put(COLUMN_CATEGORY_TYPE, "Expense");
            result = result + db.insert(TABLE_CATEGORIES, null, values);

            values.put(COLUMN_CATEGORY_ID, 1004);
            values.put(COLUMN_USER_ID, 0);
            values.put(COLUMN_CATEGORY_NAME, "Transport");
            values.put(COLUMN_CATEGORY_TYPE, "Expense");
            result = result + db.insert(TABLE_CATEGORIES, null, values);

            values.put(COLUMN_CATEGORY_ID, 1005);
            values.put(COLUMN_USER_ID, 0);
            values.put(COLUMN_CATEGORY_NAME, "Entertainment");
            values.put(COLUMN_CATEGORY_TYPE, "Expense");
            result = result + db.insert(TABLE_CATEGORIES, null, values);

            values.put(COLUMN_CATEGORY_ID, 1006);
            values.put(COLUMN_USER_ID, 0);
            values.put(COLUMN_CATEGORY_NAME, "Cloths");
            values.put(COLUMN_CATEGORY_TYPE, "Expense");
            result = result + db.insert(TABLE_CATEGORIES, null, values);

        values.put(COLUMN_CATEGORY_ID, 2001); // for income type id 2###
        values.put(COLUMN_USER_ID, 0);
        values.put(COLUMN_CATEGORY_NAME, "Salary");
        values.put(COLUMN_CATEGORY_TYPE, "Income");
        result = result + db.insert(TABLE_CATEGORIES, null, values);

        values.put(COLUMN_CATEGORY_ID, 2002);
        values.put(COLUMN_USER_ID, 0);
        values.put(COLUMN_CATEGORY_NAME, "Savings");
        values.put(COLUMN_CATEGORY_TYPE, "Income");
        result = result + db.insert(TABLE_CATEGORIES, null, values);

        values.put(COLUMN_CATEGORY_ID, 2003); // for income type id 2###
        values.put(COLUMN_USER_ID, 0);
        values.put(COLUMN_CATEGORY_NAME, "Deposits");
        values.put(COLUMN_CATEGORY_TYPE, "Income");
        result = result + db.insert(TABLE_CATEGORIES, null, values);
    }

    public ArrayList<Categories> getAll_Categories() {
        ArrayList<Categories> allCategoryList = new ArrayList<Categories>();

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY category_id ASC", null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                int user_id = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                String categoryType = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_TYPE));

                Categories categories = new Categories(id, category_id, user_id, categoryName,categoryType);
                allCategoryList.add(categories);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return allCategoryList;
    }
    public ArrayList<Categories> getIncomeCategories() {
        ArrayList<Categories> incomeCategoryList = new ArrayList<Categories>();

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories where category_type = 'Income' ORDER BY category_id ASC", null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                int user_id = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                String categoryType = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_TYPE));

                Log.d("getIncomeCategories",category_id+"............. "+categoryName);
                Categories categories = new Categories(id, category_id, user_id, categoryName,categoryType);
                incomeCategoryList.add(categories);

                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return incomeCategoryList;
    }

    public ArrayList<Categories> getExpenseCategories() {
        ArrayList<Categories> expenseCategoryList = new ArrayList<Categories>();

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories where category_type = 'Expense' ORDER BY category_id ASC", null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                int user_id = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                String categoryType = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_TYPE));

                Log.d("getIncomeCategories",category_id+"............. "+categoryName);
                Categories categories = new Categories(id, category_id, user_id, categoryName,categoryType);
                expenseCategoryList.add(categories);

                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return expenseCategoryList;
    }
}
