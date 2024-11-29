package com.example.finalproject.Activity;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.R;

public class MainActivity extends AppCompatActivity {

    TextView Income, Spent, Remaining;

    private MediaPlayer sound1;

    Button Home;
    DBAssist dbAssist;
    DBHelper dbHelper;
    double totalSpent, remainingAmount;

    //SD DOWNLOADS
    private final int SelectVideo = 1;



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





        //now use the actionListener to open the second activity
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


        //now use the actionListener to open the fifth activity
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




        //myDB = new MyDatabaseHelper(MainActivity.this);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor res = myDB.getData();
//                if (res.getCount() == 0) {
//                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()) {
//                   buffer.append("Rent Amount: " + res.getString(2));
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setCancelable(true);
//                builder.setTitle("Data");
//                builder.setMessage(buffer.toString());
//                builder.show();
//
//
//            }
//        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        findViewById(R.id.sdDownloads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickTextFile();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String updatedIncome = dbAssist.getIncome(); // Query the database for the income
        TextView incomeTextView = findViewById(R.id.ViewRemaining);
        incomeTextView.setText("Income: $" + updatedIncome);
    }

    private void PickTextFile()
    {
        Intent video = new Intent();
        video.setType("application/pdf");
        //allow document access
        video.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(video, "Select Text Files"), SelectVideo);

    }



}