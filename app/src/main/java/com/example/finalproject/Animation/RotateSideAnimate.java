package com.example.finalproject.Animation;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class RotateSideAnimate {

    //declare variables.
    private final View icon;
    final Handler handler = new Handler();
    //milliseconds for each jiggle
    final int animationDuration = 200;

    //constructor that will accept a View datatype
    public RotateSideAnimate(View icon)
    {
        this.icon = icon;
        startJiggleAnimation();
    }

    /*
    Think of the runnable as a block of code that will be run or executed later, which will
    be executed by the handler.
    So logically inside the run() method is where I define the running logic.
     */
    private void startJiggleAnimation() {
        //declare my runnable here
        final Runnable jiggleRunnable = new Runnable() {
            @Override
            public void run() {
                //provides the rotation animation to a view
                RotateAnimation rotate = new RotateAnimation(
                    //right here is defining what kind of rotation it is to be done.
                    //tilt to the left and to the right 15 degrees.
                    -15f, 15f,
                    //allow the tilt to be from the central point of view
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                );

                //do this for 200 milliseconds
                rotate.setDuration(animationDuration);

                //reverse direction after completing one cycle
                rotate.setRepeatMode(Animation.REVERSE);

                //repeat only once for now
                rotate.setRepeatCount(1);

                //now start the animation for that icon
                icon.startAnimation(rotate);
            }
        };

        //animation scheduled to be executed by the handler on the main thread (UI)
        handler.post(jiggleRunnable);

    };

}