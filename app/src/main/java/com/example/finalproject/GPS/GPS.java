package com.example.finalproject.GPS;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;
import com.example.finalproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.finalproject.databinding.ActivityGpsBinding;
import java.util.List;

public class GPS extends FragmentActivity implements OnMapReadyCallback {


    //declare instance of Google Map
    private GoogleMap mMap;
    private ActivityGpsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGpsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //check for location permission, so this will give user permission popup
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //get my blue dot!!!!! Cannot get blue dot
            //To my knowledge this does not work since I am on an emulator and I do not have the GPS chip.
            mMap.setMyLocationEnabled(true);

        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        //get green bay location
        LatLng nwtc = new LatLng(44.52599, -88.10649);
        //move the camera on it and display it at a zoom degree of 14.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nwtc, 11));

        DBHelperMap helper = new DBHelperMap(this);

        //get all the lists and call the database helper.
        List<Integer> ids = helper.getLocationIds();
        List<LatLng> locations = helper.getLocations();
        List<String> names = helper.getNames();

        //make sure that they are the same size, meaning same number of entries.
        if (locations.size() == names.size()) {
            for (int i = 0; i < locations.size(); i++) {
                LatLng position = locations.get(i);
                String name = names.get(i);
                String service = helper.getServices(ids.get(i)).toString();


                //get my BitmapDrawable ready to scale down my downloaded icon. so we need to access the resource, and a
                //bitmap because it is a drawable object on the screen.
                BitmapDrawable bp = (BitmapDrawable) getResources().getDrawable(R.drawable.icon);
                //get my bitmap from the drawable (extract it)
                Bitmap b = bp.getBitmap();
                //scale the bitmap down to a smaller size, using the Bitmap method
                Bitmap smallIcon = Bitmap.createScaledBitmap(b, 100, 100, false);
                //set it equal to a smaller marker
                //BitmapDescriptor is used by the Google Maps API to represent the bitmap image.
                BitmapDescriptor smallMarker = BitmapDescriptorFactory.fromBitmap(smallIcon);

                //now ensure that you get each position, name, and label the corresponding service, and add the marker.
                MarkerOptions options = new MarkerOptions()
                        .position(position)
                        .title(name)
                        .snippet("Services: " + service)
                        .icon(smallMarker);
                mMap.addMarker(options);

            }
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }


}