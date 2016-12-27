package com.inonitylab.smartwallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ruhul on 12/27/16.
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
}
