package com.groundsoft.dean.shoppinglist.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.text.Spanned;

import com.groundsoft.dean.shoppinglist.ShoppingListMainActivity;

import java.util.ArrayList;

public class Items extends SQLiteOpenHelper {
    public Integer id;
    public Integer listid;
    public Integer categoryid;
    public Integer quantity;
    public Integer price;
    public String name;
    public Integer checked;
    public Integer date;

    static final String TABLE_ITEMS = "Items";
    static final String TABLE_ITEMS_KEY_ID = "id";
    static final String TABLE_ITEMS_LIST_ID = "listid";
    static final String TABLE_ITEMS_CATEGORY_ID = "categoryid";
    static final String TABLE_ITEMS_KEY_NAME = "name";
    static final String TABLE_ITEMS_KEY_PRICE = "price";
    static final String TABLE_ITEMS_KEY_QUANTITY = "quantity";
    static final String TABLE_ITEMS_KEY_CHECKED = "checked";
    static final String TABLE_ITEMS_KEY_DATE = "date";

    SQLiteDatabase db1;

    public Items(Context context) {
        super(context, DbConsts.DATABASE_NAME, null, DbConsts.DATABASE_VERSION);

        if (context != null) {
            db1 = ShoppingListMainActivity.dba;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DbConsts.createAll(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbConsts.upgradeAll(db, oldVersion, newVersion);
    }


    public void updateItemCheckedStatus(Integer itemId, Integer checked) {
        //SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + TABLE_ITEMS +
                " set " + TABLE_ITEMS_KEY_CHECKED + "=" + checked +
                " where " + TABLE_ITEMS_KEY_ID + " = " + itemId;

        Cursor cursor = db1.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();

        //db.close();
    }

    public Integer addItem(Integer listid, String name, Float price, Float quantity) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_ITEMS_LIST_ID, listid);
        vals.put(TABLE_ITEMS_KEY_NAME, name);
        vals.put(TABLE_ITEMS_KEY_PRICE, price);
        vals.put(TABLE_ITEMS_KEY_QUANTITY, quantity);
        vals.put(TABLE_ITEMS_KEY_CHECKED, 0);

        Integer result = (int) db1.insert(TABLE_ITEMS, null, vals);
        //db1.close();

        return result;
    }

    public Integer addItemTest(Integer listid, Integer categoryid, String name, Float price, Float quantity, Integer checked, Integer date) {
        SQLiteDatabase db = this.getWritableDatabase();

        String safeName = name.replace("'","''");

        ContentValues vals = new ContentValues();
        vals.put(TABLE_ITEMS_LIST_ID, listid);
        vals.put(TABLE_ITEMS_CATEGORY_ID, categoryid);

        vals.put(TABLE_ITEMS_KEY_NAME, safeName);
        vals.put(TABLE_ITEMS_KEY_PRICE, price);
        vals.put(TABLE_ITEMS_KEY_QUANTITY, quantity);
        vals.put(TABLE_ITEMS_KEY_CHECKED, checked);
        vals.put(TABLE_ITEMS_KEY_DATE, date);

        Integer result = (int) db.insert(TABLE_ITEMS, null, vals);
        db.close();

        return result;
    }

    public Spanned getFirstItems(Integer listid, Integer limit) {
        String result = "";
        //TODO: массив и соединение по ,

        //ArrayList items = new ArrayList<Items>();
        String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid + " limit " + limit;


        Cursor cursor = db1.rawQuery(query, null);

        while (cursor.moveToNext()) {
            /*
            Items item = new Items(null);
            item.name = cursor.getString(3);
            item.quantity = cursor.getInt(5);
            item.checked = cursor.getInt(6);
            items.add(item);
            */
            if (cursor.getInt(6) == 1) {
                result += "<s>" + cursor.getString(3) + "</s> ";
            } else {
                result += cursor.getString(3) + " ";
            }

        }

        cursor.close();

        return Html.fromHtml(result);
    }

    public ArrayList<Items> getItems(Integer listid) {
        ArrayList items = new ArrayList<Items>();

        //String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid + " order by " + TABLE_ITEMS_KEY_DATE + " desc";

        String query = "select * from " + TABLE_ITEMS +
                " where " + TABLE_ITEMS_LIST_ID + " = " + listid +
                " order by " + TABLE_ITEMS_CATEGORY_ID + " asc, " + TABLE_ITEMS_KEY_DATE + " desc";

        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items(null);
            item.id = cursor.getInt(0);
            item.listid = cursor.getInt(1);
            item.categoryid = cursor.getInt(2);
            item.name = cursor.getString(3);
            item.price = cursor.getInt(4);
            item.quantity = cursor.getInt(5);
            item.checked = cursor.getInt(6);
            item.date = cursor.getInt(7);


            items.add(item);
        }

        cursor.close();

        return items;
    }

    public ArrayList<OneItem> getItems2(Integer listid) {
        ArrayList items = new ArrayList<OneItem>();

        //String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid + " order by " + TABLE_ITEMS_KEY_DATE + " desc";

        String query = "select * from " + TABLE_ITEMS +
                " where " + TABLE_ITEMS_LIST_ID + " = " + listid +
                " order by " + TABLE_ITEMS_CATEGORY_ID + " asc, " + TABLE_ITEMS_KEY_DATE + " desc";

        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery(query, null);

        while (cursor.moveToNext()) {
            OneItem item = new OneItem();
            item.id = cursor.getInt(0);
            item.listid = cursor.getInt(1);
            item.categoryid = cursor.getInt(2);
            item.name = cursor.getString(3);
            item.price = cursor.getFloat(4);
            item.quantity = cursor.getFloat(5);
            item.checked = cursor.getInt(6);
            item.date = cursor.getInt(7);


            items.add(item);
        }

        cursor.close();

        return items;
    }

    public void dropItem(Integer itemId) {
        String query = "delete from " + TABLE_ITEMS + " where " + TABLE_ITEMS_KEY_ID + "=" + itemId;
        db1.execSQL(query);
    }


    public void updateItem(Integer itemId, Integer categoryOrder, String name, Float itemprice, Float itemquontity) {

        String safeName = name.replace("'","''");

        String query = "update " + TABLE_ITEMS + " set " +
                TABLE_ITEMS_CATEGORY_ID + "=" + categoryOrder + ", " +
                TABLE_ITEMS_KEY_NAME + "='" + safeName + "', " +
                TABLE_ITEMS_KEY_PRICE + "=" + itemprice + ", " +
                TABLE_ITEMS_KEY_QUANTITY + "=" + itemquontity +
                " where " + TABLE_ITEMS_KEY_ID + "=" + itemId;
        db1.execSQL(query);
    }


}
