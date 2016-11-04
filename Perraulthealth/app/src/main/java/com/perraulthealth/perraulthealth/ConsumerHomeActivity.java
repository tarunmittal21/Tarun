package com.perraulthealth.perraulthealth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import static com.perraulthealth.perraulthealth.R.id.imageViewMap;


public class ConsumerHomeActivity extends AppCompatActivity implements View.OnClickListener{


    private SupportMapFragment mapFragment;
    private static final GeoLocation INITIAL_CENTER = new GeoLocation(37.7789, -122.4017);
    private static final int INITIAL_ZOOM_LEVEL = 14;
    private static final String geofire = "geofire";
    private Circle searchCircle;
    private GeoFire geoFire;
    private GeoQuery geoQuery;
    private Map<String,Marker> markers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_home);
        findViewById(R.id.imageViewMap).setOnClickListener(this);




    }
    @Override
    public void onClick(View v)
    {

        int i = v.getId();


        if(i == imageViewMap)
        {

                Toast.makeText(this, "Welcome map ...tarun", Toast.LENGTH_SHORT).show();
            // Pushing MapView Fragment
             mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    System.out.println("googleMap" + googleMap);
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);



                        LatLng latLngCenter = new LatLng(INITIAL_CENTER.latitude, INITIAL_CENTER.longitude);
                        searchCircle = googleMap.addCircle(new CircleOptions().center(latLngCenter).radius(1000));
                        searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
                        searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, INITIAL_ZOOM_LEVEL));
                        googleMap.addMarker(new MarkerOptions().position(latLngCenter).title("Marker in Sydney"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngCenter));

                        // setup GeoFire
                        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                        Log.d("GeoFire", "onMapReady:" + mRootRef);


                        geoFire = new GeoFire(mRootRef.child(geofire).getRef());
                        Log.d("GeoFire", "onMapReady:mRootRef.child(geofire).getRef()" + geoFire);
                        geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));

                    }

                }
            });

        }

        else
            Toast.makeText(this, "Welcome other than map ...tarun", Toast.LENGTH_SHORT).show();
    }
}
