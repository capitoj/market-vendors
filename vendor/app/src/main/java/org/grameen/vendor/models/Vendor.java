package org.grameen.vendor.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.grameen.vendor.database.Sqlite;

/**
 * Created by Irama on 6/5/2015.
 */
public class Vendor {

    protected Context context;
    protected String appID;

    public Vendor(Context context){
        super();
        this.context = context;
        this.appID = null;
    }

    public Vendor(Context context, String appID){
        super();
        this.context = context;
        this.appID = appID;
    }

    public boolean isRegistered(){
        SQLiteDatabase db = Sqlite.getInstance(this.context);
        Cursor cursor = db.rawQuery("select count(*) as count from gm_vendor limit 1", null);
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0 ? true : false;
    }

    public void register(String vendorName){
        SQLiteDatabase db = Sqlite.getInstance(this.context);
        db.execSQL("insert into gm_vendor (vendor_name, app_id) values (?, ?)", new String[]{vendorName, this.appID});
        db.close();
    }

    public String getAppID(){
        SQLiteDatabase db = Sqlite.getInstance(this.context);
        Cursor cursor = db.rawQuery("select app_id from gm_vendor limit 1", null);
        cursor.moveToNext();
        String appID = cursor.getString(0);
        cursor.close();
        db.close();
        return appID;
    }
}
