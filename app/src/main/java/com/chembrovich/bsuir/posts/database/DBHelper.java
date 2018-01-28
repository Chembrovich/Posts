package com.chembrovich.bsuir.posts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chembrovich.bsuir.posts.database.DBContract.CompanyContract;
import com.chembrovich.bsuir.posts.database.DBContract.AddressContract;
import com.chembrovich.bsuir.posts.database.DBContract.UserContract;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "users.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String UNIQUE = " UNIQUE ";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_COMPANY_TABLE_SCRIPT = CREATE_TABLE + CompanyContract.TABLE_NAME + " (" +
                CompanyContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CompanyContract.COLUMN_NAME + TEXT_TYPE + UNIQUE + COMMA_SEP +
                CompanyContract.COLUMN_CATCH_PHRASE + TEXT_TYPE + COMMA_SEP +
                CompanyContract.COLUMN_BS + TEXT_TYPE +
                " )";

        final String CREATE_ADDRESS_TABLE_SCRIPT = CREATE_TABLE + AddressContract.TABLE_NAME + " (" +
                AddressContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddressContract.COLUMN_STREET + TEXT_TYPE + COMMA_SEP +
                AddressContract.COLUMN_SUITE + TEXT_TYPE + COMMA_SEP +
                AddressContract.COLUMN_CITY + TEXT_TYPE + COMMA_SEP +
                AddressContract.COLUMN_ZIPCODE + TEXT_TYPE + COMMA_SEP +
                AddressContract.COLUMN_LATITUDE + TEXT_TYPE + COMMA_SEP +
                AddressContract.COLUMN_LONGITUDE + TEXT_TYPE + COMMA_SEP +
                UNIQUE + " (" +
                AddressContract.COLUMN_LATITUDE + COMMA_SEP + AddressContract.COLUMN_LONGITUDE + " )" +
                " )";

        final String CREATE_USER_TABLE_SCRIPT = CREATE_TABLE + UserContract.TABLE_NAME + " (" +
                UserContract._ID + " INTEGER PRIMARY KEY," +
                UserContract.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                UserContract.COLUMN_USERNAME + TEXT_TYPE + COMMA_SEP +
                UserContract.COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP +
                UserContract.COLUMN_ADDRESS_ID + INTEGER_TYPE + COMMA_SEP +
                UserContract.COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
                UserContract.COLUMN_WEBSITE + TEXT_TYPE + COMMA_SEP +
                UserContract.COLUMN_COMPANY_ID + INTEGER_TYPE +
                " )";

        sqLiteDatabase.execSQL(CREATE_ADDRESS_TABLE_SCRIPT);
        sqLiteDatabase.execSQL(CREATE_COMPANY_TABLE_SCRIPT);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String DROP_USER_TABLE_SCRIPT = DROP_TABLE + UserContract.TABLE_NAME;
        final String DROP_ADDRESS_TABLE_SCRIPT = DROP_TABLE + AddressContract.TABLE_NAME;
        final String DROP_COMPANY_TABLE_SCRIPT = DROP_TABLE + CompanyContract.TABLE_NAME;

        sqLiteDatabase.execSQL(DROP_USER_TABLE_SCRIPT);
        sqLiteDatabase.execSQL(DROP_ADDRESS_TABLE_SCRIPT);
        sqLiteDatabase.execSQL(DROP_COMPANY_TABLE_SCRIPT);
    }
}
