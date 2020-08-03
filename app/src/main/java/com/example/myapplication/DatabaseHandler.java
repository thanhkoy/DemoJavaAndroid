package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    protected static final String DATABASE_NAME = "MyApp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_student = "CREATE TABLE students" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstname TEXT," +
                "email TEXT)";
        String sql_account = "CREATE TABLE account " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT, " +
                "password TEXT,"+
                "role INTEGER)";
        String sql_account_2 = "Insert into account(username, password, role) values ('admin', 123456, 1)"; /*role 1->user manage; role 2->packet manage; role 2->packet status change*/
        String sql_account_3 = "Insert into account(username, password, role) values ('thanh', 'thanh', 2)"; /*role 1->user manage; role 2->packet manage; role 2->packet status change*/
        String sql_account_4 = "Insert into account(username, password, role) values ('demo', 'thanh', 3)"; /*role 1->user manage; role 2->packet manage; role 2->packet status change*/
        String sql_packet = "CREATE TABLE packet " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT, " +
                "unit TEXT,"+
                "transporter INTEGER,"+
                "from_address TEXT,"+
                "to_address TEXT,"+
                "cost INTEGER,"+
                "status INTEGER,"+
                "description TEXT)";
        db.execSQL(sql_student);
        db.execSQL(sql_account);
        db.execSQL(sql_account_2);
        db.execSQL(sql_account_3);
        db.execSQL(sql_account_4);
        db.execSQL(sql_packet);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_student = "DROP TABLE IF EXISTS students";
        String sql_account = "DROP TABLE IF EXISTS account";
        String sql_packet = "DROP TABLE IF EXISTS packet";
        db.execSQL(sql_student);
        db.execSQL(sql_account);
        db.execSQL(sql_packet);

        onCreate(db);
    }
}