package com.example.finalproject.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.finalproject.Database.DBAssist;
import com.example.finalproject.Database.DBHelper;
import com.example.finalproject.R;
import com.google.android.material.textfield.TextInputLayout;
import android.content.SharedPreferences;

public class Spent extends AppCompatActivity {

    private MediaPlayer sound1;

    // Declare currentIncome as a member variable

    String[] Budget_Items = {"Rent", "Utilities", "Phone", "Internet", "Gym", "Food", "Gas", "Insurance", "Car Loan", "Student Loan", "Charity", "Emergency Fund", "Savings", "Retirement"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    Button spendButton;
    EditText AmountSpent;
    TextInputLayout ExpenseFromList;

    EditText expenseEditText;
    String expenseFinal;



    //NEW CODE
    private DBHelper dbHelper;
    private Context context;

//    public Spent(Context context) {
//        this.context = context;
//        this.dbHelper = new DBHelper(context);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spent);

        // Retrieve income from the database (once) and store in currentIncome
        //IT'S THIS LINE THAT THE APP KEEPS CRASHING
        //currentIncome = dbAssist.getIncome().isEmpty() ? 0 : Double.parseDouble(dbAssist.getIncome());

        sound1 = MediaPlayer.create(this, R.raw.clickbutton);

        //map the button and edittext from xml
        spendButton = findViewById(R.id.AddSpent);
        AmountSpent = findViewById(R.id.AmountSpent);
        ExpenseFromList = findViewById(R.id.textInputLayout);
        expenseEditText = ExpenseFromList.getEditText();
        expenseFinal = expenseEditText.getText().toString();


        //String category = expense.getText().toString();

        // Initialize DBAssist
        //dbAssist = new DBAssist(this);
        // Initialize the database helper
        dbHelper = new DBHelper(this);



        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterItem = new ArrayAdapter<String>(this, R.layout.list_budgetitem, Budget_Items);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapterItem.getItem(position).toString();
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }
        });




        // Handle the "Add Spent" button click
        spendButton.setOnClickListener(v -> {
            sound1.start();
            processSpending();

        });


        //arrow button
        ImageView arrow4 = findViewById(R.id.backArrow4);
        arrow4.setOnClickListener(v -> {
            finish();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }



    public void processSpending() {

        // Retrieve the expense category at the time of button click
        String expenseFinal = expenseEditText.getText().toString().trim();
        String amountString = AmountSpent.getText().toString().trim();

        // Validate user inputs
        if (expenseFinal.isEmpty() || amountString.isEmpty()) {
            Toast.makeText(this, "Please enter both category and amount!" + expenseFinal, Toast.LENGTH_SHORT).show();
            return;
        }

        double amountSpent;
        try {
            amountSpent = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch budget data from the database
        Cursor cursor = dbHelper.getData();
        if (cursor != null && cursor.moveToFirst()) {
            // Check if the category exists in the database
            int columnIndex = cursor.getColumnIndex(expenseFinal);
            if (columnIndex == -1) {
                Toast.makeText(this, "Invalid category entered! ", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }

            // Get current values for the category and income
            double categoryAmount = Double.parseDouble(cursor.getString(columnIndex));
            @SuppressLint("Range") double income = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Income")));

            // Check if there are sufficient funds
            if (categoryAmount >= amountSpent) {
                // Deduct the spent amount from that category
                categoryAmount -= amountSpent;

                //deduct from the overall income
                income -= amountSpent;

                // Update the database
                @SuppressLint("Range") boolean isUpdated = dbHelper.updateData(
                        1, // Assuming BudgetID is 1
                        String.valueOf(income),
                        expenseFinal.equals("Rent") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Rent")),
                        expenseFinal.equals("Utilities") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Utilities")),
                        expenseFinal.equals("Phone") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Phone")),
                        expenseFinal.equals("Internet") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Internet")),
                        expenseFinal.equals("Gym") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Gym")),
                        expenseFinal.equals("Food") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Food")),
                        expenseFinal.equals("Gas") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Gas")),
                        expenseFinal.equals("Insurance") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Insurance")),
                        expenseFinal.equals("CarLoan") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("CarLoan")),
                        expenseFinal.equals("StudentLoan") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("StudentLoan")),
                        expenseFinal.equals("Charity") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Charity")),
                        expenseFinal.equals("EmergencyFund") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("EmergencyFund")),
                        expenseFinal.equals("Savings") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Savings")),
                        expenseFinal.equals("Retirement") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Retirement"))
                );

                if (isUpdated) {

                    //get the intent needed to pass to the MainActivity class.
                    Intent intent = new Intent(Spent.this, MainActivity.class);
                    intent.putExtra("remainingIncome", income);
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "Successfully spent $" + amountSpent + " on " + expenseFinal, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Failed to update the database!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Insufficient funds in " + expenseFinal, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to retrieve budget data!", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }


}