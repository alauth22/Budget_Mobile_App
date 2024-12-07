package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();
    String userEmail = auth.getCurrentUser().getEmail();

    EditText  Income, Rent, Utilities, Phone, Internet, Gym, Food, Gas, Insurance, CarLoan, StudentLoan, Charity, EmergencyFund, Savings, MoreIncome;
    TextView BudgetID;
    Button addButton, updateButton, refreshButton, viewButton, ADD;

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
            RotateSideAnimate rotateSideAnimate = new RotateSideAnimate(arrow1);
            finish();
        });

        //because no buttons have been clicked on, we are going to automatically set the MoreIncome to disabled.


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
        viewButton = findViewById(R.id.View);
        addButton = findViewById(R.id.AddButton);


        //these are my checks to ensure that the user cannot just click on buttons here and there.
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





//        ADD.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                String IncomeTXT = Income.getText().toString();
//                String RentTXT = Rent.getText().toString();
//                String UtilitiesTXT = Utilities.getText().toString();
//                String PhoneTXT = Phone.getText().toString();
//                String InternetTXT = Internet.getText().toString();
//                String GymTXT = Gym.getText().toString();
//                String FoodTXT = Food.getText().toString();
//                String GasTXT = Gas.getText().toString();
//                String InsuranceTXT = Insurance.getText().toString();
//                String CarLoanTXT = CarLoan.getText().toString();
//                String StudentLoanTXT = StudentLoan.getText().toString();
//                String CharityTXT = Charity.getText().toString();
//                String EmergencyFundTXT = EmergencyFund.getText().toString();
//                String SavingsTXT = Savings.getText().toString();
//                Boolean checkinsertdata = db.insertData(IncomeTXT, RentTXT, UtilitiesTXT, PhoneTXT, InternetTXT, GymTXT, FoodTXT, GasTXT, InsuranceTXT, CarLoanTXT, StudentLoanTXT, CharityTXT, EmergencyFundTXT, SavingsTXT);
//                if (checkinsertdata == true)
//                {
//                    Toast.makeText(BudgetItems.this, "New Entry ", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(BudgetItems.this, "New Entry failed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });




        //add the new record now in the budget db.
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

                if (StartingIncomeTXT.isEmpty()) {
                    if (dbAssist.getIncome(userID).isEmpty()) {
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


                //income this is getting it
                if (IncomeTXT.isEmpty()) {

                    if (dbAssist.getIncome(userID).isEmpty()) {
                        IncomeTXT = "0";

                    } else
                    {
                        IncomeTXT = dbAssist.getIncome(userID);
                    }

                }
                else
                {
                    IncomeTXT = Income.getText().toString();
                }

                //rent
                if (RentTXT.isEmpty()) {
                    if (dbAssist.getRent(userID).isEmpty()) {
                        RentTXT = "0";
                    } else {
                        RentTXT = dbAssist.getRent(userID);
                    }
                } else {
                    RentTXT = Rent.getText().toString();
                }


                //Utilities
                if (UtilitiesTXT.isEmpty()) {
                    if (dbAssist.getUtilities(userID).isEmpty()) {
                        UtilitiesTXT = "0";
                    } else {
                        UtilitiesTXT = dbAssist.getUtilities(userID);
                    }
                } else {
                    UtilitiesTXT = Utilities.getText().toString();
                }


                //phone
                if (PhoneTXT.isEmpty()) {
                    if (dbAssist.getPhone(userID).isEmpty()) {
                        PhoneTXT = "0";
                    } else {
                        PhoneTXT = dbAssist.getPhone(userID);
                    }
                } else {
                    PhoneTXT = Phone.getText().toString();
                }


                if (InternetTXT.isEmpty()) {
                    if (dbAssist.getInternet(userID).isEmpty()) {
                        InternetTXT = "0";
                    } else {
                        InternetTXT = dbAssist.getInternet(userID);
                    }
                } else {
                    InternetTXT = Internet.getText().toString();
                }


                if (GymTXT.isEmpty()) {
                    if (dbAssist.getGym(userID).isEmpty()) {
                        GymTXT = "0";
                    } else {
                        GymTXT = dbAssist.getGym(userID);
                    }
                } else {
                    GymTXT = Gym.getText().toString();
                }


                if (FoodTXT.isEmpty()) {
                    if (dbAssist.getFood(userID).isEmpty()) {
                        FoodTXT = "0";
                    } else {
                        FoodTXT = dbAssist.getFood(userID);
                    }
                } else {
                    FoodTXT = Food.getText().toString();
                }

                if (GasTXT.isEmpty()) {
                    if (dbAssist.getGas(userID).isEmpty()) {
                        GasTXT = "0";
                    } else {
                        GasTXT = dbAssist.getGas(userID);
                    }
                } else {
                    GasTXT = Gas.getText().toString();
                }

                if (InsuranceTXT.isEmpty()) {
                    if (dbAssist.getInsurance(userID).isEmpty()) {
                        InsuranceTXT = "0";
                    } else {
                        InsuranceTXT = dbAssist.getInsurance(userID);
                    }
                } else {
                    InsuranceTXT = Insurance.getText().toString();
                }


                if (CarLoanTXT.isEmpty()) {
                    if (dbAssist.getCar(userID).isEmpty()) {
                        CarLoanTXT = "0";
                    } else {
                        CarLoanTXT = dbAssist.getCar(userID);
                    }
                } else {
                    CarLoanTXT = CarLoan.getText().toString();
                }

                if (StudentLoanTXT.isEmpty()) {
                    if (dbAssist.getStudent(userID).isEmpty()) {
                        StudentLoanTXT = "0";
                    } else {
                        StudentLoanTXT = dbAssist.getStudent(userID);
                    }
                } else {
                    StudentLoanTXT = StudentLoan.getText().toString();
                }

                if (CharityTXT.isEmpty()) {
                    if (dbAssist.getCharity(userID).isEmpty()) {
                        CharityTXT = "0";
                    } else {
                        CharityTXT = dbAssist.getCharity(userID);
                    }
                } else {
                    CharityTXT = Charity.getText().toString();
                }


                if (EmergencyFundTXT.isEmpty()) {
                    if (dbAssist.getEmergency(userID).isEmpty()) {
                        EmergencyFundTXT = "0";
                    } else {
                        EmergencyFundTXT = dbAssist.getEmergency(userID);
                    }
                } else {
                    EmergencyFundTXT = EmergencyFund.getText().toString();
                }


                if (SavingsTXT.isEmpty()) {
                    if (dbAssist.getSavings(userID).isEmpty()) {
                        SavingsTXT = "0";
                    } else {
                        SavingsTXT = dbAssist.getSavings(userID);
                    }
                } else {
                    SavingsTXT = Savings.getText().toString();
                }




                //now ensure that the values do not overexceed the income
                //convert everything to double NOT USED YET
                double StartingIncome = Double.parseDouble(StartingIncomeTXT);
                //maybe I should put these all into an array and do for loops later to clean it up.
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


                double total = rent + utilities + phone + internet + gym + food + gas + insurance + car + student + charity + emergency + savings;
                if (income == 0)
                {
                    Toast.makeText(BudgetItems.this, "Please enter an income first to add a budget.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (total > income) {
                    Toast.makeText(BudgetItems.this, "Expense total  of $ " + total + " exceeds your income of $" + income, Toast.LENGTH_LONG).show();
                    return;
                }
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
                    if (checkupdatedata == true) {
                        Toast.makeText(BudgetItems.this, "Budget Added ", Toast.LENGTH_SHORT).show();
                    }
                    //else if it is not then send a bad message
                    else {
                        Toast.makeText(BudgetItems.this, "Budget Not Added.", Toast.LENGTH_SHORT).show();
                    }

                }

                //disable the button after it's clicked
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

                // Fetch input values

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


                // Handle MoreIncome input
                if (MoreIncomeTXT.isEmpty()) {
                    MoreIncomeTXT = "0";
                }

                // Convert final values to doubles
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

                // Calculate final income and total expenses
                double incomeFinal = income + moreIncome;
                double total = rentFINAL + utilitiesFINAL + phoneFINAL + internetFINAL + gymFINAL + foodFINAL + gasFINAL
                        + insuranceFINAL + carFINAL + studentFINAL + charityFINAL + emergencyFINAL + savingsFINAL;

                // Check if total exceeds income
                if (total > incomeFinal) {
                    Toast.makeText(BudgetItems.this, "Total exceeds income. Please adjust values.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the database
                Boolean checkupdatedata = db.updateData(userID, String.valueOf(incomeFinal), String.valueOf(rentFINAL), String.valueOf(utilitiesFINAL), String.valueOf(phoneFINAL),
                        String.valueOf(internetFINAL), String.valueOf(gymFINAL), String.valueOf(foodFINAL), String.valueOf(gasFINAL), String.valueOf(insuranceFINAL),
                        String.valueOf(carFINAL), String.valueOf(studentFINAL), String.valueOf(charityFINAL), String.valueOf(emergencyFINAL), String.valueOf(savingsFINAL));

                if (checkupdatedata) {
                    Toast.makeText(BudgetItems.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BudgetItems.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            // Helper methods
            private String handleEmptyInput(String inputTXT, String dbValue) {
                if (inputTXT.isEmpty()) {
                    return dbValue.isEmpty() ? "0" : dbValue;
                }
                return inputTXT;
            }

            private double parseDoubleOrZero(String value) {
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });



        //DELETE BUTTON!!!!
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetItems.this);
                builder.setMessage("Are you sure want to reset all entries and begin a new budget?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Proceed with the database action
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
                                } else {
                                    Toast.makeText(BudgetItems.this, "All Entries Are Not Reset!", Toast.LENGTH_SHORT).show();

                                }
                                //enable the button once we refresh the database.
                                addButton.setEnabled(true);
                                updateButton.setEnabled(false);
                                Income.setEnabled(true);
                                MoreIncome.setEnabled(false);

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.show();


            }
        });






        //let's do practice sending a message when the button is clicked. YES or NO to proceed.
        //THIS WILL NOT STAY THIS IS MY TEST!!!!
        viewButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //sendDBValuesToAnotherActivity();

                                // Proceed with the database action
                                Cursor res = db.getData(userID);
                                if (res.getCount() == 0) {
                                    Toast.makeText(BudgetItems.this, "No entry exists", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                StringBuilder buffer = new StringBuilder();
                                while (res.moveToNext()) {
                                    //this is my check to ensure I am only using 1 ID.
                                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                                    buffer.append("UserID: ").append(res.getString(1)).append("\n");
                                    buffer.append("Starting Income: ").append(res.getString(2)).append("\n");
                                    buffer.append("Income: ").append(res.getString(3)).append("\n");
                                    buffer.append("Rent : ").append(res.getString(4)).append("\n");
                                    buffer.append("Utilities : ").append(res.getString(5)).append("\n");
                                    buffer.append("Phone : ").append(res.getString(6)).append("\n");
                                    buffer.append("Internet : ").append(res.getString(7)).append("\n");
                                    buffer.append("Gym : ").append(res.getString(8)).append("\n");
                                    buffer.append("Food : ").append(res.getString(9)).append("\n");
                                    buffer.append("Gas : ").append(res.getString(10)).append("\n");
                                    buffer.append("Insurance : ").append(res.getString(11)).append("\n");
                                    buffer.append("Car Loan : ").append(res.getString(12)).append("\n");
                                    buffer.append("Student Loan : ").append(res.getString(13)).append("\n");
                                    buffer.append("Charity : ").append(res.getString(14)).append("\n");
                                    buffer.append("Emergency Fund : ").append(res.getString(15)).append("\n");
                                    buffer.append("Savings : ").append(res.getString(16)).append("\n");
                                }
                                buffer.append("UserEmail: " + userEmail);

                                //this is also my TESTING
                                AlertDialog.Builder detailsbuilder = new AlertDialog.Builder(BudgetItems.this);
                                detailsbuilder.setCancelable(true);
                                detailsbuilder.setTitle("User Details");
                                detailsbuilder.setMessage(buffer.toString());
                                detailsbuilder.show();



            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}