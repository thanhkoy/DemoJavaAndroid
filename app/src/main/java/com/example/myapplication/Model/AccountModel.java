package com.example.myapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHandler;
import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectStudent;

import java.util.ArrayList;
import java.util.List;

/*Class này chứa các nghiệp vụ thao tác db list thêm sửa xoá bản ghi*/

public class AccountModel extends DatabaseHandler {
    /*Tao bien final db de connect db*/

    public AccountModel(Context context) {
        super(context);
    }

    public ObjectAcount checkAth(String username, String password) {
        ObjectAcount objectAcount = null;
        String sql = "SELECT role, id FROM account where username = '" + username + "' and password = '" + password + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor rs = db.rawQuery(sql, null);
        if (rs.moveToFirst()) {
            objectAcount = new ObjectAcount();
            int id = Integer.parseInt(rs.getString(rs.getColumnIndex("id")));
            int role = Integer.parseInt(rs.getString(rs.getColumnIndex("role")));
            objectAcount.id = id;
            objectAcount.role = role;
        } else {
            objectAcount = new ObjectAcount();
            int id = 0;
            int role = 0;
            objectAcount.id = id;
            objectAcount.role = role;
        }
        rs.close();
        db.close();

        return objectAcount;
    }

    public int checkUnique(String username) {
        int num;
        String sql = "SELECT count(*) as num FROM account where username = '" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor rs = db.rawQuery(sql, null);
        if (rs.moveToFirst()) {
            num = Integer.parseInt(rs.getString(rs.getColumnIndex("num")));
        } else {
            num = 0;
        }
        rs.close();
        db.close();

        return num;
    }

    public boolean create(ObjectAcount objectAcount) {
        ContentValues values = new ContentValues();

        values.put("username", objectAcount.username);
        values.put("password", objectAcount.password);
        values.put("role", objectAcount.role);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("account", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public List<ObjectAcount> read() {
        List<ObjectAcount> recordsList = new ArrayList<ObjectAcount>();

        String sql = "SELECT * FROM account ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                int role = cursor.getInt(cursor.getColumnIndex("role"));

                ObjectAcount objectAccount = new ObjectAcount();
                objectAccount.id = id;
                objectAccount.username = username;
                objectAccount.role = role;

                recordsList.add(objectAccount);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public ObjectAcount readSingleRecord(int account_id) {
        ObjectAcount objectAcount = null;

        String sql = "SELECT * FROM account WHERE id = " + account_id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            int role = Integer.parseInt(cursor.getString(cursor.getColumnIndex("role")));

            objectAcount = new ObjectAcount();
            objectAcount.id = id;
            objectAcount.username = username;
            objectAcount.password = password;
            objectAcount.role = role;

        }

        cursor.close();
        db.close();

        return objectAcount;
    }

    public boolean update(ObjectAcount objectAcount) {
        ContentValues values = new ContentValues();

        values.put("username", objectAcount.username);
        values.put("password", objectAcount.password);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectAcount.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("account", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }
}