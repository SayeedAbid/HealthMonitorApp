package com.example.healthmonitorapp.ui.fragments;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {

    // Database and table names
    private static final String databaseName           = "Food_Table";
    private static final String databaseTableNotes     = "Food";
    private static final int databaseVersion           = 16970;

    // Database variables
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static final String databaseCreateTableNotes =
            "CREATE TABLE IF NOT EXISTS " + databaseTableNotes + " " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " food_id INT, " +
                    " food_title VARCHAR, " +
                    " food_cal DOUBLE, "+
                    " food_imageA VARCHAR, "+
                    " food_text VARCHAR);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, databaseName, null, databaseVersion);
        }

        //All Tables are created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary "  +
                        "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " fd_id INT, " +
                        " fd_date DATE, " +
                        " food_title VARCHAR, " +
                        " fd_meal_number INT);");
                db.execSQL("CREATE TABLE IF NOT EXISTS categories "  +
                        "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " category_id INT , " +
                        " category_title VARCHAR, " +
                        " category_parent_id INT, "+
                        " category_imageA VARCHAR, "+
                        " category_text VARCHAR);");
                db.execSQL(databaseCreateTableNotes);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS food_diary");
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS " + databaseTableNotes);
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

        }
    }

    //Open Database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //closes database
    public void close() {
        DBHelper.close();
    }


    // Insert record
    public void insertRecord(String table, String fields ,String data)
    {
        db.execSQL("INSERT INTO " + table + "(" + fields + ") VALUES (" + data +")" );

    }

    public int CountAllRecords(String table){
        Cursor mcount = db.rawQuery("SELECT COUNT(*) FROM " + table,null);
        mcount.moveToFirst();
        int count = mcount.getInt(0);
        mcount.close();
        return count;
    }

    // Retrieve
    public Cursor getAllRecordsFromNotes()
    {
        return db.query(databaseTableNotes, new String[]{
                "note_id",
                "note_title",
                "note_text"
        }, null, null, null, null, null, null);
    }
    public Cursor getAllRecordsFromNotesListView()
    {
        return db.query(databaseTableNotes, new String[]{
                "note_id AS _id",
                "note_title",
                "note_text"
        }, null, null, null, null, null, null);
    }
    // Retrieves a particular record
    public Cursor select(String table,String fields[],String primarykey, String rowId) throws SQLException {
        Cursor mCursor = db.query(table, fields, primarykey+ "=" + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // Select
    public Cursor select(String table, String[] fields) throws SQLException
    {
        Cursor mCursor = db.query(table, fields, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor select(String table, String[] fields, String whereClause, String whereCondition, String orderBy, String OrderMethod) throws SQLException
    {
        Cursor mCursor = null;
        if(whereClause.equals("")) {
            // We dont want to se where
            mCursor = db.query(table, fields, null, null, null, null, orderBy + " " + OrderMethod, null);
        }
        else {
            mCursor = db.query(table, fields, whereClause + "=" + whereCondition, null, null, null, orderBy + " " + OrderMethod, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public String quoteSmart(String value){
        // Is numeric?
        boolean isNumeric = false;
        try {
            double myDouble = Double.parseDouble(value);
            isNumeric = true;
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if(isNumeric == false){
            // Escapes special characters in a string for use in an SQL statement
            if (value != null && value.length() > 0) {
                value = value.replace("\\", "\\\\");
                value = value.replace("'", "\\'");
                value = value.replace("\0", "\\0");
                value = value.replace("\n", "\\n");
                value = value.replace("\r", "\\r");
                value = value.replace("\"", "\\\"");
                value = value.replace("\\x1a", "\\Z");
            }
        }

        value = "'" + value + "'";

        return value;
    }
    public double quoteSmart(double value) { return value; }
    public int quoteSmart(int value) { return value; }
    public long quoteSmart(long value) { return value; }




    // Truncate
    public int truncateNotes()
    {
        return db.delete(databaseTableNotes, "1", null);
    }

    public boolean update(String table, String primaryKey, long rowId, String field, String value) {
        // Remove first and last value of value
        value = value.substring(1, value.length()-1); // removes ' after running quote smart

        ContentValues args = new ContentValues();
        args.put(field, value);
        return db.update(table, args, primaryKey + "=" + rowId, null) > 0;
    }
    public boolean update(String table, String primaryKey, long rowId, String field, double value) {
        ContentValues args = new ContentValues();
        args.put(field, value);
        return db.update(table, args, primaryKey + "=" + rowId, null) > 0;
    }
    public boolean update(String table, String primaryKey, long rowId, String field, int value) {
        ContentValues args = new ContentValues();
        args.put(field, value);
        return db.update(table, args, primaryKey + "=" + rowId, null) > 0;
    }

    /* 12 Delete ----------------------------------------------------------------- */
    // Delete a particular record
    public int delete(String table, String primaryKey, long rowID) throws SQLException {
        return db.delete(table, primaryKey + "=" + rowID, null);
    }


}
