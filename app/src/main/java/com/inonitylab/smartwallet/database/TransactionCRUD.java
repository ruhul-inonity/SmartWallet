package com.inonitylab.smartwallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inonitylab.smartwallet.model.TransactionModel;

/**
 * Created by ruhul on 12/28/16.
 */

public class TransactionCRUD extends DBHelper {
    private SQLiteDatabase db;

    public TransactionCRUD(Context context) {
        super(context);
    }

    //Insert into Pending Ledger transaction table
    public long insertTransaction(TransactionModel transaction) {

        db = this.getWritableDatabase();
        long result1 = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANSACTION_ID, 0);
            values.put(COLUMN_CATEGORY_ID, transaction.getCategoryId());
            values.put(COLUMN_USER_ID, transaction.getUserId());
            values.put(COLUMN_CATEGORY_TYPE, transaction.getCategoryType());
            values.put(COLUMN_AMOUNT, transaction.getAmount());
            values.put(COLUMN_NOTE, transaction.getNote());
            values.put(COLUMN_DATE, transaction.getDate());

            result1 = db.insert(TABLE_TRANSACTIONS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();

        if (result1 >= 0)
            return 1;
        else
            return -1;
    }

}
