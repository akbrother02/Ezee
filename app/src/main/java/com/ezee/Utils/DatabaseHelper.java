package com.tadqa.android.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tadqa.android.accessibility.SolrData;

public class DatabaseHelper {

    private String TABLE_NAME = "CART_DATA";
    private String CART_ITEM_QUANTITY = "CART_ITEM_QUANTITY";
    private String DATABASE_NAME = "cart_detail.db";
    private int DATABASE_VERSION = 1;
    SQLiteDatabase database;

    SolrData solrdata=null;

    InnerHelper helper;

    public DatabaseHelper(Context context) {
        helper = new InnerHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        solrdata=(SolrData)context.getApplicationContext();
    }

    public DatabaseHelper open(){
        database = helper.getWritableDatabase();
        return this;
    }

    public int clearItemCart(){
        return  database.delete("CART_DATA", null, null);
    }

    public int clearQuantityCart(){
        return database.delete("CART_ITEM_QUANTITY", null, null);
    }

    public long insertCartItemQuantityData(String table, ContentValues contentValues, String id, String type){
        database.execSQL("DELETE FROM " + CART_ITEM_QUANTITY + " WHERE ITEM_ID = '" + id.trim() + "' AND ITEM_TYPE = '" + type.trim() + "';");
        return database.insert(table, null, contentValues);
    }

    public long insertData(String table, ContentValues contentValues, String id, String type){
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ITEM_ID = '" + id.trim() + "' AND ITEM_TYPE = '" + type.trim() + "';");
        return database.insert(table, null, contentValues);
    }

//    public boolean databaseExist()
//    {
//        File dbFile = new File(DATABASE_NAME);
//        return dbFile.exists();
//    }

    public Cursor getItemQuantityData(String table, String id){
        try {

            String query = "SELECT ITEM_QUANTITY FROM " + table + " WHERE ITEM_ID = '" + id.trim() + "';";
            return database.rawQuery(query, null);
        }
        catch (Exception ex)
        {

        }
        return  null;

    }
    public Cursor getTotalItemQuantityData(String table){
        try {

            String query = "SELECT ITEM_QUANTITY FROM " + table + "';";
            return database.rawQuery(query, null);
        }
        catch (Exception ex)
        {

        }
        return  null;

    }


    public Cursor getItemQuantityDataOfType(String table, String id, String type){
        try {

            String query = "SELECT ITEM_QUANTITY FROM " + table + " WHERE ITEM_ID = '" + id.trim() + "' AND ITEM_TYPE = '" + type.trim() + "';";
            return database.rawQuery(query, null);
        }
        catch (Exception ex)
        {}
        return null;
    }

    public void remove(String table, String id){
        database.execSQL("DELETE FROM " + table + " WHERE ITEM_ID = '" + id.trim() + "';");
    }

    public void removeWithType(String table, String id, String type){
        database.execSQL("DELETE FROM " + table + " WHERE ITEM_ID = '" + id.trim()+ "' AND ITEM_TYPE = '" + type.trim() + "';");
    }

    public void close(){
        database.close();
    }

    public Cursor getData(String table){
        return database.query(table, null, null, null, null, null, null);
    }

    public class InnerHelper extends SQLiteOpenHelper {

        public InnerHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(ITEM_ID string, ITEM_NAME string, ITEM_PRICE integer, ITEM_QUANTITY integer, ITEM_TYPE string,ITEM_IMAGE_URL string, ITEM_TOTAL_PRICE integer);");
            db.execSQL("CREATE TABLE " + CART_ITEM_QUANTITY + "(ITEM_ID string, ITEM_TYPE string, ITEM_QUANTITY integer);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
