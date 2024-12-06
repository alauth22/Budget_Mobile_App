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


    //constructor
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
        // Handle database version upgrades here
        db.execSQL("DROP TABLE IF EXISTS Budget");
        onCreate(db);

    }


    public Boolean insertData(String userID, String incomeStart, String income, String rent, String utilities, String phone, String internet,
                              String gym, String food, String gas, String insurance, String carLoan, String studentLoan,
                              String charity, String emergencyFund, String savings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add the UserID to ContentValues
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

        Log.d("DB_Insert", "Insert Result: " + result);

        if (result == -1) {
            return false; // Error occurred
        } else {
            return true; // Data inserted successfully
        }
    }



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

        // Query to check if the user has a budget record
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE userID = ?", new String[]{userID});
        if (cursor.getCount() > 0) {
            // User already has a budget, update it
            long result = db.update("Budget", contentValues, "userID=?", new String[]{userID});
            if (result == -1) {
                return false; // Update failed
            } else {
                return true; // Update succeeded
            }
        } else {
            // User doesn't have a budget, return false
            return false;
        }
    }


    public Boolean deleteData(String userID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the row based on the userID
        int result = db.delete("Budget", "userID=?", new String[]{userID});

        // If the result is greater than 0, it means the deletion was successful
        if (result > 0) {
            return true;  // Deletion successful
        } else {
            return false; // Deletion failed
        }
    }


    public Cursor getData(String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Use parameterized queries to prevent SQL injection
        Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE userID = ?", new String[]{userID});
        return cursor;
    }



    // Get data from a specific column filtered by userID
    public String getDatabyColumnName(String userID, String columnName) {
        // Reference to my writable database
        SQLiteDatabase db = this.getWritableDatabase();
        String endResult = "";

        // Write query to get value from that column name filtered by userID
        String query = "SELECT " + columnName + " FROM Budget WHERE userID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userID});

        // If the cursor is not null AND you can extract information from the first row
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(columnName);
            if (columnIndex != -1) { // Ensure the column exists
                endResult = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        return endResult;
    }



}
