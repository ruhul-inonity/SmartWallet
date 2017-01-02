package com.inonitylab.smartwallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inonitylab.smartwallet.model.TransactionModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

    //get all transactions
    public ArrayList<TransactionModel> getAllTransactions() {
        ArrayList<TransactionModel> transactionList = new ArrayList<TransactionModel>();
        db = this.getReadableDatabase();
        String query = "select * from transactions order by date desc";
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    int trans_id = cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_ID));
                    int category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                    String category_type = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_TYPE));
                    int user_id = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                    double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));
                    String note = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                    TransactionModel transactionModel = new TransactionModel(id, user_id, category_id, trans_id, amount, note, date, category_type);
                    /*DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(8);*/

                    transactionList.add(transactionModel);
                    Log.d("all transaction data", "......................." + trans_id + category_type + " amount " + amount + " " + note + date);
                    cursor.moveToNext();
                }
                cursor.close();
            } else

                Log.d("All transaction db", "...................Cursor is empty");
        } catch (Exception e) {
            Log.e("All transaction db", "...............Exception  While Receiving Data From transactions " + e);
        }
        db.close();
        return transactionList;
    }

    public ArrayList getBalanceStatement(String fromDate, String toDate, String accountType) {

        db = this.getReadableDatabase();
        ArrayList<String[]> report = new ArrayList<>();

        Cursor cursor = db.rawQuery("select category_name , balance from (select * from categories where category_type = ?) " +
                "left  join (" +
                "select a.category_name,sum(b.amount) as balance " +
                "from categories a, transactions b " +
                "where b.date between ? and ?  and a.category_type = ? " +
                "and a.category_id = b.category_id " +
                "group by a.category_name) " +
                "using(category_name) " +
                "order by balance desc", new String[]{accountType, fromDate, toDate, accountType});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String statementRow[] = new String[2];
                statementRow[0] = cursor.getString(cursor.getColumnIndex("category_name"));
                statementRow[1] = String.valueOf(cursor.getDouble(cursor.getColumnIndex("balance")));
                report.add(statementRow);
               // Log.d("getBalanceStatement","........................... category name "+statementRow[0]+" balance "+statementRow[1]);
            }
            while (cursor.moveToNext());
            cursor.close();
            db.close();
            return report;
        }
        db.close();
        return report;

/*        select category_name , balance from (select * from categories where category_type = "Expense")
        left  join (
                select a.category_name,sum(b.amount) as balance
        from categories a, transactions b
        where b.date between  '2017/01/01' and  '2017/01/04' and a.category_type = 'Expense'
        and a.category_id = b.category_id
        group by a.category_name)
        using(category_name)
        order by balance desc*/

    }

}
