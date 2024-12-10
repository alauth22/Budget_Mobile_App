package com.example.finalproject.SignatureCanvas;

import android.content.Context;
import android.graphics.Bitmap;
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
//so that will count towards working with an
public class SignatureView extends View {

    //need to use paint here for the drawing of the signature.
    private Paint paint;

    //this is the Path class that will trace where the finger draws the signature
    private Path path;

    //here is my arrayList that will keep track of everywhere the user draws or the paths
    private List<Path> paths = new ArrayList<>();

    //class constructor
    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //initialize the paint and path
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK); // Signature color
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8f); // Stroke width for the signature
        paint.setStyle(Paint.Style.STROKE); // Only the stroke (no fill)
        paint.setStrokeCap(Paint.Cap.ROUND); // Rounded edges for a smoother stroke
        paint.setStrokeJoin(Paint.Join.ROUND); // Rounded joins for smoother lines
        path = new Path(); // Initialize path for the first touch
    }


    //onDraw here can count towards my custom view object
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all paths (multiple strokes from the user)
        for (Path p : paths) {

            //so for each path that the person's finger moves, draw it wtih all the draw requirements
            canvas.drawPath(p, paint);
        }

        // Draw the current path (if the user is still drawing)
        canvas.drawPath(path, paint);
    }


    //need to be able to actually touch the screen to draw the signature
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //these need to be datatypes of float for the built-in
        //Path functions
        float PositionX = event.getX();
        float PositionY = event.getY();

        switch (event.getAction()) {

            //when teh person first touches at any point in the screen and starts moving.
            case MotionEvent.ACTION_MOVE:
                path.lineTo(PositionX, PositionY);
                break;
            case MotionEvent.ACTION_DOWN:
                path = new Path(); // Start a new path for the new stroke
                path.moveTo(PositionX, PositionY);
                break;
            case MotionEvent.ACTION_UP:
                //so when the user lifts their finger up, we NEED the drawing to remain there.
                //so we need to add the previously drawn paths to the arrayList
                paths.add(path);
                //now add a new path
                path = new Path(); // Start a new path for the next stroke
                path.moveTo(PositionX, PositionY);
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



    public Bitmap getBitmap() {
        // Create a bitmap with the same width and height as the SignatureView
        //basically you NEED a bitmap for your view specifically
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        // Create a canvas to draw on the bitmap (you need teh actual canvas)
        Canvas canvas = new Canvas(bitmap);
        // Draw the current view content onto the canvas (captures the signature)
        draw(canvas);
        return bitmap;
    }


}
