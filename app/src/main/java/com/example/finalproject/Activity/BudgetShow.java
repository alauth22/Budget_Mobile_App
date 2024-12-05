package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.finalproject.Animation.HoverEffect;
import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Handler;

public class BudgetShow extends AppCompatActivity {



    private MediaPlayer sound2;

    private MediaPlayer sound3;
    TextView tv;
    ImageView arrow1;
    Button RefreshButton;
    DBHelper db;

    String income;
   DBAssist dbAssist = new DBAssist(this);

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
            db = new DBHelper(this);

            //media player for sound1

            sound2 = MediaPlayer.create(this, R.raw.messageincoming);
            sound3 = MediaPlayer.create(this, R.raw.clickbutton);


            tv = findViewById(R.id.BudgetView);

            //Animation doesn't quite work yet!!!
            DollarSign = findViewById(R.id.DollarSign);
            new HoverEffect(DollarSign);


            //code for arrow button
            arrow1 = findViewById(R.id.backArrow1);
            arrow1.setOnClickListener(v -> {
                startJiggleAnimation(arrow1);
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




    private void startJiggleAnimation(final ImageView icon) {
        final Handler handler = new Handler();
        final int animationDuration = 200; // milliseconds for each jiggle

        final Runnable jiggleRunnable = new Runnable() {
            @Override
            public void run() {
                RotateAnimation rotate = new RotateAnimation(
                        -10f, 10f, // From -10 degrees to 10 degrees
                        Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X at the center
                        Animation.RELATIVE_TO_SELF, 0.5f // Pivot Y at the center
                );
                rotate.setDuration(animationDuration);
                rotate.setRepeatMode(Animation.REVERSE);
                rotate.setRepeatCount(1);

                icon.startAnimation(rotate);

                // Repeat the animation every 500ms (or adjust to your preference)
                handler.postDelayed(this, 500);
            }
        };

        // Start the jiggle animation
        handler.post(jiggleRunnable);

    };


}