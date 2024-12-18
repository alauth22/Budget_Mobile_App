package com.example.finalproject.SignatureCanvas;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
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
import java.io.IOException;
import java.io.OutputStream;


public class Sign extends AppCompatActivity {

    //get my view!!!
    private SignatureView signatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign);

        //declare variables and map them to the fields
        signatureView = findViewById(R.id.signatureView);
        Button clearButton = findViewById(R.id.clearButton);
        Button saveButton = findViewById(R.id.saveButton);
        ImageView arrow = findViewById(R.id.arrow6);

        //clear signature button
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearSignature();
            }
        });


        //save the signature
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

        //back arrow
        arrow.setOnClickListener(v -> {
            new RotateSideAnimate(arrow);
            finish();
        });
    }


    /*
    Save the images to downloads only when you have the right SDK version.
    All snippets of code were resourced from StackOverFlow, GeeksforGeeks, and ChatGPT.
     */
    private void saveImageToDownloads(Bitmap bitmap) {
        //this I had to google so I got this from the internet.
        //this essentially is if you have the correct SDK version I believe
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageWithMediaStore(bitmap);
        } else {
            Toast.makeText(this, "Must use Android Q or higher for this to work.", Toast.LENGTH_SHORT).show();
        }

    }


    /*
    You need this method to save the image to the downloads folder.
    It involves setting up metadata so this was mostly guided tutorials, stackoverflow, and other resources.
     */
    private void saveImageWithMediaStore(Bitmap bitmap) {

        //I got most of these directions from a stackoverflow example. this will determine where the file goes and how it is saved.
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "signature.png");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        //which directory do you want it to be saved in.
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);


        /*
        gets content and inserts the metadata into the MediaStore on the emulator
         */
        ContentResolver resolver = getContentResolver();
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        }

        //if the uri actually is NOT null (URI: Uniform Resource Identifier) or file creation.
        //write the bitmap to the file.
        if (uri != null)
        {
            //if you can open the outputstream or if it has something in it.
            try (OutputStream outputStream = resolver.openOutputStream(uri)) {
                if (outputStream != null) {
                    //compress the bitmap to the outputstream so that it can ultimately be saved.
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