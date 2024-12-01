package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.TextView;
import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView Income, Spent, Remaining;

    private MediaPlayer sound1;

    Button Home;
    DBAssist dbAssist;
    DBHelper dbHelper;

    //SD DOWNLOADS
    private final int SelectVideo = 1;

    private CircularProgressIndicator circularProgressIndicator;
    private int i = 0;


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //grabs the activity_main.xml where all the views are located.
        setContentView(R.layout.activity_main);

        dbAssist = new DBAssist(this);
        dbHelper = new DBHelper(this);

        sound1 = MediaPlayer.create(this, R.raw.dot);


        //find the actual button based off of it's ID from the activity_main
        Button button = findViewById(R.id.Budget);
        Remaining = findViewById(R.id.ViewRemaining);

        //budgetshow button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound1.start();
                //now need to tell it what I intend to do with it.
                Intent intent = new Intent(MainActivity.this, BudgetShow.class);
                //start the new activity with the intent
                startActivity(intent);
            }
        });


        //spent button
        Button buttonSpent = findViewById(R.id.Spent);
        buttonSpent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sound1.start();
               //now need to tell it what I intend to do with it.
               Intent intent = new Intent(MainActivity.this, Spent.class);
               startActivity(intent);
           }
       });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //resources button
        findViewById(R.id.sdDownloads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        String updatedIncome = dbAssist.getIncome(); // Query the database for the income
        TextView incomeTextView = findViewById(R.id.ViewRemaining);
        incomeTextView.setText("Income: $" + updatedIncome);


        // Convert the updatedIncome string to an integer (or double depending on your logic)
        int incomeValue = 0;
        try {
            incomeValue = Integer.parseInt(updatedIncome);  // Or use Double.parseDouble(updatedIncome) if needed
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Handle invalid number format
        }

        // Ensure the progress value is between 0 and 100
        int progressValue = Math.min(Math.max(incomeValue, 0), 100);



        //below is the code that will deal with the circular progress bar
        circularProgressIndicator = findViewById(R.id.circularProgressIndicator);

        // Optionally, if you want to animate the progress change, use a handler
        //REVIEW HOW A HANDLER WORKS HERE!!!!!
        //
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                // Animate progress from current to the new value
                circularProgressIndicator.setProgressCompat(progressValue, true);
            }
        }, 200);


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