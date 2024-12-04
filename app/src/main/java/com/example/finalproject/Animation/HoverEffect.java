package com.example.finalproject.Animation;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class HoverEffect {

    private ImageView image;
    private Handler handler;
    private Runnable jiggleRunnable;

    // Constructor to initialize the image and start hover effect
    public HoverEffect(ImageView image) {
        this.image = image;
        this.handler = new Handler();
        HoverListener();
    }

    // Hover listener for hover events
    public void HoverListener() {
        image.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_HOVER_ENTER) {
                    // Start the jiggling effect when hover begins
                    startJiggle();
                } else if (event.getAction() == MotionEvent.ACTION_HOVER_EXIT) {
                    // Stop the jiggling effect when hover ends
                    stopJiggle();
                }
                return true;
            }
        });
    }

    // Start the jiggling effect using a Handler and Runnable
    private void startJiggle() {
        final float jiggleDistance = 20f; // Distance for jiggling
        final int delay = 50; // Delay in milliseconds between each jiggle

        // Runnable to move the image back and forth
        jiggleRunnable = new Runnable() {
            private boolean moveRight = true;

            @Override
            public void run() {
                // Move the image to the right or left based on the current state
                if (moveRight) {
                    image.animate().translationXBy(jiggleDistance).setDuration(delay).start();
                } else {
                    image.animate().translationXBy(-jiggleDistance).setDuration(delay).start();
                }

                // Toggle the movement direction for the next iteration
                moveRight = !moveRight;

                // Repeat the jiggle effect
                handler.postDelayed(this, delay);
            }
        };

        // Start the jiggling by posting the Runnable
        handler.post(jiggleRunnable);
    }

    // Stop the jiggling effect by removing the Runnable
    private void stopJiggle() {
        if (jiggleRunnable != null) {
            handler.removeCallbacks(jiggleRunnable); // Stop the jiggle by removing the Runnable
            // Optionally reset the position of the image to its original state
            image.animate().translationX(0f).setDuration(200).start();
        }
    }
}