package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.Database.DBSingleton;
import com.example.finalproject.R;

public class BudgetItems extends AppCompatActivity {


    private MediaPlayer sound1;
    private MediaPlayer sound2;

    EditText  Income, Rent, Utilities, Phone, Internet, Gym, Food, Gas, Insurance, CarLoan, StudentLoan, Charity, EmergencyFund, Savings, Retirement;

    TextView BudgetID;

    Button addButton, updateButton, refreshButton, viewButton;

    DBHelper db;

    DBAssist dbAssist = new DBAssist(this);

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budgetitems);

        db = DBSingleton.getInstance(this);

        sound1 = MediaPlayer.create(this, R.raw.swordswing);
        sound2 = MediaPlayer.create(this, R.raw.clickbutton);

        ImageView arrow1 = findViewById(R.id.backArrow3);
        arrow1.setOnClickListener(v -> {
            sound1.start();
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
        Retirement = findViewById(R.id.Retirement);
        BudgetID = findViewById(R.id.ID);

        updateButton = findViewById(R.id.Update);
        refreshButton = findViewById(R.id.Refresh);
        viewButton = findViewById(R.id.View);



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
                String RetirementTXT = Retirement.getText().toString();


                //now we need to check if it is blank. if blank then we need to retrieve the current value in the database.
                //income
                if (IncomeTXT.isEmpty()) {

                    if (dbAssist.getIncome().isEmpty()) {
                        IncomeTXT = "0";

                    } else {
                        IncomeTXT = dbAssist.getIncome();
                    }

                }
                else
                {
                    IncomeTXT = Income.getText().toString();
                }

                //rent
                if(RentTXT.isEmpty())
                {
                    if(dbAssist.getRent().isEmpty())
                    {
                        RentTXT = "0";
                    }
                    else
                    {
                        RentTXT = dbAssist.getRent();
                    }
                }
                else
                {
                    RentTXT = Rent.getText().toString();
                }


                //Utilities
                if(UtilitiesTXT.isEmpty())
                {
                    if(dbAssist.getUtilities().isEmpty())
                    {
                        UtilitiesTXT = "0";
                    }
                    else
                    {
                        UtilitiesTXT = dbAssist.getUtilities();
                    }
                }
                else
                {
                    UtilitiesTXT = Utilities.getText().toString();
                }


                //phone
                if(PhoneTXT.isEmpty())
                {
                    if(dbAssist.getPhone().isEmpty())
                    {
                        PhoneTXT = "0";
                    }
                    else
                    {
                        PhoneTXT = dbAssist.getPhone();
                    }
                }
                else
                {
                    PhoneTXT = Phone.getText().toString();
                }


                if(InternetTXT.isEmpty())
                {
                    if(dbAssist.getInternet().isEmpty())
                    {
                        InternetTXT = "0";
                    }
                    else
                    {
                        InternetTXT = dbAssist.getInternet();
                    }
                }
                else
                {
                    InternetTXT = Internet.getText().toString();
                }


                if(GymTXT.isEmpty())
                {
                    if(dbAssist.getGym().isEmpty())
                    {
                        GymTXT = "0";
                    }
                    else
                    {
                        GymTXT = dbAssist.getGym();
                    }
                }
                else
                {
                    GymTXT = Gym.getText().toString();
                }


                if(FoodTXT.isEmpty())
                {
                    if(dbAssist.getFood().isEmpty())
                    {
                        FoodTXT = "0";
                    }
                    else
                    {
                        FoodTXT = dbAssist.getFood();
                    }
                }
                else
                {
                    FoodTXT = Food.getText().toString();
                }

                if(GasTXT.isEmpty())
                {
                    if(dbAssist.getGas().isEmpty())
                    {
                        GasTXT = "0";
                    }
                    else
                    {
                        GasTXT = dbAssist.getGas();
                    }
                }
                else
                {
                    GasTXT = Gas.getText().toString();
                }

                if(InsuranceTXT.isEmpty())
                {
                    if(dbAssist.getInsurance().isEmpty())
                    {
                        InsuranceTXT = "0";
                    }
                    else
                    {
                        InsuranceTXT = dbAssist.getInsurance();
                    }
                }
                else
                {
                    InsuranceTXT = Insurance.getText().toString();
                }


                if(CarLoanTXT.isEmpty())
                {
                    if(dbAssist.getCar().isEmpty())
                    {
                        CarLoanTXT = "0";
                    }
                    else
                    {
                        CarLoanTXT = dbAssist.getCar();
                    }
                }
                else
                {
                    CarLoanTXT = CarLoan.getText().toString();
                }

                if(StudentLoanTXT.isEmpty())
                {
                    if(dbAssist.getStudent().isEmpty())
                    {
                        StudentLoanTXT = "0";
                    }
                    else
                    {
                        StudentLoanTXT = dbAssist.getStudent();
                    }
                }
                else
                {
                    StudentLoanTXT = StudentLoan.getText().toString();
                }

                if(CharityTXT.isEmpty())
                {
                    if(dbAssist.getCharity().isEmpty())
                    {
                        CharityTXT = "0";
                    }
                    else
                    {
                        CharityTXT = dbAssist.getCharity();
                    }
                }
                else
                {
                    CharityTXT = Charity.getText().toString();
                }


                if(EmergencyFundTXT.isEmpty())
                {
                    if(dbAssist.getEmergency().isEmpty())
                    {
                        EmergencyFundTXT = "0";
                    }
                    else
                    {
                        EmergencyFundTXT = dbAssist.getEmergency();
                    }
                }
                else
                {
                    EmergencyFundTXT = EmergencyFund.getText().toString();
                }


                if(SavingsTXT.isEmpty())
                {
                    if(dbAssist.getSavings().isEmpty())
                    {
                        SavingsTXT = "0";
                    }
                    else
                    {
                        SavingsTXT = dbAssist.getSavings();
                    }
                }
                else
                {
                    SavingsTXT = Savings.getText().toString();
                }


                if(RetirementTXT.isEmpty())
                {
                    if(dbAssist.getRetirement().isEmpty())
                    {
                        RetirementTXT = "0";
                    }
                    else
                    {
                        RetirementTXT = dbAssist.getRetirement();
                    }
                }
                else
                {
                    RetirementTXT = Retirement.getText().toString();
                }



                //now ensure that the values do not overexceed the income
                //convert everything to double
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
                double retirement = Double.parseDouble(RetirementTXT);
                double total = rent + utilities + phone + internet + gym + food + gas + insurance + car + student + charity + emergency + savings + retirement;
                if (total > income) {
                    Toast.makeText(BudgetItems.this, "Total exceeds income. Please adjust the values.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //proceed to updating the database.
                    Boolean checkupdatedata = db.updateData(1, IncomeTXT, RentTXT, UtilitiesTXT, PhoneTXT, InternetTXT, GymTXT, FoodTXT, GasTXT, InsuranceTXT, CarLoanTXT, StudentLoanTXT, CharityTXT, EmergencyFundTXT, SavingsTXT, RetirementTXT);
                    if (checkupdatedata == true) {
                        Toast.makeText(BudgetItems.this, "Entry Updated ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BudgetItems.this, "Entry Not Updated.", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound2.start();
                Boolean checkresetdata = db.resetData();

                if(checkresetdata == true) {
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
                    Retirement.setText("");
                    Toast.makeText(BudgetItems.this, "All Entries Reset", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(BudgetItems.this, "All Entries Not Reset", Toast.LENGTH_SHORT).show();

                }

            }
        });




        viewButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                //sendDBValuesToAnotherActivity();

                sound2.start();

                //THIS WORKS!!!!
                Cursor res = db.getData();
                if (res.getCount() == 0) {
                    Toast.makeText(BudgetItems.this, "No entry exists", Toast.LENGTH_SHORT).show();
//                    tv.setText("");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    //this is my check to ensure I am only using 1 ID.
                    //buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Income: ").append(res.getString(1)).append("\n");
                    buffer.append("Rent : ").append(res.getString(2)).append("\n");
                    buffer.append("Utilities : ").append(res.getString(3)).append("\n");
                    buffer.append("Phone : ").append(res.getString(4)).append("\n");
                    buffer.append("Internet : ").append(res.getString(5)).append("\n");
                    buffer.append("Gym : ").append(res.getString(6)).append("\n");
                    buffer.append("Food : ").append(res.getString(7)).append("\n");
                    buffer.append("Gas : ").append(res.getString(8)).append("\n");
                    buffer.append("Insurance : ").append(res.getString(9)).append("\n");
                    buffer.append("Car Loan : ").append(res.getString(10)).append("\n");
                    buffer.append("Student Loan : ").append(res.getString(11)).append("\n");
                    buffer.append("Charity : ").append(res.getString(12)).append("\n");
                    buffer.append("Emergency Fund : ").append(res.getString(13)).append("\n");
                    buffer.append("Savings : ").append(res.getString(14)).append("\n");
                    buffer.append("Retirement : ").append(res.getString(15)).append("\n");


                }

                //this is also my TESTING
                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetItems.this);
                builder.setCancelable(true);
                builder.setTitle("User Details");
                builder.setMessage(buffer.toString());
                builder.show();


            }


        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}