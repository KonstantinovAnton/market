package com.example.market;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactsDb";


    public static final String TABLE_USERS = "userTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";


    public static final String TABLE_MEAT = "meatTable";

    public static final String KEY_ID1 = "_id1";
    public static final String KEY_SORT = "sort";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_PRICE = "price";

    public static final String TABLE_DRINKS = "drinksTable";

    public static final String KEY_ID2 = "_id2";
    public static final String KEY_NAMING = "drinkName";
    public static final String KEY_VOL = "vol";
    public static final String KEY_PRICED = "drinkPrice";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_USERS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_PASSWORD + " text" + ")");

        db.execSQL("create table " + TABLE_MEAT + "(" + KEY_ID1
                + " integer primary key," + KEY_SORT + " text," + KEY_WEIGHT + " text," + KEY_PRICE + " text" + ")");

        db.execSQL("create table " + TABLE_DRINKS + "(" + KEY_ID2
                + " integer primary key," + KEY_NAMING + " text," + KEY_VOL + " text," + KEY_PRICED + " text" + ")");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {


            db.execSQL("drop table if exists " + TABLE_USERS);
            onCreate(db);
            db.execSQL("drop table if exists " + TABLE_MEAT);
            onCreate(db);
            db.execSQL("drop table if exists " + TABLE_DRINKS);
            onCreate(db);
        }
    }
}
