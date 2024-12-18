package com.example.finalproject.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.Animation.RotateSideAnimate;
import com.example.finalproject.Database.DBAssist2;
import com.example.finalproject.Database.DBHelper2;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class BudgetItems extends AppCompatActivity {

    //declare variables
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();
    String userEmail = auth.getCurrentUser().getEmail();
    EditText  Income, Rent, Utilities, Phone, Internet, Gym, Food, Gas, Insurance, CarLoan, StudentLoan, Charity, EmergencyFund, Savings, MoreIncome;
    TextView BudgetID;
    Button addButton, updateButton, refreshButton, viewButton;
    DBHelper2 db;
    DBAssist2 dbAssist = new DBAssist2(this);


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budgetitems);

        //declare the database.
        db = new DBHelper2(this);

        //arrow icon
        ImageView arrow1 = findViewById(R.id.backArrow3);
        arrow1.setOnClickListener(v -> {
            new RotateSideAnimate(arrow1);
            finish();
        });


        //get my edit text fields
        Income = findViewById(R.id.Income);
        Rent = findViewById(R.id.RentMortgage);
        Food = findViewById(R.id.Food);
        Gas = findViewById(R.id.Gas);
        Utilities = findViewById(R.id.Utilities);
        Insurance = findViewById(R.id.Insurance);
        Charity = findViewById(R.id.Charity);
        Phone = findViewById(R.id.Phone);
        Internet = findViewById(R.id.Internet);
        Gym = findViewById(R.id.Gym);
        CarLoan = findViewById(R.id.CarLoan);
        StudentLoan = findViewById(R.id.StudentLoan);
        EmergencyFund = findViewById(R.id.EmergencyFund);
        Savings = findViewById(R.id.Savings);
        MoreIncome = findViewById(R.id.MoreIncome);


        //my buttons
        updateButton = findViewById(R.id.Update);
        refreshButton = findViewById(R.id.Refresh);
        addButton = findViewById(R.id.AddButton);


        //these are my checks to ensure that the user cannot just click on buttons anywhere
        String incomePresent = dbAssist.getIncome(userID);
        if(incomePresent.isEmpty()){
            addButton.setEnabled(true);
            updateButton.setEnabled(false);
            Income.setEnabled(true);
            MoreIncome.setEnabled(false);
        }
        else
        {
            addButton.setEnabled(false);
            updateButton.setEnabled(true);
            Income.setEnabled(false);
            MoreIncome.setEnabled(true);
        };


        //add the new record now in the budget db with the add click button.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the starting income too for the database column index 2
                String StartingIncomeTXT = Income.getText().toString();
                String IncomeTXT = Income.getText().toString();
                String RentTXT = Rent.getText().toString();
                String UtilitiesTXT = Utilities.getText().toString();
                String PhoneTXT = Phone.getText().toString();
                String InternetTXT = Internet.getText().toString();
                String GymTXT = Gym.getText().toString();
                String FoodTXT = Food.getText().toString();
                String GasTXT = Gas.getText().toString();
                String InsuranceTXT = Insurance.getText().toString();
                String CarLoanTXT = CarLoan.getText().toString();
                String StudentLoanTXT = StudentLoan.getText().toString();
                String CharityTXT = Charity.getText().toString();
                String EmergencyFundTXT = EmergencyFund.getText().toString();
                String SavingsTXT = Savings.getText().toString();

                //if nothing has been entered for that column then set it to 0.
                if (StartingIncomeTXT.isEmpty()) {
                    if (dbAssist.getIncome(userID).isEmpty()) {
                        //if it is empty, set it automatically to zero.
                        StartingIncomeTXT = "0";
                    }
                    else
                    {
                        StartingIncomeTXT = dbAssist.getStartingIncome(userID);
                    }
                }
                else
                {
                    StartingIncomeTXT = Income.getText().toString();
                }


                //income
                //if it is left empty by user.
                if (IncomeTXT.isEmpty()) {
                    //check to see if db is empty
                    if (dbAssist.getIncome(userID).isEmpty()) {
                        //if it is empty, set it automatically to zero.
                        IncomeTXT = "0";

                    }
                    else
                    {
                        //get the pre-existing value from db
                        IncomeTXT = dbAssist.getIncome(userID);
                    }

                }
                else
                {
                    //if the edittext field is NOT empty, grab that value from the user.
                    IncomeTXT = Income.getText().toString();
                }


                //rent
                if (RentTXT.isEmpty()) {
                    if (dbAssist.getRent(userID).isEmpty()) {
                        RentTXT = "0";
                    }
                    else
                    {
                        RentTXT = dbAssist.getRent(userID);
                    }
                }
                else
                {
                    RentTXT = Rent.getText().toString();
                }


                //Utilities
                if (UtilitiesTXT.isEmpty()) {
                    if (dbAssist.getUtilities(userID).isEmpty()) {
                        UtilitiesTXT = "0";
                    }
                    else
                    {
                        UtilitiesTXT = dbAssist.getUtilities(userID);
                    }
                }
                else
                {
                    UtilitiesTXT = Utilities.getText().toString();
                }


                //phone
                if (PhoneTXT.isEmpty()) {
                    if (dbAssist.getPhone(userID).isEmpty()) {
                        PhoneTXT = "0";
                    }
                    else
                    {
                        PhoneTXT = dbAssist.getPhone(userID);
                    }
                }
                else
                {
                    PhoneTXT = Phone.getText().toString();
                }


                if (InternetTXT.isEmpty()) {
                    if (dbAssist.getInternet(userID).isEmpty()) {
                        InternetTXT = "0";
                    }
                    else
                    {
                        InternetTXT = dbAssist.getInternet(userID);
                    }
                }
                else
                {
                    InternetTXT = Internet.getText().toString();
                }


                if (GymTXT.isEmpty()) {
                    if (dbAssist.getGym(userID).isEmpty()) {
                        GymTXT = "0";
                    }
                    else
                    {
                        GymTXT = dbAssist.getGym(userID);
                    }
                }
                else
                {
                    GymTXT = Gym.getText().toString();
                }


                if (FoodTXT.isEmpty()) {
                    if (dbAssist.getFood(userID).isEmpty()) {
                        FoodTXT = "0";
                    }
                    else
                    {
                        FoodTXT = dbAssist.getFood(userID);
                    }
                }
                else
                {
                    FoodTXT = Food.getText().toString();
                }

                if (GasTXT.isEmpty()) {
                    if (dbAssist.getGas(userID).isEmpty()) {
                        GasTXT = "0";
                    }
                    else
                    {
                        GasTXT = dbAssist.getGas(userID);
                    }
                }
                else
                {
                    GasTXT = Gas.getText().toString();
                }

                if (InsuranceTXT.isEmpty()) {
                    if (dbAssist.getInsurance(userID).isEmpty()) {
                        InsuranceTXT = "0";
                    }
                    else
                    {
                        InsuranceTXT = dbAssist.getInsurance(userID);
                    }
                }
                else
                {
                    InsuranceTXT = Insurance.getText().toString();
                }


                if (CarLoanTXT.isEmpty()) {
                    if (dbAssist.getCar(userID).isEmpty()) {
                        CarLoanTXT = "0";
                    }
                    else
                    {
                        CarLoanTXT = dbAssist.getCar(userID);
                    }
                }
                else
                {
                    CarLoanTXT = CarLoan.getText().toString();
                }

                if (StudentLoanTXT.isEmpty()) {
                    if (dbAssist.getStudent(userID).isEmpty()) {
                        StudentLoanTXT = "0";
                    }
                    else
                    {
                        StudentLoanTXT = dbAssist.getStudent(userID);
                    }
                }
                else
                {
                    StudentLoanTXT = StudentLoan.getText().toString();
                }

                if (CharityTXT.isEmpty()) {
                    if (dbAssist.getCharity(userID).isEmpty()) {
                        CharityTXT = "0";
                    }
                    else
                    {
                        CharityTXT = dbAssist.getCharity(userID);
                    }
                }
                else
                {
                    CharityTXT = Charity.getText().toString();
                }


                if (EmergencyFundTXT.isEmpty()) {
                    if (dbAssist.getEmergency(userID).isEmpty()) {
                        EmergencyFundTXT = "0";
                    }
                    else
                    {
                        EmergencyFundTXT = dbAssist.getEmergency(userID);
                    }
                }
                else
                {
                    EmergencyFundTXT = EmergencyFund.getText().toString();
                }


                if (SavingsTXT.isEmpty()) {
                    if (dbAssist.getSavings(userID).isEmpty()) {
                        SavingsTXT = "0";
                    }
                    else
                    {
                        SavingsTXT = dbAssist.getSavings(userID);
                    }
                }
                else
                {
                    SavingsTXT = Savings.getText().toString();
                }


                //convert to double
                double income = Double.parseDouble(IncomeTXT);
                double rent = Double.parseDouble(RentTXT);
                double utilities = Double.parseDouble(UtilitiesTXT);
                double phone = Double.parseDouble(PhoneTXT);
                double internet = Double.parseDouble(InternetTXT);
                double gym = Double.parseDouble(GymTXT);
                double food = Double.parseDouble(FoodTXT);
                double gas = Double.parseDouble(GasTXT);
                double insurance = Double.parseDouble(InsuranceTXT);
                double car = Double.parseDouble(CarLoanTXT);
                double student = Double.parseDouble(StudentLoanTXT);
                double charity = Double.parseDouble(CharityTXT);
                double emergency = Double.parseDouble(EmergencyFundTXT);
                double savings = Double.parseDouble(SavingsTXT);

                //add total expenses
                double total = rent + utilities + phone + internet + gym + food + gas + insurance + car + student + charity + emergency + savings;

                //if the income equals 0 we need to have an income to even work
                if (income == 0)
                {
                    Toast.makeText(BudgetItems.this, "Please enter an income first to add a budget.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if the total exceeds the present income then send a message.
                else if (total > income)
                {
                    Toast.makeText(BudgetItems.this, "Expense total  of $ " + total + " exceeds your income of $" + income, Toast.LENGTH_LONG).show();
                    return;
                }
                //if the total does not equal the present income then send a message. This is when we cannot allocate the correct amount that will equal our budget.
                else if (total != income)
                {
                    Toast.makeText(BudgetItems.this, "Your expense total of $" + total + " does not equal your income of $" + income, Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    //we are inserting this now into the database
                    Boolean checkupdatedata = db.insertData(userID, StartingIncomeTXT, IncomeTXT, RentTXT, UtilitiesTXT, PhoneTXT, InternetTXT, GymTXT, FoodTXT, GasTXT, InsuranceTXT, CarLoanTXT, StudentLoanTXT, CharityTXT, EmergencyFundTXT, SavingsTXT);
                    //send a good message
                    if (checkupdatedata == true)
                    {
                        Toast.makeText(BudgetItems.this, "Budget Added ", Toast.LENGTH_SHORT).show();
                    }
                    //else if it is not then send a bad message
                    else
                    {
                        Toast.makeText(BudgetItems.this, "Budget Not Added.", Toast.LENGTH_SHORT).show();
                    }

                }

                //disable add button and enable update button
                addButton.setEnabled(false);
                updateButton.setEnabled(true);

                //immediately go to show activity
               finish();

            }

        });



        //UPDATE BUTTON
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the input values
                String RentTXT = Rent.getText().toString();
                String UtilitiesTXT = Utilities.getText().toString();
                String PhoneTXT = Phone.getText().toString();
                String InternetTXT = Internet.getText().toString();
                String GymTXT = Gym.getText().toString();
                String FoodTXT = Food.getText().toString();
                String GasTXT = Gas.getText().toString();
                String InsuranceTXT = Insurance.getText().toString();
                String CarLoanTXT = CarLoan.getText().toString();
                String StudentLoanTXT = StudentLoan.getText().toString();
                String CharityTXT = Charity.getText().toString();
                String EmergencyFundTXT = EmergencyFund.getText().toString();
                String SavingsTXT = Savings.getText().toString();
                String MoreIncomeTXT = MoreIncome.getText().toString();


                //handle MoreIncome input, this will be any additional income someone may have after a budget is created.
                if (MoreIncomeTXT.isEmpty()) {
                    MoreIncomeTXT = "0";
                }

                //convert final values to doubles, use the dbAssist methods to get those values for that userID.
                double income = parseDoubleOrZero(dbAssist.getIncome(userID));
                double rentCurrent = parseDoubleOrZero(dbAssist.getRent(userID));
                double rent = parseDoubleOrZero(RentTXT);

                double utilitiesCurrent = parseDoubleOrZero(dbAssist.getUtilities(userID));
                double utilities = parseDoubleOrZero(UtilitiesTXT);

                double phoneCurrent = parseDoubleOrZero(dbAssist.getPhone(userID));
                double phone = parseDoubleOrZero(PhoneTXT);

                double internetCurrent = parseDoubleOrZero(dbAssist.getInternet(userID));
                double internet = parseDoubleOrZero(InternetTXT);

                double gymCurrent = parseDoubleOrZero(dbAssist.getGym(userID));
                double gym = parseDoubleOrZero(GymTXT);

                double foodCurrent = parseDoubleOrZero(dbAssist.getFood(userID));
                double food = parseDoubleOrZero(FoodTXT);

                double gasCurrent = parseDoubleOrZero(dbAssist.getGas(userID));
                double gas = parseDoubleOrZero(GasTXT);

                double insuranceCurrent = parseDoubleOrZero(dbAssist.getInsurance(userID));
                double insurance = parseDoubleOrZero(InsuranceTXT);

                double carCurrent = parseDoubleOrZero(dbAssist.getCar(userID));
                double car = parseDoubleOrZero(CarLoanTXT);

                double studentCurrent = parseDoubleOrZero(dbAssist.getStudent(userID));
                double student = parseDoubleOrZero(StudentLoanTXT);

                double charityCurrent = parseDoubleOrZero(dbAssist.getCharity(userID));
                double charity = parseDoubleOrZero(CharityTXT);

                double emergencyCurrent = parseDoubleOrZero(dbAssist.getEmergency(userID));
                double emergency = parseDoubleOrZero(EmergencyFundTXT);

                double savingsCurrent = parseDoubleOrZero(dbAssist.getSavings(userID));
                double savings = parseDoubleOrZero(SavingsTXT);

                double moreIncome = parseDoubleOrZero(MoreIncomeTXT);


                double rentFINAL = rentCurrent + rent;
                double utilitiesFINAL = utilitiesCurrent + utilities;
                double phoneFINAL = phoneCurrent + phone;
                double internetFINAL = internetCurrent + internet;
                double gymFINAL = gymCurrent + gym;
                double foodFINAL = foodCurrent + food;
                double gasFINAL = gasCurrent + gas;
                double insuranceFINAL = insuranceCurrent + insurance;
                double carFINAL = carCurrent + car;
                double studentFINAL = studentCurrent + student;
                double charityFINAL = charityCurrent + charity;
                double emergencyFINAL = emergencyCurrent + emergency;
                double savingsFINAL = savingsCurrent + savings;

                //calculate final income and total expenses
                double incomeFinal = income + moreIncome;
                double total = rentFINAL + utilitiesFINAL + phoneFINAL + internetFINAL + gymFINAL + foodFINAL + gasFINAL
                        + insuranceFINAL + carFINAL + studentFINAL + charityFINAL + emergencyFINAL + savingsFINAL;

                //if the total exceeds income
                if (total > incomeFinal) {
                    Toast.makeText(BudgetItems.this, "Total exceeds income. Please adjust values.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //update the database with the proper database function
                Boolean checkupdatedata = db.updateData(userID, String.valueOf(incomeFinal), String.valueOf(rentFINAL), String.valueOf(utilitiesFINAL), String.valueOf(phoneFINAL),
                        String.valueOf(internetFINAL), String.valueOf(gymFINAL), String.valueOf(foodFINAL), String.valueOf(gasFINAL), String.valueOf(insuranceFINAL),
                        String.valueOf(carFINAL), String.valueOf(studentFINAL), String.valueOf(charityFINAL), String.valueOf(emergencyFINAL), String.valueOf(savingsFINAL));

                if (checkupdatedata)
                {
                    Toast.makeText(BudgetItems.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(BudgetItems.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
                }

                finish();
            }


            //helper method to convert string to double
            private double parseDoubleOrZero(String value) {
                try {
                    return Double.parseDouble(value);
                }
                catch
                (NumberFormatException e) {
                    return 0;
                }
            }
        });



        //DELETE BUTTON!!!!
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this is my alert message to ask the user if they indeed want to delete their budget.
                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetItems.this);

                builder.setMessage("Are you sure want to reset all entries and begin a new budget?")
                        .setCancelable(false)
                        //if yes then delete the database
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //call the delete function
                                Boolean checkresetdata = db.deleteData(userID);
                                if (checkresetdata == true) {
                                    Income.setText("");
                                    Rent.setText("");
                                    Utilities.setText("");
                                    Phone.setText("");
                                    Internet.setText("");
                                    Gym.setText("");
                                    Food.setText("");
                                    Gas.setText("");
                                    Insurance.setText("");
                                    CarLoan.setText("");
                                    StudentLoan.setText("");
                                    Charity.setText("");
                                    EmergencyFund.setText("");
                                    Savings.setText("");
                                    Toast.makeText(BudgetItems.this, "All Entries Are Reset!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(BudgetItems.this, "All Entries Are Not Reset!", Toast.LENGTH_SHORT).show();

                                }

                                //enable the add button once we refresh the database.
                                addButton.setEnabled(true);
                                //disable the update button once we refresh the database.
                                updateButton.setEnabled(false);
                                //allow the user to set an income
                                Income.setEnabled(true);
                                //prevent them from setting a moreincome value
                                MoreIncome.setEnabled(false);

                            }

                        })
                        //if no then cancel the dialog and let the user continue
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.show();
            }
        });

    }

    //resume to where you last were when you return to the activity.
    @Override
    protected void onResume() {
        super.onResume();

    }

}