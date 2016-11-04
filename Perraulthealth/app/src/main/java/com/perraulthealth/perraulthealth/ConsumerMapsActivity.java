package com.perraulthealth.perraulthealth;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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

public class ConsumerMapsActivity extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
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
        //findViewById(R.id.imageViewMap).setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onClick(View v)
    {

        int i = v.getId();


        if(i == imageViewMap)
        {

            Toast.makeText(this, "Welcome map ...tarun", Toast.LENGTH_SHORT).show();

        }

        else
            Toast.makeText(this, "Welcome other than map ...tarun", Toast.LENGTH_SHORT).show();
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
        System.out.println("googleMap" + googleMap);
        mMap = googleMap;
        if (mMap != null) {

            googleMap.getUiSettings().setAllGesturesEnabled(true);



            LatLng latLngCenter = new LatLng(INITIAL_CENTER.latitude, INITIAL_CENTER.longitude);
            this.searchCircle = this.mMap.addCircle(new CircleOptions().center(latLngCenter).radius(1000));
            this.searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
            this.searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, INITIAL_ZOOM_LEVEL));
            this.mMap.addMarker(new MarkerOptions().position(latLngCenter).title("Marker in Sydney"));
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngCenter));

            // setup GeoFire
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            Log.d("GeoFire", "onMapReady:" + mRootRef);


            geoFire = new GeoFire(mRootRef.child(geofire).getRef());
            Log.d("GeoFire", "onMapReady:mRootRef.child(geofire).getRef()" + geoFire);
            geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));

        }

        }

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
}
