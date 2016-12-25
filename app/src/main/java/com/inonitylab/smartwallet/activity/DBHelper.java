package com.inonitylab.smartwallet.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 19-Nov-16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "myapp.db"; //innitialize database name and version
    public static final int DB_VERSION = 1;

    /*create table users(
        id integer primary key autoincrement,
       email text,
       password text);
    */
    public static final String CREATE_TABLE_USERS = "create table users (" +
            "id integer primary key autoincrement, " +
            "email text, " +
            "pass text);";
    public static final String CREATE_TABLE_TRANSACTIONS = "create table transaction (" +
            "transactonid integer primary key autoincrement, " +
            "userid integer, " +
            "category text, " +
            "name text, " +
            "date text, " +
            "amount integer);";
    public static final String CREATE_TABLE_CATEGORIES = "create table categories (" +
            "categoryid integer primary key autoincrement, " +
            "categoryType text, " +
            "categoryName text, " +
            "userid integer);";
    public static final String CREATE_TABLE_REMINDERS = "create table transaction (" +
            "userid integer, " +
            "category text, " +
            "name text, " +
            "date text, " +
            "amount integer, " +
            "recurring integer, " +
            "repeatdate date, " +
            "reminderid primary key autoincrement);";


    public DBHelper(Context context) {  //explain this constructor and how it works with other activities.
        super(context, DB_NAME, null, DB_VERSION); //SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // since we have database name and version, we can change default
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
     //   db.execSQL(CREATE_TABLE_TRANSACTIONS);
        //db.execSQL(CREATE_TABLE_CATEGORIES);
        //db.execSQL(CREATE_TABLE_REMINDERS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //check this methods docuentation!
       // db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    String emailid, pass;
    public void addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();//write to databasen---EXPLAIN THIS!!

        ContentValues values = new ContentValues(); //input values into database
        values.put("email", email);
        values.put("pass", password);

        long id = db.insert("users",null, values); //inserting
        db.close();

    }


    //check if user logged in correctly
    public boolean getUser (String email, String pass){
        String selectQuery = "select * from users where email = "+ "'"+email+"'"  + " and pass = "+ "'"+pass+"'";
        SQLiteDatabase db = this.getReadableDatabase(); //--EXPLAIN THIS!!!!
        Cursor cursor = db.rawQuery(selectQuery, null); //--EXPLAIN THIS ALSO!!

        cursor.moveToFirst(); //goes to the top row

        if(cursor.getCount() >0){ //returns the number of rows in the cursor.
            return true;
        }
        cursor.close();
        db.close();
        return false;

    }

}
