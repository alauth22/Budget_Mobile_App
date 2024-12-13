package com.example.finalproject.Animation;

import android.os.Handler;
import android.os.Looper;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CircularAnimation {

    //class constructor to get the final progress value
    public CircularAnimation(CircularProgressIndicator cp, int finalProgressValue) {
        //call the function below for the animation.
        startCircularAnimation(cp, finalProgressValue);
    }


    /*
    Method to start the circular animation based off of the difference between the starting income and the current income (spent on).
     */
    private void startCircularAnimation(CircularProgressIndicator cp, int finalProgressValue)
    {
        //handler and runnable example
        new Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //rotateSideAnimate progress from current to the new income value.
                        cp.setProgressCompat(finalProgressValue, true);
                    }
                }, 200);
    }

}