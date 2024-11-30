package com.example.finalproject.Database;
import android.content.Context;

public class DBSingleton {
    private static DBHelper  instance;
    private DBHelper dbHelper;

    //private constructor to prevent instantiation from outside the classes
   private DBSingleton(Context applicationContext) {}

    // Public static method to provide a single instance
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBSingleton.class) {
                if (instance == null) {
                    instance = new DBHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

}
