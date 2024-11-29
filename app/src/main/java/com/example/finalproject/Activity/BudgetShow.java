package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BudgetShow extends AppCompatActivity {


    private MediaPlayer sound1;
    private MediaPlayer sound2;

    private MediaPlayer sound3;
    TextView tv;
    Button RefreshButton;
    DBHelper db;

    String income;
   DBAssist dbAssist = new DBAssist(this);

    //RecyclerView recyclerView;
    FloatingActionButton fab1;
    //MyDatabaseHelper myDB;

//    Intent intent = new Intent(BudgetShow.this, BudgetItems.class);
//    startActivity(intent);


    //ArrayList<Double> budgetID, income, rentArray, utilities, phone, internet, gym, foodArray, gasArray, insurance, car, student, charity, emergency, savings, retirement;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_budgetshow);

            //need to give the DBHelper the correct context.
            db = new DBHelper(this);

            //media player for sound1
            sound1 = MediaPlayer.create(this, R.raw.swordswing);
            sound2 = MediaPlayer.create(this, R.raw.messageincoming);
            sound3 = MediaPlayer.create(this, R.raw.clickbutton);


            tv = findViewById(R.id.BudgetView);

            //code for arrow button
            ImageView arrow1 = findViewById(R.id.backArrow1);
            arrow1.setOnClickListener(v -> {
                sound1.start();
                finish();
            });

            //code for the floating action button to obtain the new budget
            fab1 = findViewById(R.id.BudgetAdd);
            fab1.setOnClickListener(view -> {
                // Intent to open BudgetShow
                sound2.start();
                Intent intent2 = new Intent(BudgetShow.this, BudgetItems.class);
                startActivity(intent2);
            });


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateBudgetView();
    }


    private void updateBudgetView() {
        StringBuilder sb = new StringBuilder();

        // Fetch values from the database
        String income = dbAssist.getIncome();
        String rent = dbAssist.getRent();
        String utilities = dbAssist.getUtilities();
        String phone = dbAssist.getPhone();
        String internet = dbAssist.getInternet();
        String gym = dbAssist.getGym();
        String food = dbAssist.getFood();
        String gas = dbAssist.getGas();
        String insurance = dbAssist.getInsurance();
        String carLoan = dbAssist.getCar();
        String studentLoan = dbAssist.getStudent();
        String charity = dbAssist.getCharity();
        String emergencyFund = dbAssist.getEmergency();
        String savings = dbAssist.getSavings();
        String retirement = dbAssist.getRetirement();

        // Append data to the StringBuilder
        sb.append("Income: $").append(income).append("\n\n");
        sb.append("Rent: $").append(rent).append("\n\n");
        sb.append("Utilities: $").append(utilities).append("\n\n");
        sb.append("Phone: $").append(phone).append("\n\n");
        sb.append("Internet: $").append(internet).append("\n\n");
        sb.append("Gym: $").append(gym).append("\n\n");
        sb.append("Food: $").append(food).append("\n\n");
        sb.append("Gas: $").append(gas).append("\n\n");
        sb.append("Insurance: $").append(insurance).append("\n\n");
        sb.append("Car Loan: $").append(carLoan).append("\n\n");
        sb.append("Student Loan: $").append(studentLoan).append("\n\n");
        sb.append("Charity: $").append(charity).append("\n\n");
        sb.append("Emergency Fund: $").append(emergencyFund).append("\n\n");
        sb.append("Savings: $").append(savings).append("\n\n");
        sb.append("Retirement: $").append(retirement).append("\n\n");

        // Update the TextView
        tv.setText(sb.toString());
    }



}