package org.grameen.vendor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.grameen.vendor.utils.Constants;

/**
 * Created by Joseph Capito on 6/5/2015.
 */
public class Sqlite {

    public static SQLiteDatabase getInstance(Context context){
        return context.openOrCreateDatabase(Constants.DATABASE, Context.MODE_PRIVATE, null);
    }

    public static void initDataTables(Context context){
        Sqlite.createDataTables(context);
    }

    protected static void createDataTables(Context context){
        SQLiteDatabase db = Sqlite.getInstance(context);

        db.execSQL("create table if not exists gm_vendor(vendor_name VARCHAR, app_id VARCHAR)");

        db.execSQL("create table if not exists gm_units (unit_id INTEGER PRIMARY KEY, unit_name VARCHAR)");

        db.execSQL("create table if not exists gm_items(item_id INTEGER PRIMARY KEY, item_name VARCHAR)");

        db.execSQL("create table if not exists gm_vendor_items (id INTEGER PRIMARY KEY, item_id INTEGER, price INTEGER, unit_id INTEGER)");

        db.close();
    }
}
