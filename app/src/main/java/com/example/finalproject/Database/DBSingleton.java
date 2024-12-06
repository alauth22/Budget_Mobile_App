package com.example.finalproject.Database;
import android.content.Context;

//OLD CODE

/*
Singleton ensures that only one DBHelper instance exists throughout the application's lifecycle.
Helps prevent database helper instances from being created randomly and all over the place.

I also encapsulate here by making the dbhelper instance private and only accessible via the DBSingleton.getInstance method
which is my main controller.
 */
//
//public class DBSingleton {
//    private static DBHelper  instance;
//
//    //private constructor to prevent instantiation from outside the classes
//   private DBSingleton(Context applicationContext) {}
//
//    // Public static method to provide a single instance
//    public static DBHelper getInstance(Context context) {
//        if (instance == null) {
//            synchronized (DBSingleton.class) {
//                if (instance == null) {
//                    instance = new DBHelper(context.getApplicationContext());
//                }
//            }
//        }
//        return instance;
//    }
//
//}
