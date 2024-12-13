package com.example.finalproject.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper2 extends SQLiteOpenHelper {

    //declare my variables.
    private static DBHelper2 instance;
    private static final String DATABASE_NAME = "Budget.db";
    private static final int DATABASE_VERSION = 1;


    //class constructor to get the database name and version
    public DBHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Budget table if it doesn't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS Budget " +
                "(BudgetID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserID TEXT, " +  // Add UserID field for Firebase user association
                "IncomeStart TEXT, " +
                "Income TEXT, " +
                "Rent TEXT, " +
                "Utilities TEXT, " +
                "Phone TEXT, " +
                "Internet TEXT, " +
                "Gym TEXT, " +
                "Food TEXT, " +
                "Gas TEXT, " +
                "Insurance TEXT, " +
                "CarLoan TEXT, " +
                "StudentLoan TEXT, " +
                "Charity TEXT, " +
                "EmergencyFund TEXT, " +
                "Savings TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Handle database version upgrades here, so drop th table if it already exists and create it again.
        db.execSQL("DROP TABLE IF EXISTS Budget");
        onCreate(db);

    }

    /*
    Method to insert new data into the table. Accepts all string parameters.
     */
    public Boolean insertData(String userID, String incomeStart, String income, String rent, String utilities, String phone, String internet,
                              String gym, String food, String gas, String insurance, String carLoan, String studentLoan,
                              String charity, String emergencyFund, String savings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add the UserID to ContentValues, this userID will be coming from the unique ID firebase authentication gives each user.
        contentValues.put("UserID", userID);
        // Insert user-provided values (from UI or input fields)
        contentValues.put("IncomeStart", incomeStart);
        contentValues.put("Income", income);
        contentValues.put("Rent", rent);
        contentValues.put("Utilities", utilities);
        contentValues.put("Phone", phone);
        contentValues.put("Internet", internet);
        contentValues.put("Gym", gym);
        contentValues.put("Food", food);
        contentValues.put("Gas", gas);
        contentValues.put("Insurance", insurance);
        contentValues.put("CarLoan", carLoan);
        contentValues.put("StudentLoan", studentLoan);
        contentValues.put("Charity", charity);
        contentValues.put("EmergencyFund", emergencyFund);
        contentValues.put("Savings", savings);

        // Insert into the database, use CONFLICT_REPLACE to ensure only one budget per UserID
        long result = db.insertWithOnConflict("Budget", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        //Log the result for debugging purposes
        Log.d("DB_Insert", "Insert Result: " + result);

        //if our result returns a -1 which is impossible unless it failed.
        if (result == -1) {
           //data insertion failed
            return false;
        }
        else
        {
            //data insertion succeeded
            return true;
        }
    }


    /*
    Method to update pre-existing data entries, accepts all String parameters.
     */
    public Boolean updateData(String userID, String income, String rent, String utilities, String phone, String internet, String gym, String food, String gas, String insurance, String carLoan, String studentLoan, String charity, String emergencyFund, String savings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Update all of these values
        contentValues.put("Income", income);
        contentValues.put("Rent", rent);
        contentValues.put("Utilities", utilities);
        contentValues.put("Phone", phone);
        contentValues.put("Internet", internet);
        contentValues.put("Gym", gym);
        contentValues.put("Food", food);
        contentValues.put("Gas", gas);
        contentValues.put("Insurance", insurance);
        contentValues.put("CarLoan", carLoan);
        contentValues.put("StudentLoan", studentLoan);
        contentValues.put("Charity", charity);
        contentValues.put("EmergencyFund", emergencyFund);
        contentValues.put("Savings", savings);

        // Query to check if the user has a budget record in the database.
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE userID = ?", new String[]{userID});
        //if there is actually an entry that the cursor can find and sift through.
        if (cursor.getCount() > 0) {
            // User already has a budget, update it
            long result = db.update("Budget", contentValues, "userID=?", new String[]{userID});
            if (result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            //user doesn't have a budget, return false
            return false;
        }
    }


    /*
    Method to delete pre-existing data in the database. Need to have the user's firebase ID to delete their data.
     */
    public Boolean deleteData(String userID) {
        //get the writable database first
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the row based on the userID
        int result = db.delete("Budget", "userID=?", new String[]{userID});

        // If the result is greater than 0, it means the deletion was successful
        if (result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /*
    Method that does use a Cursor to get all the data.
     */
    public Cursor getData(String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //get all the data and return the cursor.
        Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE userID = ?", new String[]{userID});
        return cursor;
    }


    /*
    Method to get a specific column from the database, using userID and the column name.
     */
    public String getDatabyColumnName(String userID, String columnName) {
        //reference to my writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //declare the end result string variable.
        String endResult = "";

        //query to get value from that column name filtered by userID
        String query = "SELECT " + columnName + " FROM Budget WHERE userID = ?";
        //run the query and store it in a cursor
        Cursor cursor = db.rawQuery(query, new String[]{userID});

        //if  cursor is not null AND you can extract information from the first row
        if (cursor != null && cursor.moveToFirst()) {
            //now we need to get the column index for that colum name.
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex != -1)
            {
                //get value from the columnIndex.
                endResult = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return endResult;
    }
}