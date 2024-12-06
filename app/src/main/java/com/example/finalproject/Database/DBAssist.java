//package com.example.finalproject.Database;
//import android.content.Context;
//import android.database.Cursor;
//import android.widget.Toast;
//
//public class DBAssist {
//
//    //get my DBHelper class instantiated
//    private DBHelper db;
//
//    double currentIncome = 0;
//
//
//    //empty constructor
//    public DBAssist(Context context) {
//        // Initialize DBHelper with the provided context
//        db = new DBHelper(context);
//
//    }
//
//    //method to get the startingIncome value
//    public String getStartingIncome() {
//       String startingIncome = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            startingIncome = res.getString(1);
//        }
//        return startingIncome;
//    }
//
//
//
//    public String getIncome() {
//        String income = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            //we want to only get the second income which is the dynamical income!!
//            income = res.getString(2);
//        }
//        return income;
//    }
//
//
//
//
//    public String getRent() {
//        String rent = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            rent = res.getString(3);
//        }
//        return rent;
//    }
//
//
//    public String getUtilities() {
//        String utilities = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            utilities = res.getString(4);
//        }
//        return utilities;
//
//    }
//
//
//    public String getPhone() {
//        String phone = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            phone = res.getString(5);
//        }
//        return phone;
//    }
//
//    public String getInternet() {
//        String internet = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            internet = res.getString(6);
//        }
//        return internet;
//    }
//
//    public String getGym() {
//        String gym = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            gym = res.getString(7);
//        }
//        return gym;
//    }
//
//    public String getFood() {
//        String food = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            food = res.getString(8);
//        }
//
//        return food;
//    }
//
//
//    public String getGas() {
//        String gas = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            gas = res.getString(9);
//        }
//        return gas;
//    }
//
//
//    public String getInsurance() {
//        String insurance = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            insurance = res.getString(10);
//        }
//        return insurance;
//    }
//
//
//    public String getCar() {
//        String car = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            car = res.getString(11);
//        }
//        return car;
//    }
//
//
//    public String getStudent() {
//        String student = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            student = res.getString(12);
//        }
//        return student;
//    }
//
//
//    public String getCharity() {
//        String charity = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            charity = res.getString(13);
//        }
//        return charity;
//    }
//
//    public String getEmergency() {
//        String emergency = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            emergency = res.getString(14);
//        }
//        return emergency;
//    }
//
//    public String getSavings() {
//        String savings = "";
//        Cursor res = db.getData();
//        if (res != null && res.moveToNext()) {
//            savings = res.getString(15);
//        }
//        return savings;
//    }
//
//
//    //Now I want to be able to take whatever they select and the amount and subtract that from the Income
//
//    //first get the expense and the respective amount associated with it
//    public double getExpenseAllocatedAmount(String expense) {
//        double allocatedAmount = 0;
//
//        //no need to worry about non-zeros because in the database, it'll ensure a blank is 0.
//        switch (expense) {
//            case "Rent":
//                allocatedAmount = Double.parseDouble(getRent());
//                break;
//            case "Utilities":
//                allocatedAmount = Double.parseDouble(getUtilities());
//                break;
//            case "Phone":
//                allocatedAmount = Double.parseDouble(getPhone());
//                break;
//            case "Internet":
//                allocatedAmount = Double.parseDouble(getInternet());
//                break;
//            case "Gym":
//                allocatedAmount = Double.parseDouble(getGym());
//                break;
//            case "Food":
//                allocatedAmount = Double.parseDouble(getFood());
//                break;
//            case "Gas":
//                allocatedAmount = Double.parseDouble(getGas());
//                break;
//            case "Insurance":
//                allocatedAmount = Double.parseDouble(getInsurance());
//                break;
//            case "Car":
//                allocatedAmount = Double.parseDouble(getCar());
//                break;
//            case "Student":
//                allocatedAmount = Double.parseDouble(getStudent());
//                break;
//            case "Charity":
//                allocatedAmount = Double.parseDouble(getCharity());
//                break;
//            case "Emergency":
//                allocatedAmount = Double.parseDouble(getEmergency());
//                break;
//            case "Savings":
//                allocatedAmount = Double.parseDouble(getSavings());
//                break;
//            default:
//                allocatedAmount = 0;
//        }
//
//        return allocatedAmount;
//    }
//
//
//    //get the remaining amount after you spent and
//    public double spendAmount(String expenseName, double amountToSpend, Context context) {
//        //just grab the income
//        double income = getIncome().isEmpty() ? 0 : Double.parseDouble(getIncome());
//        //use the expense name to get the amount associated with it
//        double expenseBudget = getExpenseAllocatedAmount(expenseName);
//        //now subtract the amount spent from the income
//        double remainingAmount;
//        double defaultAmount = 0;
//
//
//        // Check if the budget for this expense is already depleted
//        if (expenseBudget <= 0) {
//            Toast.makeText(context, "Budget for " + expenseName + " is fully spent or no money set aside for this expense!", Toast.LENGTH_SHORT).show();
//            return income; // Return the remaining income without changes
//        }
//
//        // Check if the amount to spend exceeds the allocated budget for this expense
//        if (amountToSpend > expenseBudget) {
//            Toast.makeText(context, "Not enough funds allocated for " + expenseName + "!", Toast.LENGTH_SHORT).show();
//            return income; // Return the remaining income without changes
//        }
//
//        // Deduct the amount to spend from the total income
//        //double remainingIncome = income - amountToSpend;
//        income = income - amountToSpend;
//
//        return income;
//
//    }
//
//
//}