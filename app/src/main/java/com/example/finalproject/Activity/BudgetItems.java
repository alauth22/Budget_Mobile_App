package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.Database.DBSingleton;
import com.example.finalproject.R;

public class BudgetItems extends AppCompatActivity {

    private MediaPlayer sound2;
    EditText  Income, Rent, Utilities, Phone, Internet, Gym, Food, Gas, Insurance, CarLoan, StudentLoan, Charity, EmergencyFund, Savings;
    TextView BudgetID;
    Button addButton, updateButton, refreshButton, viewButton, ADD;

    DBHelper db;

    DBAssist dbAssist = new DBAssist(this);

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budgetitems);

        // My singleton will return an instance of DBHelper.
        db = DBSingleton.getInstance(this);

        sound2 = MediaPlayer.create(this, R.raw.clickbutton);

        ImageView arrow1 = findViewById(R.id.backArrow3);
        arrow1.setOnClickListener(v -> {
            finish();
        });


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



        updateButton = findViewById(R.id.Update);
        refreshButton = findViewById(R.id.Refresh);
        viewButton = findViewById(R.id.View);
        addButton = findViewById(R.id.AddButton);

        //these are my checks to ensure that the user cannot just click on buttons here and there.
        String incomePresent = dbAssist.getStartingIncome();
        if(incomePresent.isEmpty()){
            addButton.setEnabled(true);
        }
        else
        {
            addButton.setEnabled(false);
        };

        //user cannot update their budget unless they have added one before.
        if(incomePresent.isEmpty()){
            updateButton.setEnabled(false);
        }
        else
        {
            updateButton.setEnabled(true);
        }




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




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound2.start();


                //get the starting income too for the database column index 1
                String StartingIncomeTXT = Income.getText().toString();
                //I don't think I need this....
                //get the values here.
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
                    if (dbAssist.getIncome().isEmpty()) {
                        StartingIncomeTXT = "0";
                    } else {
                        StartingIncomeTXT = dbAssist.getStartingIncome();
                    }
                } else {
                    StartingIncomeTXT = Income.getText().toString();
                }


                //income this is getting it
                if (IncomeTXT.isEmpty()) {

                    if (dbAssist.getIncome().isEmpty()) {
                        IncomeTXT = "0";

                    } else {
                        IncomeTXT = dbAssist.getIncome();
                    }

                } else {
                    IncomeTXT = Income.getText().toString();
                }

                //rent
                if (RentTXT.isEmpty()) {
                    if (dbAssist.getRent().isEmpty()) {
                        RentTXT = "0";
                    } else {
                        RentTXT = dbAssist.getRent();
                    }
                } else {
                    RentTXT = Rent.getText().toString();
                }


                //Utilities
                if (UtilitiesTXT.isEmpty()) {
                    if (dbAssist.getUtilities().isEmpty()) {
                        UtilitiesTXT = "0";
                    } else {
                        UtilitiesTXT = dbAssist.getUtilities();
                    }
                } else {
                    UtilitiesTXT = Utilities.getText().toString();
                }


                //phone
                if (PhoneTXT.isEmpty()) {
                    if (dbAssist.getPhone().isEmpty()) {
                        PhoneTXT = "0";
                    } else {
                        PhoneTXT = dbAssist.getPhone();
                    }
                } else {
                    PhoneTXT = Phone.getText().toString();
                }


                if (InternetTXT.isEmpty()) {
                    if (dbAssist.getInternet().isEmpty()) {
                        InternetTXT = "0";
                    } else {
                        InternetTXT = dbAssist.getInternet();
                    }
                } else {
                    InternetTXT = Internet.getText().toString();
                }


                if (GymTXT.isEmpty()) {
                    if (dbAssist.getGym().isEmpty()) {
                        GymTXT = "0";
                    } else {
                        GymTXT = dbAssist.getGym();
                    }
                } else {
                    GymTXT = Gym.getText().toString();
                }


                if (FoodTXT.isEmpty()) {
                    if (dbAssist.getFood().isEmpty()) {
                        FoodTXT = "0";
                    } else {
                        FoodTXT = dbAssist.getFood();
                    }
                } else {
                    FoodTXT = Food.getText().toString();
                }

                if (GasTXT.isEmpty()) {
                    if (dbAssist.getGas().isEmpty()) {
                        GasTXT = "0";
                    } else {
                        GasTXT = dbAssist.getGas();
                    }
                } else {
                    GasTXT = Gas.getText().toString();
                }

                if (InsuranceTXT.isEmpty()) {
                    if (dbAssist.getInsurance().isEmpty()) {
                        InsuranceTXT = "0";
                    } else {
                        InsuranceTXT = dbAssist.getInsurance();
                    }
                } else {
                    InsuranceTXT = Insurance.getText().toString();
                }


                if (CarLoanTXT.isEmpty()) {
                    if (dbAssist.getCar().isEmpty()) {
                        CarLoanTXT = "0";
                    } else {
                        CarLoanTXT = dbAssist.getCar();
                    }
                } else {
                    CarLoanTXT = CarLoan.getText().toString();
                }

                if (StudentLoanTXT.isEmpty()) {
                    if (dbAssist.getStudent().isEmpty()) {
                        StudentLoanTXT = "0";
                    } else {
                        StudentLoanTXT = dbAssist.getStudent();
                    }
                } else {
                    StudentLoanTXT = StudentLoan.getText().toString();
                }

                if (CharityTXT.isEmpty()) {
                    if (dbAssist.getCharity().isEmpty()) {
                        CharityTXT = "0";
                    } else {
                        CharityTXT = dbAssist.getCharity();
                    }
                } else {
                    CharityTXT = Charity.getText().toString();
                }


                if (EmergencyFundTXT.isEmpty()) {
                    if (dbAssist.getEmergency().isEmpty()) {
                        EmergencyFundTXT = "0";
                    } else {
                        EmergencyFundTXT = dbAssist.getEmergency();
                    }
                } else {
                    EmergencyFundTXT = EmergencyFund.getText().toString();
                }


                if (SavingsTXT.isEmpty()) {
                    if (dbAssist.getSavings().isEmpty()) {
                        SavingsTXT = "0";
                    } else {
                        SavingsTXT = dbAssist.getSavings();
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
                    //if we are able to actually update the database without error then we are good.
                    Boolean checkupdatedata = db.initialData(1, IncomeTXT, RentTXT, UtilitiesTXT, PhoneTXT, InternetTXT, GymTXT, FoodTXT, GasTXT, InsuranceTXT, CarLoanTXT, StudentLoanTXT, CharityTXT, EmergencyFundTXT, SavingsTXT);
                    //send a good message
                    if (checkupdatedata == true) {
                        Toast.makeText(BudgetItems.this, "Entry Updated ", Toast.LENGTH_SHORT).show();
                    }
                    //else if it is not then send a bad message
                    else {
                        Toast.makeText(BudgetItems.this, "Entry Not Updated.", Toast.LENGTH_SHORT).show();
                    }

                }

                //disable the button after it's clicked
                addButton.setEnabled(false);
                updateButton.setEnabled(true);
            }

        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sound2.start();

                //get the values here.
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



                //now we need to check if it is blank. if blank then we need to retrieve the current value in the database.

                //income this is getting it
                if (IncomeTXT.isEmpty()) {

                    if (dbAssist.getIncome().isEmpty()) {
                        IncomeTXT = "0";

                    } else {
                        IncomeTXT = dbAssist.getIncome();
                    }

                } else {
                    IncomeTXT = Income.getText().toString();
                }

                //rent
                if (RentTXT.isEmpty()) {
                    if (dbAssist.getRent().isEmpty()) {
                        RentTXT = "0";
                    } else {
                        RentTXT = dbAssist.getRent();
                    }
                } else {
                    RentTXT = Rent.getText().toString();
                }


                //Utilities
                if (UtilitiesTXT.isEmpty()) {
                    if (dbAssist.getUtilities().isEmpty()) {
                        UtilitiesTXT = "0";
                    } else {
                        UtilitiesTXT = dbAssist.getUtilities();
                    }
                } else {
                    UtilitiesTXT = Utilities.getText().toString();
                }


                //phone
                if (PhoneTXT.isEmpty()) {
                    if (dbAssist.getPhone().isEmpty()) {
                        PhoneTXT = "0";
                    } else {
                        PhoneTXT = dbAssist.getPhone();
                    }
                } else {
                    PhoneTXT = Phone.getText().toString();
                }


                if (InternetTXT.isEmpty()) {
                    if (dbAssist.getInternet().isEmpty()) {
                        InternetTXT = "0";
                    } else {
                        InternetTXT = dbAssist.getInternet();
                    }
                } else {
                    InternetTXT = Internet.getText().toString();
                }


                if (GymTXT.isEmpty()) {
                    if (dbAssist.getGym().isEmpty()) {
                        GymTXT = "0";
                    } else {
                        GymTXT = dbAssist.getGym();
                    }
                } else {
                    GymTXT = Gym.getText().toString();
                }


                if (FoodTXT.isEmpty()) {
                    if (dbAssist.getFood().isEmpty()) {
                        FoodTXT = "0";
                    } else {
                        FoodTXT = dbAssist.getFood();
                    }
                } else {
                    FoodTXT = Food.getText().toString();
                }

                if (GasTXT.isEmpty()) {
                    if (dbAssist.getGas().isEmpty()) {
                        GasTXT = "0";
                    } else {
                        GasTXT = dbAssist.getGas();
                    }
                } else {
                    GasTXT = Gas.getText().toString();
                }

                if (InsuranceTXT.isEmpty()) {
                    if (dbAssist.getInsurance().isEmpty()) {
                        InsuranceTXT = "0";
                    } else {
                        InsuranceTXT = dbAssist.getInsurance();
                    }
                } else {
                    InsuranceTXT = Insurance.getText().toString();
                }


                if (CarLoanTXT.isEmpty()) {
                    if (dbAssist.getCar().isEmpty()) {
                        CarLoanTXT = "0";
                    } else {
                        CarLoanTXT = dbAssist.getCar();
                    }
                } else {
                    CarLoanTXT = CarLoan.getText().toString();
                }

                if (StudentLoanTXT.isEmpty()) {
                    if (dbAssist.getStudent().isEmpty()) {
                        StudentLoanTXT = "0";
                    } else {
                        StudentLoanTXT = dbAssist.getStudent();
                    }
                } else {
                    StudentLoanTXT = StudentLoan.getText().toString();
                }

                if (CharityTXT.isEmpty()) {
                    if (dbAssist.getCharity().isEmpty()) {
                        CharityTXT = "0";
                    } else {
                        CharityTXT = dbAssist.getCharity();
                    }
                } else {
                    CharityTXT = Charity.getText().toString();
                }


                if (EmergencyFundTXT.isEmpty()) {
                    if (dbAssist.getEmergency().isEmpty()) {
                        EmergencyFundTXT = "0";
                    } else {
                        EmergencyFundTXT = dbAssist.getEmergency();
                    }
                } else {
                    EmergencyFundTXT = EmergencyFund.getText().toString();
                }


                if (SavingsTXT.isEmpty()) {
                    if (dbAssist.getSavings().isEmpty()) {
                        SavingsTXT = "0";
                    } else {
                        SavingsTXT = dbAssist.getSavings();
                    }
                } else {
                    SavingsTXT = Savings.getText().toString();
                }





                //now ensure that the values do not overexceed the income
                //convert everything to double NOT USED YET

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
                if (total > income) {
                    Toast.makeText(BudgetItems.this, "Total exceeds income. Please adjust values and use zeros.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //if we are able to actually update the database without error then we are good.
                    Boolean checkupdatedata = db.updateData(1, IncomeTXT, RentTXT, UtilitiesTXT, PhoneTXT, InternetTXT, GymTXT, FoodTXT, GasTXT, InsuranceTXT, CarLoanTXT, StudentLoanTXT, CharityTXT, EmergencyFundTXT, SavingsTXT);
                    //send a good message
                    if (checkupdatedata == true) {
                        Toast.makeText(BudgetItems.this, "Entry Updated ", Toast.LENGTH_SHORT).show();
                    }
                    //else if it is not then send a bad message
                    else {
                        Toast.makeText(BudgetItems.this, "Entry Not Updated.", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });




        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound2.start();

                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetItems.this);
                builder.setMessage("Are you sure want to reset all entries and begin a new budget?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Proceed with the database action
                                Boolean checkresetdata = db.resetData();

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
                sound2.start();


                                // Proceed with the database action
                                Cursor res = db.getData();
                                if (res.getCount() == 0) {
                                    Toast.makeText(BudgetItems.this, "No entry exists", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                StringBuilder buffer = new StringBuilder();
                                while (res.moveToNext()) {
                                    //this is my check to ensure I am only using 1 ID.
                                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                                    buffer.append("Starting Income: ").append(res.getString(1)).append("\n");

                                    buffer.append("Income: ").append(res.getString(2)).append("\n");
                                    buffer.append("Rent : ").append(res.getString(3)).append("\n");
                                    buffer.append("Utilities : ").append(res.getString(4)).append("\n");
                                    buffer.append("Phone : ").append(res.getString(5)).append("\n");
                                    buffer.append("Internet : ").append(res.getString(6)).append("\n");
                                    buffer.append("Gym : ").append(res.getString(7)).append("\n");
                                    buffer.append("Food : ").append(res.getString(8)).append("\n");
                                    buffer.append("Gas : ").append(res.getString(9)).append("\n");
                                    buffer.append("Insurance : ").append(res.getString(10)).append("\n");
                                    buffer.append("Car Loan : ").append(res.getString(11)).append("\n");
                                    buffer.append("Student Loan : ").append(res.getString(12)).append("\n");
                                    buffer.append("Charity : ").append(res.getString(13)).append("\n");
                                    buffer.append("Emergency Fund : ").append(res.getString(14)).append("\n");
                                    buffer.append("Savings : ").append(res.getString(15)).append("\n");
                                }

                                //this is also my TESTING
                                AlertDialog.Builder detailsbuilder = new AlertDialog.Builder(BudgetItems.this);
                                detailsbuilder.setCancelable(true);
                                detailsbuilder.setTitle("User Details");
                                detailsbuilder.setMessage(buffer.toString());
                                detailsbuilder.show();



            }

        });


    }


}