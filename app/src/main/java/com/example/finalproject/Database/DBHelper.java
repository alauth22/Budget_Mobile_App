package com.example.finalproject.Database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
Why everything works without Setters
Direct Database Updates:
Since you handle updates directly via DBHelper, there’s no intermediate state in DBAssist that needs to be set. This avoids redundancy.

Encapsulation of Logic:
The DBAssist class centralizes your database-related operations, making it easy to maintain and test without exposing unnecessary internals.

Dynamic Data Retrieval:
By querying the database each time, your application always works with the most up-to-date information, ensuring consistency.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Volatile instance to ensure thread safety
    //private static volatile DBHelper instance;

    public DBHelper(Context context) {
        super(context, "Budget.db", null, 1);

    }



    //add the incomeStart which will only be updated once and then you can refresh it
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Budget " +
                "(BudgetID INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        db.execSQL("DROP TABLE IF EXISTS Budget");

    }


//        public Boolean insertData(String income, String rent, String utilities, String phone, String internet, String gym, String food, String gas, String insurance, String carLoan, String studentLoan, String charity, String emergencyFund, String savings) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        //primary key make it Autoincrement and it will always be 1.
//            contentValues.put("IncomeStart", income);
//            contentValues.put("Income", income);
//            contentValues.put("Rent", rent);
//            contentValues.put("Utilities", utilities);
//            contentValues.put("Phone", phone);
//            contentValues.put("Internet", internet);
//            contentValues.put("Gym", gym);
//            contentValues.put("Food", food);
//            contentValues.put("Gas", gas);
//            contentValues.put("Insurance", insurance);
//            contentValues.put("CarLoan", carLoan);
//            contentValues.put("StudentLoan", studentLoan);
//            contentValues.put("Charity", charity);
//            contentValues.put("EmergencyFund", emergencyFund);
//            contentValues.put("Savings", savings);
//            //conflict replace will ensure only one row exists, if an entyr with the same income key exists it will overwrite it.
//            long result = db.insertWithOnConflict("Budget", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//            Log.d("DB_Insert", "Insert Result: " + result);
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//
//    }





    public Boolean initialData (int BudgetID, String income, String rent, String utilities, String phone, String internet, String gym, String food, String gas, String insurance, String carLoan, String studentLoan, String charity, String emergencyFund, String savings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("IncomeStart", income);
        //we want to update all of these like the same below:
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

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE BudgetID = ?", new String[]{String.valueOf(BudgetID)});
        if (cursor.getCount() > 0) {
            long result = db.update("Budget", contentValues, "BudgetID=?", new String[]{String.valueOf(BudgetID)});
            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {
            //budget ID is not found.
            return false;
        }

    }



    //this will for the SPENDING UPDATES ONLY NOT THE INCOME UPDATES
    public Boolean updateData (int BudgetID, String income, String rent, String utilities, String phone, String internet, String gym, String food, String gas, String insurance, String carLoan, String studentLoan, String charity, String emergencyFund, String savings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put("IncomeStart", income);

        //we want to update all of these like the same below:
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

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE BudgetID = ?", new String[]{String.valueOf(BudgetID)});
        if (cursor.getCount() > 0) {
            long result = db.update("Budget", contentValues, "BudgetID=?", new String[]{String.valueOf(BudgetID)});
            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {
            //budget ID is not found.
            return false;
        }

    }


    //method to update the database and set all the values back to blank.
    public boolean resetData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IncomeStart", "");
        contentValues.put("Income", "");
        contentValues.put("Rent", "");
        contentValues.put("Utilities", "");
        contentValues.put("Phone", "");
        contentValues.put("Internet", "");
        contentValues.put("Gym", "");
        contentValues.put("Food", "");
        contentValues.put("Gas", "");
        contentValues.put("Insurance", "");
        contentValues.put("CarLoan", "");
        contentValues.put("StudentLoan", "");
        contentValues.put("Charity", "");
        contentValues.put("EmergencyFund", "");
        contentValues.put("Savings", "");
        long result = db.update("Budget", contentValues, "BudgetID=?", new String[]{"1"});
        Log.d("DB_Insert", "Insert Result: " + result);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Budget", null);
        return cursor;
    }



    //get the specific column's
    public String getDatabyColumnName(String columnName)
    {

        //reference to my writable database
        SQLiteDatabase db = this.getWritableDatabase();
        String endResult = "";

        //write query to get value from that column name.
        String query = "SELECT " + columnName + " FROM Budget";
        Cursor cursor = db.rawQuery(query, null);

        //if the cursor does not equal null AND you can actually extract information from the first row
        if(cursor != null && cursor.moveToFirst())
        {
            int columnIndex = cursor.getColumnIndex(columnName);
            endResult = cursor.getString(columnIndex);

            cursor.close();
        }

        return endResult;

    }


}