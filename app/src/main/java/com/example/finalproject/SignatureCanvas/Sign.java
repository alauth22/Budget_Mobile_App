package com.example.finalproject.SignatureCanvas;

import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Animation.RotateSideAnimate;
import com.example.finalproject.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Sign extends AppCompatActivity {

    private SignatureView signatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign);


        signatureView = findViewById(R.id.signatureView);
        Button clearButton = findViewById(R.id.clearButton);
        Button saveButton = findViewById(R.id.saveButton);
        ImageView arrow = findViewById(R.id.arrow6);

        //Clear the signature when the "Clear Signature" button is clicked
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearSignature();
            }
        });

        //Save the signature when the "Save Signature" button is clicked
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try to get the drawing as a Bitmap
                Bitmap bitmap = signatureView.getBitmap();
                //now you have the bitmap, save it to the downloads folder.
                saveImageToDownloads(bitmap);
                //go back to the home base activity
                finish();
            }

        });

        arrow.setOnClickListener(v -> {
            RotateSideAnimate rotateSideAnimate = new RotateSideAnimate(arrow);
            finish();
        });
    }



    private void saveImageToDownloads(Bitmap bitmap) {
        //this I had to google so I got this from the internet.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageWithMediaStore(bitmap);
        } else {
            Toast.makeText(this, "Must use Android Q or higher for this to work.", Toast.LENGTH_SHORT).show();
        }

}


    private void saveImageWithMediaStore(Bitmap bitmap) {

        //I got most of these directions from a random stackoverflow example.
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "signature.png"); // File name
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");       // File type
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS); // Target directory


        /*
        explain what a ContentResolver is
         */
        ContentResolver resolver = getContentResolver();
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        }

        /*
        OutputStream =
        Explain exactly what this code does.
        what is a URI:
         */
        if (uri != null) {
            try (OutputStream outputStream = resolver.openOutputStream(uri)) {
                if (outputStream != null)
                {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    Toast.makeText(this, "Signature saved to Downloads", Toast.LENGTH_SHORT).show();
                }
            }
            catch (IOException e)
            {
                Toast.makeText(this, "Failed to save signature", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Failed to create file URI", Toast.LENGTH_SHORT).show();
        }

    }


}
