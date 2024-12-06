package com.example.finalproject.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.finalproject.Animation.RotateSideAnimate;
import com.example.finalproject.Animation.CircularAnimation;
import com.example.finalproject.Database.DBAssist2;
import com.example.finalproject.Database.DBHelper2;
import com.example.finalproject.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;

public class HomeBase extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();

    String userEmail = auth.getCurrentUser().getEmail();
    TextView Email, Remaining;

    private MediaPlayer sound1;

    Button Home;

    ImageView logout;
    DBAssist2 dbAssist;
    DBHelper2 dbHelper;

    //SD DOWNLOADS
    private final int SelectVideo = 1;

    private CircularProgressIndicator circularProgressIndicator;
    private int i = 0;
    private int progressValue;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_base);

        dbAssist = new DBAssist2(this);
        dbHelper = new DBHelper2(this);

        sound1 = MediaPlayer.create(this, R.raw.dot);

        logout = findViewById(R.id.LogOUT2);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateSideAnimate rotateSideAnimate = new RotateSideAnimate(logout);
                Intent intent = new Intent(HomeBase.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //find the actual button based off of it's ID from the activity_main
        Button button = findViewById(R.id.Budget2);
        Remaining = findViewById(R.id.ViewRemaining2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                //now need to tell it what I intend to do with it.
                Intent intent = new Intent(HomeBase.this, BudgetShow.class);
                //start the new activity with the intent
                startActivity(intent);
            }
        });



        //spent button
        Button buttonSpent = findViewById(R.id.Spent2);
        buttonSpent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                //now need to tell it what I intend to do with it.
                Intent intent = new Intent(HomeBase.this, com.example.finalproject.Activity.Spent.class);
                startActivity(intent);
            }
        });


        Email = findViewById(R.id.UserEmail);
        Email.setText("Welcome: " + userEmail + "!");



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.sdDownloads2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                PickTextFile();
            }
        });


    }



    //REVIEW THIS CODE AGAIN BECAUSE IT WILL ALWAYS BE A 100% BECAUSE THE INCOME IS SEEN AS 100% AS IT CHANGES BECAUSE THERE IS NO COMPARISON.
    //MAYBE SOMEHOW HAVE AN INTEGER VARIABLE BE EQUAL TO THE INCOME OR HAVE THEM PLUG IT IN.

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        String updatedIncome = dbAssist.getIncome(userID); // Query the database for the income
        TextView incomeTextView = findViewById(R.id.ViewRemaining2);
        //incomeTextView.setText("Income: $" + updatedIncome);


        //ok now we begin out work on the circular progress bar
        String incomeStartStr = dbAssist.getStartingIncome(userID);
        String incomeCurrentStr = dbAssist.getIncome(userID);


        double incomeStart = 1;
        double incomeCurrent = 0;

        try {
            incomeStart = Double.parseDouble(incomeStartStr);
            incomeCurrent = Double.parseDouble(incomeCurrentStr);
        } catch (NumberFormatException e) {
            //if there is somehow an invalid number format
            e.printStackTrace();
        }

        progressValue = 0;

        //so if the income start is greater than 0, because this is going to be based off of a 0 - 100 scale, we need to convert this to a percentage.
        if (incomeStart > 0) {
            progressValue = (int) ((incomeCurrent / incomeStart) * 100);
        }

      /*
      Here I am using both a handler and a runnable. The handler is the looper.getmainlooper() which allows
      the tasks that needs to be executed to be done on the main thread. I want this as I am only updating the UI circular progress indicator bar.
      The runnable or where it says run() defines which code needs to actually be run after the delay in the loop. in my example, I have the runnable being executed
      after 200 milliseconds delay.
       */
        incomeTextView.setText(progressValue + "% Left");
        circularProgressIndicator = findViewById(R.id.circularProgressIndicator2);
        progressValue = Math.min(progressValue, 100);
        int finalProgressValue = progressValue;
        new CircularAnimation(circularProgressIndicator, finalProgressValue);

    }

    //code for access of the SD card with the pdfs downloaded
    private void PickTextFile()
    {
        Intent video = new Intent();
        video.setType("application/pdf");
        //allow document access
        video.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(video, "Select Text Files"), SelectVideo);

    }


}