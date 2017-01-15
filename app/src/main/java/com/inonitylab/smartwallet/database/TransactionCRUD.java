package com.inonitylab.smartwallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inonitylab.smartwallet.model.ReportModel;
import com.inonitylab.smartwallet.model.TransactionModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.R.attr.accountType;

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

    public long updateTransaction(TransactionModel transaction) {

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

            result1 = db.update(TABLE_TRANSACTIONS, values, COLUMN_ID + " = ?" ,new String[transaction.get_id()]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();

        if (result1 >= 0)
            return 1;
        else
            return -1;
    }


    //Insert into Pending Ledger transaction table
    public long insertReminder(TransactionModel transaction) {

        db = this.getWritableDatabase();
        long result1 = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANSACTION_ID, 0);
            values.put(COLUMN_CATEGORY_ID, transaction.getCategoryId());
            values.put(COLUMN_USER_ID, transaction.getUserId());
            values.put(COLUMN_AMOUNT, transaction.getAmount());
            values.put(COLUMN_NOTE, transaction.getNote());
            values.put(COLUMN_DATE, transaction.getDate());
            values.put(COLUMN_TIME, transaction.getTime());

            result1 = db.insert(TABLE_REMINDERS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();

        if (result1 >= 0)
            return 1;
        else
            return -1;
    }

    //Insert into Pending Ledger transaction table
    public long insertBudget(TransactionModel transaction) {

        db = this.getWritableDatabase();
        long result1 = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, 0);
            values.put(COLUMN_CATEGORY_NAME, transaction.getCategoryName());
            values.put(COLUMN_AMOUNT, transaction.getAmount());
            values.put(COLUMN_TO_DATE, transaction.getToDate());
            values.put(COLUMN_FROM_DATE, transaction.getFromDate());
            values.put(COLUMN_TIME, transaction.getTime());

            result1 = db.insert(TABLE_BUDGETS, null, values);
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

    public ArrayList<TransactionModel> getTransactionsByCategory(int categoryId) {
        ArrayList<TransactionModel> transactionList = new ArrayList<TransactionModel>();
        db = this.getReadableDatabase();
        String query = "select * from transactions where category_id = ? order by date desc";
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
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

    //get all reminders
    public ArrayList<TransactionModel> getAllReminders() {
        ArrayList<TransactionModel> reminderList = new ArrayList<TransactionModel>();
        db = this.getReadableDatabase();
        String query = "select * from reminders order by date desc";
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    int trans_id = cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_ID));
                    int category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                    int user_id = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                    double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));
                    String note = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                    String category_type = "";

                    TransactionModel transactionModel = new TransactionModel(id, user_id, category_id, trans_id, amount, note, date, category_type);
                    transactionModel.setTime(time);
                    /*DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(8);*/

                    reminderList.add(transactionModel);
                    Log.d("all reminder data", "......................." + trans_id + category_type + " amount " + amount + " " + note + date);
                    cursor.moveToNext();
                }
                cursor.close();
            } else

                Log.d("All reminder db", "...................Cursor is empty");
        } catch (Exception e) {
            Log.e("All reminder db", "...............Exception  While Receiving Data From transactions " + e);
        }
        db.close();
        return reminderList;
    }



    public ArrayList <ReportModel> getReport(String fromDate, String catType) {

        db = this.getReadableDatabase();
        ArrayList<ReportModel> reportList = new ArrayList<ReportModel>();

        Cursor cursor = db.rawQuery("select category_name , balance" +
                " from (select * from categories where category_type = ?)" +
                " left  join (" +
                " select a.category_name,sum(b.amount) as balance, strftime(\"%m-%Y\", date) as 'month-year'" +
                " from categories a, transactions b" +
                " where a.category_type = ?" +
                " and a.category_id = b.category_id" +
                " and strftime(\"%m-%Y\", date) = ?" +
                " group by a.category_name)" +
                " using(category_name)" +
                " order by balance desc", new String[]{catType, catType,fromDate});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String category= cursor.getString(cursor.getColumnIndex("category_name"));
                Double amount = cursor.getDouble(cursor.getColumnIndex("balance"));
                ReportModel reportModel = new ReportModel(category,amount);

                reportList.add(reportModel);
                Log.d("all report", "......................." + category + " amount " + amount);
                cursor.moveToNext();
            }
            cursor.close();
        } else
            Log.d("All reminder db", "...................Cursor is empty");

        db.close();
        return reportList;

    }

    public double getMaxBudget(String category){
        db = this.getReadableDatabase();
        double amount  = 0;

        try {
            Cursor cursor = db.rawQuery("select max(amount) from budgets where category_name = ? ",new String[]{category});
            if (cursor!= null && cursor.getCount() > 0){
                cursor.moveToPosition(0);
                amount = cursor.getDouble(0);
                cursor.close();
            }else {
                amount = 0;
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }


    public ArrayList<TransactionModel> getCorrespondingCategoryNames() {
        ArrayList<TransactionModel> categoryNames = new ArrayList<TransactionModel>();
        db = this.getReadableDatabase();
        String query = "select category_name from categories, transactions where categories.category_id = transactions.category_id";
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String category_name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));

                    TransactionModel transactionModel = new TransactionModel();
                    transactionModel.setCategoryName(category_name);


                    categoryNames.add(transactionModel);
                    Log.d("all transaction data", "......................." + category_name);
                    cursor.moveToNext();
                }
                cursor.close();
            } else

                Log.d("All transaction db", "...................Cursor is empty");
        } catch (Exception e) {
            Log.e("All transaction db", "...............Exception  While Receiving Data From transactions " + e);
        }
        db.close();
        return categoryNames;
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


    public ArrayList getIncome() {

        db = this.getReadableDatabase();
        ArrayList<Double> report = new ArrayList<>();

//        db.rawQuery("update transactions set date = REPLACE(date, '/', '-')", null);
//        Log.d("TAG", "-------- update complete");
        String q = "update transactions set date = REPLACE(date, ?, ?)";
        Cursor cursora = db.rawQuery(q, new String[] { "/", "-" });
        if(cursora !=null){
            Log.d("TAG","------------------------ cursur not null man!!");
        } else Log.d("TAG", "---------------CURSOR NULL!!!");

        Cursor cursor = db.rawQuery("select SUM(amount), strftime(\"%m-%Y\", date) as 'month-year' \n" +
                "       from transactions where category_type = 'Income' group by strftime(\"%m-%Y\", date)", null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Double statementRow;

                statementRow = cursor.getDouble(cursor.getColumnIndex("SUM(amount)"));
                report.add(statementRow);
                Log.d("getIncome","........................... balance "+statementRow);
            }
            while (cursor.moveToNext());
            cursor.close();
            db.close();

            return report;
        }

        db.close();
        return report;
    }

    public long deleteTransaction(int transId){
        db = this.getWritableDatabase();
        long result = 0;
        try {
            result = db.delete(TABLE_TRANSACTIONS, COLUMN_ID + "=?", new String[]{String.valueOf(transId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();

        if (result >= 0)
            return 1;
        else
            return -1;
    }
    public ArrayList getExpense() {

        db = this.getReadableDatabase();
        ArrayList<Double> report = new ArrayList<>();

        Cursor cursor = db.rawQuery("select SUM(amount), strftime(\"%m-%Y\", date) as 'month-year' \n" +
                "       from transactions where category_type = 'Expense' group by strftime(\"%m-%Y\", date);", null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                double statementRow;
                statementRow = cursor.getDouble(cursor.getColumnIndex("SUM(amount)"));
                report.add(statementRow);
                Log.d("getExpense","........................... balance "+statementRow);
            }
            while (cursor.moveToNext());
            cursor.close();
            db.close();
            return report;
        }
        db.close();
        return report;
    }


/*
    public ArrayList<TransactionModel> getAllBudgets() {
        ArrayList<TransactionModel> reminderList = new ArrayList<TransactionModel>();
        db = this.getReadableDatabase();
        String query = "select * from budgets order by date desc";
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String category= cursor.getString(cursor.getColumnIndex("category_name"));
                    Double amount = cursor.getDouble(cursor.getColumnIndex("balance"));
                    ReportModel reportModel = new ReportModel(category,amount);

                    TransactionModel transactionModel = new TransactionModel(id, user_id, category_id, trans_id, amount, note, date, category_type);
                    transactionModel.setTime(time);
                    *//*DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(8);*//*

                    reminderList.add(transactionModel);
                    Log.d("all reminder data", "......................." + trans_id + category_type + " amount " + amount + " " + note + date);
                    cursor.moveToNext();
                }
                cursor.close();
            } else

                Log.d("All reminder db", "...................Cursor is empty");
        } catch (Exception e) {
            Log.e("All reminder db", "...............Exception  While Receiving Data From transactions " + e);
        }
        db.close();
        return reminderList;
    }*/


}
