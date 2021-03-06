package com.groundsoft.dean.shoppinglist.Models;

import android.database.sqlite.SQLiteDatabase;

class DbConsts {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";

    static void createAll (SQLiteDatabase db){

        String query = "Create table " + Lists.TABLE_LISTS + " (" +
                Lists.TABLE_LISTS_KEY_ID + " integer primary key, " +
                Lists.TABLE_LISTS_KEY_NAME + " text, " +
                Lists.TABLE_LISTS_KEY_DATE + " integer)";
        db.execSQL(query);

        String query2 = "Create table " + Items.TABLE_ITEMS + " (" +
                Items.TABLE_ITEMS_KEY_ID + " integer primary key, " +
                Items.TABLE_ITEMS_LIST_ID + " integer, " +
                Items.TABLE_ITEMS_CATEGORY_ID + " integer, " +
                Items.TABLE_ITEMS_KEY_NAME + " text, " +
                Items.TABLE_ITEMS_KEY_PRICE + " float, " +
                Items.TABLE_ITEMS_KEY_QUANTITY + " float, " +
                Items.TABLE_ITEMS_KEY_CHECKED + " integer, " +
                Items.TABLE_ITEMS_KEY_DATE + " integer)";
        db.execSQL(query2);

        String query3 = "Create table " + Categories1.TABLE_CATEGORIES + " (" +
                Categories1.TABLE_CATEGORIES_KEY_ID + " integer primary key, " +
                Categories1.TABLE_CATEGORIES_KEY_NAME + " text, " +
                Categories1.TABLE_CATEGORIES_KEY_ORDER + " integer)";
        db.execSQL(query3);

        String query4 = "Create table " + DefItems.TABLE_DEFITEMS + " (" +
                DefItems.TABLE_DEFITEMS_KEY_ID + " integer primary key, " +
                DefItems.TABLE_DEFITEMS_KEY_NAME + " text, " +
                DefItems.TABLE_DEFITEMS_KEY_ORDER + " integer, " +
                DefItems.TABLE_DEFITEMS_KEY_DEFCATEGORY + " integer)";
        db.execSQL(query4);

    }

    static void upgradeAll(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Items.TABLE_ITEMS);
        db.execSQL("drop table if exists " + Categories1.TABLE_CATEGORIES);
        db.execSQL("drop table if exists " + Lists.TABLE_LISTS);
        db.execSQL("drop table if exists " + DefItems.TABLE_DEFITEMS);
        createAll(db);
    }
}
