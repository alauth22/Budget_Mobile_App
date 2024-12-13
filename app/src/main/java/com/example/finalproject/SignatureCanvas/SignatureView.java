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
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;


//remember we're using the Android Canvas API here
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
        initialize();
    }

    //initialize the paint and path
    private void initialize() {
        paint = new Paint();
        //color of the signature
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        //stroke width for the signature
        paint.setStrokeWidth(8f);
        //no fill in the stroke
        paint.setStyle(Paint.Style.STROKE);
        //rounded edges and joins
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        //I want to initialize path at the first touch
        path = new Path();
    }


    //onDraw to actually now draw the signature. Canvas is used.
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //draw all paths which will be each stroke the user draws.
        for (Path p : paths) {
            //so for each path that the person's finger moves, draw it with all the draw requirements
            canvas.drawPath(p, paint);
        }

        //draw the current path
        canvas.drawPath(path, paint);
    }


    //need to be able to actually touch the screen to draw the signature
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //these need to be datatypes of float for the built-in
        //path functions - stackoverflow and geeksforgeeks all said you need floats for these.
        float PositionX = event.getX();
        float PositionY = event.getY();

        switch (event.getAction()) {
            //when the  person first touches at any point in the screen and starts moving.
            case MotionEvent.ACTION_MOVE:
                //document the moving position
                path.lineTo(PositionX, PositionY);
                break;
            case MotionEvent.ACTION_DOWN:
                //start new path when the person touches the screen
                path = new Path();
                //document movements
                path.moveTo(PositionX, PositionY);
                break;
            case MotionEvent.ACTION_UP:
                //so when the user lifts their finger up, we NEED the drawing to remain there.
                //so we need to add the previously drawn paths to the arrayList
                paths.add(path);
                //now add a new path if the user starts with an upward stroke
                path = new Path();
                //document movements
                path.moveTo(PositionX, PositionY);
                break;
        }

        //learned the hard way you need this to redraw the view after every touch event
        invalidate();
        //return true after this was a success.
        return true;
    }

    /*
    Method to clear the signature.
     */
    public void clearSignature() {
        paths.clear();
        invalidate(); // Redraw the view to clear the signature
    }


    //we need this when the person will want to save their image. Bitmaps are important!
    public Bitmap getBitmap() {
        //create a bitmap with the same width and height as the SignatureView for a pdf? It's simplier that way.
        //basically you NEED a bitmap for your view specifically
        //stackoverflow helped with this.
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //new canvas to draw on the bitmap
        Canvas canvas = new Canvas(bitmap);

        //draw the current view content onto the canvas.
        draw(canvas);
        return bitmap;
    }


}
