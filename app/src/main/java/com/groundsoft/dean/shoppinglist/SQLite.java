package com.groundsoft.dean.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class SQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.groundsoft.dean.shoppinglist.db";

    private static final String TABLE_LISTS = "lists";
    private static final String TABLE_LISTS_KEY_ID = "id";
    private static final String TABLE_LISTS_KEY_NAME = "name";
    private static final String TABLE_LISTS_KEY_DATE = "date";

    private static final String TABLE_ITEMS = "Items";
    private static final String TABLE_ITEMS_KEY_ID = "id";
    private static final String TABLE_ITEMS_LIST_ID = "listid";
    private static final String TABLE_ITEMS_CATEGORY_ID = "categoryid";
    private static final String TABLE_ITEMS_KEY_NAME = "name";
    private static final String TABLE_ITEMS_KEY_PRICE = "price";
    private static final String TABLE_ITEMS_KEY_QUANTITY = "quantity";
    private static final String TABLE_ITEMS_KEY_CHECKED = "checked";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_CATEGORIES_KEY_ID = "id";
    private static final String TABLE_CATEGORIES_KEY_NAME = "name";
    private static final String TABLE_CATEGORIES_KEY_ORDER = "order";

    private static final String TABLE_DEFITEMS = "categories";
    private static final String TABLE_DEFITEMS_KEY_ID = "id";
    private static final String TABLE_DEFITEMS_KEY_NAME = "name";
    private static final String TABLE_DEFITEMS_KEY_ORDER = "order";

    SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table " + TABLE_LISTS + " (" +
                TABLE_LISTS_KEY_ID + " integer primary key, " +
                TABLE_LISTS_KEY_NAME + " text, " +
                TABLE_LISTS_KEY_DATE + " integer)";
        sqLiteDatabase.execSQL(query);

        String query2 = "Create table " + TABLE_ITEMS + " (" +
                TABLE_ITEMS_KEY_ID + " integer primary key, " +
                TABLE_ITEMS_LIST_ID + " integer, " +
                TABLE_ITEMS_CATEGORY_ID + " integer, " +
                TABLE_ITEMS_KEY_NAME + " text, " +
                TABLE_ITEMS_KEY_PRICE + " integer, " +
                TABLE_ITEMS_KEY_QUANTITY + " integer, " +
                TABLE_ITEMS_KEY_CHECKED + " integer)";
        sqLiteDatabase.execSQL(query2);

        String query3 = "Create table " + TABLE_CATEGORIES + " (" +
                TABLE_CATEGORIES_KEY_ID + " integer primary key, " +
                TABLE_CATEGORIES_KEY_NAME + " text, " +
                TABLE_CATEGORIES_KEY_ORDER + " integer)";
        sqLiteDatabase.execSQL(query3);

        String query4 = "Create table " + TABLE_DEFITEMS + " (" +
                TABLE_DEFITEMS_KEY_ID + " integer primary key, " +
                TABLE_DEFITEMS_KEY_NAME + " text, " +
                TABLE_DEFITEMS_KEY_ORDER + " integer)";
        sqLiteDatabase.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_LISTS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_ITEMS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_CATEGORIES);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_DEFITEMS);


        onCreate(sqLiteDatabase);
    }

    public void fill() {
        for (int i = 1; i <= 21; i++) {
            addList("List " + i, (int) (long) (System.currentTimeMillis() / 1000));
        }

        for (int i = 0; i <= 5; i++) {
            addItem(20, "Item of list 20 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(19, "Item of list 19 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(18, "Item of list 18 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(17, "Item of list 17 #" + i, 0, 1);
        }

        for (int i = 0; i <= 5; i++) {
            addItem(16, "Item of list 16 #" + i, 0, 1);
        }


        addCategory("Аксессуары", 10);
        addCategory("Алкоголь, табак", 20);
        addCategory("Бакалея", 30);
        addCategory("Замороженные продукты", 40);
        addCategory("Косметика, гигиена", 50);
        addCategory("Лекарства", 60);
        addCategory("Молочные продукты", 70);
        addCategory("Мясо, рыба, яйца", 80);
        addCategory("Напитки, соки", 90);
        addCategory("Обувь", 100);
        addCategory("Одежда", 110);
        addCategory("Товары для дома", 120);
        addCategory("Фрукты, овощи, соленья", 130);
        addCategory("Хлеб, выпечка, сладости", 140);
        addCategory("Электроника, бытовая техника", 150);
        addCategory("Другое", 9000);



        addDefItem("Батон", 10);
        addDefItem("Кола", 20);
        addDefItem("Колбаса", 30);
        addDefItem("Хлеб", 40);

        /*

         */
    }

    // ------------------------- Default items -----------------------------//

    public void addDefItem(String name, Integer order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_DEFITEMS_KEY_NAME, name);
        vals.put(TABLE_DEFITEMS_KEY_ORDER, order);

        db.insert(TABLE_DEFITEMS, null, vals);
        db.close();
    }

    public DefItems getDefItem(Integer defItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_DEFITEMS,
                new String[]{TABLE_DEFITEMS_KEY_ID, TABLE_DEFITEMS_KEY_NAME, TABLE_DEFITEMS_KEY_ORDER},
                TABLE_DEFITEMS_KEY_ID + "=" + defItemId,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        DefItems defItems = new DefItems();

        while (cursor.moveToNext()) {

            defItems.defItemId = cursor.getInt(0);
            defItems.defItemName = cursor.getString(1);
            defItems.defItemOrder = cursor.getInt(2);

        }
        cursor.close();

        return defItems;
    }

    public ArrayList<DefItems> searchDefItems(String name, Integer limit) {
        ArrayList items = new ArrayList<DefItems>();

        String query = "select * from " + TABLE_DEFITEMS +
                " where " + TABLE_DEFITEMS_KEY_NAME + " like '%" + name + "%'" + " limit " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            DefItems item = new DefItems();
            item.defItemId = cursor.getInt(0);
            item.defItemName = cursor.getString(1);
            item.defItemOrder = cursor.getInt(2);

            items.add(item);
        }

        cursor.close();

        return items;
    }

    // ------------------------- Categories -----------------------------//

    public void addCategory(String name, Integer order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_CATEGORIES_KEY_NAME, name);
        vals.put(TABLE_CATEGORIES_KEY_ORDER, order);

        db.insert(TABLE_CATEGORIES, null, vals);
        db.close();
    }

    public Categories getCategory(Integer categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES,
                new String[]{TABLE_CATEGORIES_KEY_ID, TABLE_CATEGORIES_KEY_NAME, TABLE_CATEGORIES_KEY_ORDER},
                TABLE_CATEGORIES_KEY_ID + "=" + categoryId,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        Categories categories = new Categories();

        while (cursor.moveToNext()) {

            categories.categoryId = cursor.getInt(0);
            categories.categoryName = cursor.getString(1);
            categories.categoryOrder = cursor.getInt(2);

        }
        cursor.close();

        return categories;
    }

    // ------------------------- Items -----------------------------//

    public void addItem(Integer listid, String name, Integer price, Integer quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_ITEMS_LIST_ID, listid);
        vals.put(TABLE_ITEMS_KEY_NAME, name);
        vals.put(TABLE_ITEMS_KEY_PRICE, price);
        vals.put(TABLE_ITEMS_KEY_QUANTITY, quantity);
        vals.put(TABLE_ITEMS_KEY_CHECKED, 0);

        db.insert(TABLE_ITEMS, null, vals);
        db.close();
    }

    public String getFirstItems(Integer listid, Integer limit) {
        String res = "";
        ArrayList items = new ArrayList<Items>();
        String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid + " limit " + limit;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.name = cursor.getString(2);
            item.quantity = cursor.getInt(4);
            item.checked = cursor.getInt(5);
            items.add(item);
            res += cursor.getString(2) + " ";
        }


        cursor.close();
        return res;
    }

    public ArrayList<Items> getItems(Integer listid) {
        ArrayList items = new ArrayList<Items>();

        String query = "select * from " + TABLE_ITEMS + " where " + TABLE_ITEMS_LIST_ID + " = " + listid;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Items item = new Items();
            item.id = cursor.getInt(0);
            item.listid = cursor.getInt(1);
            item.name = cursor.getString(2);
            item.price = cursor.getInt(3);
            item.quantity = cursor.getInt(4);
            item.checked = cursor.getInt(5);

            items.add(item);
        }

        cursor.close();

        return items;
    }


    // ------------------------- Lists -----------------------------//

    public void addList(String name, Integer date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TABLE_LISTS_KEY_NAME, name);
        vals.put(TABLE_LISTS_KEY_DATE, date);

        db.insert(TABLE_LISTS, null, vals);
        db.close();
    }

    public void addList(Lists li) {
        addList(li.listname, li.date);
    }

    public Lists getList(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_LISTS,
                new String[]{TABLE_LISTS_KEY_ID, TABLE_LISTS_KEY_NAME, TABLE_LISTS_KEY_DATE},
                TABLE_LISTS_KEY_ID + "=" + id,
                //new String[]{String.valueOf(id)},
                null, null, null, null);

        Lists li = new Lists();

        while (cursor.moveToNext()) {

            li.id = cursor.getInt(0);
            li.listname = cursor.getString(1);
            li.date = cursor.getInt(2);

        }
        cursor.close();

        return li;
    }

    public ArrayList<Lists> getAllLists() {
        ArrayList listitems = new ArrayList<Lists>();

        String query = "select * from " + TABLE_LISTS + " order by date desc";  //id desc date desc
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Lists li = new Lists(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );
            listitems.add(li);
        }

        cursor.close();

        return listitems;
    }
}