package com.example.finalproject.Database;

import android.content.Context;
import android.database.Cursor;

public class DBAssist2 {

    //declare my variables
    private DBHelper2 db;

    //class constructor
    public DBAssist2(Context context) {
        //initialize DBHelper with the provided context
        db = new DBHelper2(context);

    }

    public String getStartingIncome(String userID) {
        String startingIncome = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            //we know that in the database, starting income is at index of 2.
            startingIncome = res.getString(2);
        }
        return startingIncome;
    }

    public String getIncome(String userID) {
        String income = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            //we want to only get the second income which is the dynamical income!!
            income = res.getString(3);
        }
        return income;
    }

    public String getRent(String userID) {
        String rent = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            rent = res.getString(4);
        }
        return rent;
    }

    public String getUtilities(String userID) {
        String utilities = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            utilities = res.getString(5);
        }
        return utilities;
    }

    public String getPhone(String userID) {
        String phone = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            phone = res.getString(6);
        }
        return phone;
    }

    public String getInternet(String userID) {
        String internet = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            internet = res.getString(7);
        }
        return internet;
    }

    public String getGym(String userID) {
        String gym = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            gym = res.getString(8);
        }
        return gym;
    }

    public String getFood(String userID) {
        String food = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            food = res.getString(9);
        }
        return food;
    }

    public String getGas(String userID) {
        String gas = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            gas = res.getString(10);
        }
        return gas;
    }

    public String getInsurance(String userID) {
        String insurance = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            insurance = res.getString(11);
        }
        return insurance;
    }

    public String getCar(String userID) {
        String car = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            car = res.getString(12);
        }
        return car;
    }

    public String getStudent(String userID) {
        String student = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            student = res.getString(13);
        }
        return student;
    }

    public String getCharity(String userID) {
        String charity = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            charity = res.getString(14);
        }
        return charity;
    }

    public String getEmergency(String userID) {
        String emergency = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            emergency = res.getString(15);
        }
        return emergency;
    }

    public String getSavings(String userID) {
        String savings = "";
        Cursor res = db.getData(userID);
        if (res != null && res.moveToNext()) {
            savings = res.getString(16);
        }
        return savings;
    }

}