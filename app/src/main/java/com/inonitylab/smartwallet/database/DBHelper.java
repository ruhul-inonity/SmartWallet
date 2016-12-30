package com.inonitylab.smartwallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 19-Nov-16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "smart_wallet"; //initialize database name and version
    public static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_REMINDERS = "reminders";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_TRANSACTION_ID = "transaction_id";

    public static final String COLUMN_USER_EMAIL = "u_email";
    public static final String COLUMN_USER_PASSWORD = "u_password";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_NOTE = "note";

    public static final String COLUMN_CATEGORY_NAME = "category_name";
    public static final String COLUMN_CATEGORY_TYPE = "category_type";


    private String CREATE_TABLE_USERS = "create table " + TABLE_USERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_USER_EMAIL + " TEXT," +
            COLUMN_USER_PASSWORD + " TEXT" +
            ");";

    private String CREATE_TABLE_TRANSACTIONS = "create table " + TABLE_TRANSACTIONS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_TRANSACTION_ID + " INTEGER," +
            COLUMN_CATEGORY_ID + " INTEGER," +
            COLUMN_CATEGORY_TYPE + " TEXT," +
            COLUMN_USER_ID + " INTEGER," +
            COLUMN_AMOUNT + " DOUBLE," +
            COLUMN_NOTE + " TEXT," +
            COLUMN_DATE + " TEXT" +
            ");";

    private String CREATE_TABLE_CATEGORIES = "create table " + TABLE_CATEGORIES + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_CATEGORY_ID + " INTEGER," +
            COLUMN_USER_ID + " INTEGER," +
            COLUMN_CATEGORY_NAME + " TEXT," +
            COLUMN_CATEGORY_TYPE + " TEXT" +
            ");";

    private String CREATE_TABLE_REMINDERS = "create table " + TABLE_REMINDERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_TRANSACTION_ID + " INTEGER," +
            COLUMN_CATEGORY_ID + " INTEGER," +
            COLUMN_USER_ID + " INTEGER," +
            COLUMN_AMOUNT + " DOUBLE," +
            COLUMN_NOTE + " TEXT," +
            COLUMN_DATE + " TEXT," +
            COLUMN_TIME + " TEXT" +
            ");";


    public DBHelper(Context context) {  //explain this constructor and how it works with other activities.
        super(context, DB_NAME, null, DB_VERSION); //SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // since we have database name and version, we can change default
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_REMINDERS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //check this methods docuentation!
       // db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues(); //input values into database
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long id = db.insert(TABLE_USERS,null, values); //inserting
        db.close();

    }


    //check if user logged in correctly
    public boolean getUser (String email, String pass){
        String selectQuery = "select * from users where u_email = "+ "'"+email+"'"  + " and u_password = "+ "'"+pass+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst(); //goes to the top row
        if(cursor.getCount() >0){ //returns the number of rows in the cursor.
            return true;
        }
        cursor.close();
        db.close();
        return false;

    }

}
