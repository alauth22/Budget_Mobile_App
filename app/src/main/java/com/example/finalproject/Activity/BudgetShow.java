package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.finalproject.Animation.RotateSideAnimate;
import com.example.finalproject.Database.DBAssist2;
import com.example.finalproject.Database.DBHelper2;
import com.example.finalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BudgetShow extends AppCompatActivity {


    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();

    private MediaPlayer sound2;

    private MediaPlayer sound3;
    TextView tv;
    ImageView arrow1;
    Button RefreshButton;
    DBHelper2 db;

    String income;
   DBAssist2 dbAssist = new DBAssist2(this);

    //RecyclerView recyclerView;
    FloatingActionButton fab1;

    ImageView DollarSign;


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
            db = new DBHelper2(this);

            sound2 = MediaPlayer.create(this, R.raw.messageincoming);
            sound3 = MediaPlayer.create(this, R.raw.clickbutton);

            tv = findViewById(R.id.BudgetView);

            //code for arrow button
            arrow1 = findViewById(R.id.backArrow1);

            arrow1.setOnClickListener(v -> {
                RotateSideAnimate rotateSideAnimate = new RotateSideAnimate(arrow1);
                finish();
                    });

//            arrow1.setOnClickListener(v -> {
//                finish();
//            });

            //code for the floating action button to obtain the new budget
            fab1 = findViewById(R.id.BudgetAdd);
            fab1.setOnClickListener(view -> {
                // Intent to open BudgetShow
                sound2.start();
                RotateSideAnimate rotateSideAnimate = new RotateSideAnimate(fab1);
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
        updateBudgetView(userID);
    }


    private void updateBudgetView(String userID) {
        StringBuilder sb = new StringBuilder();

        // Fetch values from the database
        String income = dbAssist.getIncome(userID);
        String rent = dbAssist.getRent(userID);
        String utilities = dbAssist.getUtilities(userID);
        String phone = dbAssist.getPhone(userID);
        String internet = dbAssist.getInternet(userID);
        String gym = dbAssist.getGym(userID);
        String food = dbAssist.getFood(userID);
        String gas = dbAssist.getGas(userID);
        String insurance = dbAssist.getInsurance(userID);
        String carLoan = dbAssist.getCar(userID);
        String studentLoan = dbAssist.getStudent(userID);
        String charity = dbAssist.getCharity(userID);
        String emergencyFund = dbAssist.getEmergency(userID);
        String savings = dbAssist.getSavings(userID);

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

        // Update the TextView
        tv.setText(sb.toString());
    }

}