package com.example.finalproject.SignatureCanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


//remember we're using the Android Canvas API here


public class SignatureView extends View {

    private Paint paint;
    private Path path; // Path to draw the signature
    private List<Path> paths = new ArrayList<>(); // List of paths to handle multiple strokes

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK); // Signature color
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f); // Stroke width for the signature
        paint.setStyle(Paint.Style.STROKE); // Only the stroke (no fill)
        paint.setStrokeCap(Paint.Cap.ROUND); // Rounded edges for a smoother stroke
        paint.setStrokeJoin(Paint.Join.ROUND); // Rounded joins for smoother lines
        path = new Path(); // Initialize path for the first touch
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all paths (multiple strokes from the user)
        for (Path p : paths) {
            canvas.drawPath(p, paint);
        }

        // Draw the current path (if the user is still drawing)
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path(); // Start a new path for the new stroke
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY); // Draw a line to the new touch point
                break;
            case MotionEvent.ACTION_UP:
                paths.add(path); // Save the path when the user lifts their finger
                path = new Path(); // Start a new path for the next stroke
                break;
        }
        invalidate(); // Redraw the view after every touch event
        return true;
    }

    // Optionally: Method to clear the signature
    public void clearSignature() {
        paths.clear();
        invalidate(); // Redraw the view to clear the signature
    }
}
