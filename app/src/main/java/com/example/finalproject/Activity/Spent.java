package com.example.finalproject.Activity;

import android.annotation.SuppressLint;
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
import com.example.finalproject.Animation.RotateSideAnimate;
import com.example.finalproject.Database.DBAssist2;
import com.example.finalproject.Database.DBHelper2;
import com.example.finalproject.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Spent extends AppCompatActivity {

    //declare my various variables.
    private MediaPlayer sound1;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userID = auth.getCurrentUser().getUid();
    //List of all items in database.
    String[] Budget_Items = {"Rent", "Utilities", "Phone", "Internet", "Gym", "Food", "Gas", "Insurance", "Car Loan", "Student Loan", "Charity", "Emergency Fund", "Savings"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    Button spendButton;
    EditText AmountSpent, expenseEditText;
    TextInputLayout ExpenseFromList;
    String expenseFinal, item, currentAmount;
    private DBHelper2 dbHelper;
    private DBAssist2 dbAssist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spent);

        //sound for button click
        sound1 = MediaPlayer.create(this, R.raw.clickbutton);

        //map the button and edittext from xml
        spendButton = findViewById(R.id.AddSpent);
        AmountSpent = findViewById(R.id.AmountSpent);

        //get the String value from the ExpenseEditText
        ExpenseFromList = findViewById(R.id.textInputLayout);
        expenseEditText = ExpenseFromList.getEditText();
        expenseFinal = expenseEditText.getText().toString();

        // Initialize the database helper
        dbHelper = new DBHelper2(this);

        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterItem = new ArrayAdapter<String>(this, R.layout.list_budgetitem, Budget_Items);
        autoCompleteTextView.setAdapter(adapterItem);

        //dropdown list of budget items for spending.
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //currently have in that category.
                item = adapterItem.getItem(position);
                //get the current amount so that the user remembers how much they can spend in that category
                currentAmount = dbHelper.getDatabyColumnName(userID, replaceSpace(item.replace(" ", "")));
                Toast.makeText(getApplicationContext(), item + ": $" + currentAmount + " is remaining!", Toast.LENGTH_SHORT).show();
            }
        });


        //spending button
        spendButton.setOnClickListener(v -> {
            sound1.start();
            processSpending();
        });

        //back arrow button
        ImageView arrow4 = findViewById(R.id.backArrow4);
        arrow4.setOnClickListener(v -> {
            new RotateSideAnimate(arrow4);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    /*
    Method to process the spending.
     */
    public void processSpending() {

        //declare my variables and get the item from the dropdown list.
        expenseFinal = item;
        String amountString = AmountSpent.getText().toString().trim();

        //either or both the expense box and the amount are empty send error message.
        if (expenseFinal.isEmpty() || amountString.isEmpty()) {
            Toast.makeText(this, "Please enter an amount for " + expenseFinal + "!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Check if the amount is a valid number
        double amountSpent;
        try {
            amountSpent = Double.parseDouble(amountString);
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Invalid amount entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        //get the budget data from the database
        Cursor cursor = dbHelper.getData(userID);
        if (cursor != null && cursor.moveToFirst()) {
            // Check if the category exists in the database
            int columnIndex = cursor.getColumnIndex(replaceSpace(expenseFinal));
            if (columnIndex == -1) {
                Toast.makeText(this, "Invalid category entered! ", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }

            //get current values for the category and income
            double categoryAmount = Double.parseDouble(cursor.getString(columnIndex));
            double income = Double.parseDouble(cursor.getString(3));

            //check if there are sufficient funds
            if (categoryAmount >= amountSpent) {
                //deduct the spent amount from that category
                categoryAmount -= amountSpent;
                //deduct from the overall income
                income -= amountSpent;
                //update the database
                @SuppressLint("Range") boolean isUpdated = dbHelper.updateData(
                        userID,
                        String.valueOf(income),
                        //I want to say if the expenseFinal.equals("Rent") I want to grab it from index 2
                        expenseFinal.equals("Rent") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Rent")),
                        expenseFinal.equals("Utilities") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Utilities")),
                        expenseFinal.equals("Phone") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Phone")),
                        expenseFinal.equals("Internet") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Internet")),
                        expenseFinal.equals("Gym") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Gym")),
                        expenseFinal.equals("Food") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Food")),
                        expenseFinal.equals("Gas") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Gas")),
                        expenseFinal.equals("Insurance") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Insurance")),
                        expenseFinal.equals("Car Loan") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("CarLoan")),
                        expenseFinal.equals("Student Loan") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("StudentLoan")),
                        expenseFinal.equals("Charity") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Charity")),
                        expenseFinal.equals("Emergency Fund") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("EmergencyFund")),
                        expenseFinal.equals("Savings") ? String.valueOf(categoryAmount) : cursor.getString(cursor.getColumnIndex("Savings"))
                );

                //if the update was successful and true
                if (isUpdated) {
                    //get the intent needed to pass to the MainActivity class.
                    Intent intent = new Intent(Spent.this, HomeBase.class);
                    intent.putExtra("remainingIncome", income);
                    startActivity(intent);
                    finish();
                    //send an appropriate message to user.
                    Toast.makeText(this, "Successfully spent $" + amountSpent + " on " + expenseFinal + "!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(this, "Failed to update the database!", Toast.LENGTH_SHORT).show();
                    AmountSpent.setText("");
                }
            }
            else
            {
                Toast.makeText(this, "Insufficient funds in " + expenseFinal + "!", Toast.LENGTH_SHORT).show();
                AmountSpent.setText("");
            }
        }
        else
        {
            Toast.makeText(this, "Failed to retrieve budget data!", Toast.LENGTH_SHORT).show();
            AmountSpent.setText("");
        }
        //close the cursor
        if (cursor != null) {
            cursor.close();
        }
    }

    //replace spaces with no spaces for two-worded columns.
    private String replaceSpace(String columnName)
    {
        return columnName.replace(" ", "");
    }

}