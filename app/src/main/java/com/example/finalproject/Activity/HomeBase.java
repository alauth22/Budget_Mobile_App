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
import com.example.finalproject.GPS;
import com.example.finalproject.R;
import com.example.finalproject.SignatureCanvas.Sign;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;

public class HomeBase extends AppCompatActivity {

    //declare variables
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();
    String userEmail = auth.getCurrentUser().getEmail();
    TextView Email, Remaining;
    private MediaPlayer sound1;
    ImageView pencil, video, logout, map;
    DBAssist2 dbAssist;
    DBHelper2 dbHelper;

    //SD DOWNLOADS
    private final int SelectVideo = 1;
    private CircularProgressIndicator circularProgressIndicator;
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

        //logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RotateSideAnimate(logout);
                Intent intent = new Intent(HomeBase.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //find the actual button based off of it's ID from the activity_main
        Button budget = findViewById(R.id.Budget2);
        Remaining = findViewById(R.id.ViewRemaining2);

        //budget button
        budget.setOnClickListener(new View.OnClickListener() {
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


        //know which user logged in.
        Email = findViewById(R.id.UserEmail);
        Email.setText("Welcome: " + userEmail + "!");

        //pencil icon to get you to the signature pad.
        pencil = findViewById(R.id.pencil);
        pencil.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              sound1.start();
              Intent intent = new Intent(HomeBase.this, Sign.class);
              startActivity(intent);

          }
      });


        map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                Intent intent = new Intent(HomeBase.this, GPS.class);
                startActivity(intent);
            }
        });


        //video icon to get you to the video page.
        video = findViewById(R.id.video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                Intent intent = new Intent(HomeBase.this, Videos.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get you to the SD card of emulator
        findViewById(R.id.sdDownloads2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                PickTextFile();
            }
        });


    }

    //upon resuming to the page.
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        TextView incomeTextView = findViewById(R.id.ViewRemaining2);
        //ok now we begin out work on the circular progress bar
        String incomeStartStr = dbAssist.getStartingIncome(userID);
        String incomeCurrentStr = dbAssist.getIncome(userID);


        double incomeStart = 1;
        double incomeCurrent = 0;

        try {
            incomeStart = Double.parseDouble(incomeStartStr);
            incomeCurrent = Double.parseDouble(incomeCurrentStr);
        }
        catch (NumberFormatException e)
        {
            //if there is somehow an invalid number format
            e.printStackTrace();
        }

        //this will help us keep track of what value we are currently at for the progress bar.
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