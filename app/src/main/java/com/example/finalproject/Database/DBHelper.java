package com.example.finalproject.Database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // Volatile instance to ensure thread safety
    //private static volatile DBHelper instance;

    public DBHelper(Context context) {
        super(context, "Budget.db", null, 1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Budget " +
                "(BudgetID INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
                "Savings TEXT, " +
                "Retirement TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Budget");

    }


//    public Boolean insertData(String income, String rent, String utilities, String phone, String internet, String gym, String food, String gas, String insurance, String carLoan, String studentLoan, String charity, String emergencyFund, String savings, String retirement) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        //primary key make it Autoincrement and it will always be 1.
//
//        contentValues.put("Income", income);
//        contentValues.put("Rent", rent);
//        contentValues.put("Utilities", utilities);
//        contentValues.put("Phone", phone);
//        contentValues.put("Internet", internet);
//        contentValues.put("Gym", gym);
//        contentValues.put("Food", food);
//        contentValues.put("Gas", gas);
//        contentValues.put("Insurance", insurance);
//        contentValues.put("CarLoan", carLoan);
//        contentValues.put("StudentLoan", studentLoan);
//        contentValues.put("Charity", charity);
//        contentValues.put("EmergencyFund", emergencyFund);
//        contentValues.put("Savings", savings);
//        contentValues.put("Retirement", retirement);
//        //conflict replace will ensure only one row exists, if an entyr with the same income key exists it will overwrite it.
//        long result = db.insertWithOnConflict("Budget", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//        Log.d("DB_Insert", "Insert Result: " + result);
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }



    public Boolean updateData (int BudgetID, String income, String rent, String utilities, String phone, String internet, String gym, String food, String gas, String insurance, String carLoan, String studentLoan, String charity, String emergencyFund, String savings, String retirement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

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
        contentValues.put("Retirement", retirement);

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


//    public Boolean deleteData (String income) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE BudgetID = ?", new String[]{"1"});
//        if (cursor.getCount() > 0) {
//            long result = db.delete("Budget", "BudgetID=?", new String[]{"1"});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//
//        } else {
//            return false;
//        }
//
//    }


    //method to update the database and set all the values back to blank.
    public boolean resetData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        contentValues.put("Retirement", "");
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



}