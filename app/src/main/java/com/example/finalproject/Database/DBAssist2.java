package com.example.finalproject.Database;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

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


    /*
    Now I want to be able to take whatever they select and the amount and subtract that from the Income
    first get the expense and the respective amount associated with it
     */
    public double getExpenseAllocatedAmount(String userID, String expense) {
        double allocatedAmount = 0;

        //no need to worry about non-zeros because in the database, it'll ensure a blank is 0.
        switch (expense) {
            case "Rent":
                allocatedAmount = Double.parseDouble(getRent(userID));
                break;
            case "Utilities":
                allocatedAmount = Double.parseDouble(getUtilities(userID));
                break;
            case "Phone":
                allocatedAmount = Double.parseDouble(getPhone(userID));
                break;
            case "Internet":
                allocatedAmount = Double.parseDouble(getInternet(userID));
                break;
            case "Gym":
                allocatedAmount = Double.parseDouble(getGym(userID));
                break;
            case "Food":
                allocatedAmount = Double.parseDouble(getFood(userID));
                break;
            case "Gas":
                allocatedAmount = Double.parseDouble(getGas(userID));
                break;
            case "Insurance":
                allocatedAmount = Double.parseDouble(getInsurance(userID));
                break;
            case "Car":
                allocatedAmount = Double.parseDouble(getCar(userID));
                break;
            case "Student":
                allocatedAmount = Double.parseDouble(getStudent(userID));
                break;
            case "Charity":
                allocatedAmount = Double.parseDouble(getCharity(userID));
                break;
            case "Emergency":
                allocatedAmount = Double.parseDouble(getEmergency(userID));
                break;
            case "Savings":
                allocatedAmount = Double.parseDouble(getSavings(userID));
                break;
            default:
                allocatedAmount = 0;
        }
        return allocatedAmount;
    }


    //get the remaining income amount after you spent
    public double spendAmount(String userID, String expenseName, double amountToSpend, Context context) {
        //just grab the income
        double income = getIncome(userID).isEmpty() ? 0 : Double.parseDouble(getIncome(userID));
        //use the expense name to get the amount associated with it
        double expenseBudget = getExpenseAllocatedAmount(userID, expenseName);

        // Check if the budget for this expense is already depleted
        if (expenseBudget <= 0) {
            Toast.makeText(context, "Budget for " + expenseName + " is fully spent or no money set aside for this expense!", Toast.LENGTH_SHORT).show();
            return income; // Return the remaining income without changes
        }

        // Check if the amount to spend exceeds the allocated budget for this expense
        if (amountToSpend > expenseBudget) {
            Toast.makeText(context, "Not enough funds allocated for " + expenseName + "!", Toast.LENGTH_SHORT).show();
            return income; // Return the remaining income without changes
        }

        // Deduct the amount to spend from the total income
        //double remainingIncome = income - amountToSpend;
        income = income - amountToSpend;

        return income;

    }
}