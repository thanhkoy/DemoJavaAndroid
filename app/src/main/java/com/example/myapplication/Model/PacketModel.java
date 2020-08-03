package com.example.myapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHandler;
import com.example.myapplication.Entity.ObjectAcount;
import com.example.myapplication.Entity.ObjectPacket;

import java.util.ArrayList;
import java.util.List;

/*Class này chứa các nghiệp vụ thao tác db list thêm sửa xoá bản ghi*/

public class PacketModel extends DatabaseHandler {
    /*Tao bien final db de connect db*/

    public PacketModel(Context context) {
        super(context);
    }

    public boolean create(ObjectPacket objectPacket) {
        ContentValues values = new ContentValues();

        values.put("code", objectPacket.code);
        values.put("unit", objectPacket.unit);
        values.put("transporter", objectPacket.transporter);
        values.put("from_address", objectPacket.from_address);
        values.put("to_address", objectPacket.to_address);
        values.put("cost", objectPacket.cost);
        values.put("description", objectPacket.description);
        values.put("status", objectPacket.status);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful;
        long id = db.insert("packet", null, values);
        if (id > 0){
            createSuccessful = true;
        } else {
            createSuccessful = false;
        }
        values.clear();
        values.put("code", objectPacket.unit + id);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString((int) id) };

        db.update("packet", values, where, whereArgs);
        db.close();

        return createSuccessful;
    }

    public List<ObjectPacket> read() {
        List<ObjectPacket> recordsList = new ArrayList<ObjectPacket>();

        String sql = "SELECT packet.id, packet.code, packet.cost, packet.status, account.username " +
                "FROM packet left join account on account.id = packet.transporter " +
                "ORDER BY packet.id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String code = cursor.getString(cursor.getColumnIndex("code"));
                int cost = cursor.getInt(cursor.getColumnIndex("cost"));
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                String username = cursor.getString(cursor.getColumnIndex("username"));

                ObjectPacket objectPacket = new ObjectPacket();
                objectPacket.id = id;
                objectPacket.code = code;
                objectPacket.cost = cost;
                objectPacket.status = status;
                objectPacket.transporter_name = username;

                recordsList.add(objectPacket);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public List<ObjectPacket> readByAccount( String account_id) {
        List<ObjectPacket> recordsList = new ArrayList<ObjectPacket>();

        String sql = "SELECT packet.id, packet.code, packet.cost, packet.status, account.username " +
                "FROM packet join account on account.id = packet.transporter " +
                "Where  account.id = " + account_id + " " +
                "ORDER BY packet.id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String code = cursor.getString(cursor.getColumnIndex("code"));
                int cost = cursor.getInt(cursor.getColumnIndex("cost"));
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                String username = cursor.getString(cursor.getColumnIndex("username"));

                ObjectPacket objectPacket = new ObjectPacket();
                objectPacket.id = id;
                objectPacket.code = code;
                objectPacket.cost = cost;
                objectPacket.status = status;
                objectPacket.transporter_name = username;

                recordsList.add(objectPacket);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public List<ObjectAcount> ListTransporter() {
        List<ObjectAcount> recordsList = new ArrayList<ObjectAcount>();

        String sql = "SELECT * FROM account where role = 3 ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String username = cursor.getString(cursor.getColumnIndex("username"));

                ObjectAcount objectAcount = new ObjectAcount();
                objectAcount.id = id;
                objectAcount.username = username;

                recordsList.add(objectAcount);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public ObjectPacket readSingleRecord(int packet_id) {
        ObjectPacket objectPacket = null;

        String sql = "SELECT * FROM packet WHERE id = " + packet_id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String unit = cursor.getString(cursor.getColumnIndex("unit"));
            String from = cursor.getString(cursor.getColumnIndex("from_address"));
            String to = cursor.getString(cursor.getColumnIndex("to_address"));
            int cost = Integer.parseInt(cursor.getString(cursor.getColumnIndex("cost")));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            objectPacket = new ObjectPacket();
            objectPacket.id = id;
            objectPacket.unit = unit;
            objectPacket.from_address = from;
            objectPacket.to_address = to;
            objectPacket.cost = cost;
            objectPacket.description = description;
        }

        cursor.close();
        db.close();

        return objectPacket;
    }

    public boolean update(ObjectPacket objectPacket) {
        ContentValues values = new ContentValues();

        values.put("code", objectPacket.code);
        values.put("unit", objectPacket.unit);
        values.put("from_address", objectPacket.from_address);
        values.put("to_address", objectPacket.to_address);
        values.put("cost", objectPacket.cost);
        values.put("description", objectPacket.description);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectPacket.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("packet", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

    public boolean processPacket(ObjectPacket objectPacket) {
        ContentValues values = new ContentValues();

        values.put("status", objectPacket.status);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectPacket.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("packet", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

    public int GetTransporterId(String name) {
        String sql = "SELECT id FROM account WHERE username = '" + name + "' limit 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        int id = 0;
        if (cursor.moveToFirst()) {
            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        }

        cursor.close();
        db.close();

        return id;
    }

}