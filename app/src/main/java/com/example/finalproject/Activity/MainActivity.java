package com.example.finalproject.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, newuserButton;



    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //grabs the activity_main.xml where all the views are located.
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //initialize my controls here and tie them to controls on the main activity
        emailEditText = findViewById(R.id.emailEditText2);
        passwordEditText = findViewById(R.id.passwordEditText2);
        loginButton = findViewById(R.id.loginButton2);
        newuserButton = findViewById(R.id.newuserButton2);


        // Set login button onClickListener
        loginButton.setOnClickListener(v -> {
            //grab the email and password from the edit text fields and store them in variables
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //make sure that you have both an email and a password
            if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(MainActivity.this, "Please enter both an email and a password", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                //calls my method down below that will sign in the user in Firebase
                signInWithEmail_Password(email, password);
            }

        });



        //button used to register an account and is tied to the Firebase authentication account to this project
        newuserButton.setOnClickListener( v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both an email and a password", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                //calls my method down below that will add a new user in Firebase
                createUserWithEmail_Password(email, password);
            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }



    //method to sign in the user in Firebase Authentication
    private void signInWithEmail_Password(String email, String password) {
        //this is Firebase Authentication type
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        //if the sign in is successful
                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        //go to the greeting activity and show the user
                        Intent intent = new Intent(MainActivity.this, HomeBase.class);
                        startActivity(intent);

                    } else {
                        //If sign in fails, display a message and do not show the user the greeting
                        Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //method to add a new user to Firebase Authentication
    private void createUserWithEmail_Password(String email, String password) {
        //again use this method from Firebase Authentication to create a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        //Sign in was a success
                        Toast.makeText(MainActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeBase.class);
                        startActivity(intent);

                    }
                    else
                    {
                        //Sign in fails, display a message
                        Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    

}