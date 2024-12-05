package com.example.finalproject.Animation;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CircularAnimation {

    final Handler handler = new Handler();
    final int animationDuration = 200; // milliseconds for each jiggle


    public CircularAnimation(CircularProgressIndicator cp, int finalProgressValue) {

        startCircularAnimation(cp, finalProgressValue);
    }


    private void startCircularAnimation(CircularProgressIndicator cp, int finalProgressValue)
    {
        new Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        // RotateSideAnimate progress from current to the new value
                        cp.setProgressCompat(finalProgressValue, true);
                    }
                }, 200);
    }


}
