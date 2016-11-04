package com.perraulthealth.perraulthealth.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.perraulthealth.perraulthealth.R;

import java.util.Map;

;


/**
 * Created by sutu on 11/2/2016.
 */

public class MapsFragment  extends Fragment implements GeoQueryEventListener {

    private MapFragment mMapFragment;

    private static final GeoLocation INITIAL_CENTER = new GeoLocation(37.7789, -122.4017);
        private static final int INITIAL_ZOOM_LEVEL = 14;
       // private static final String GEO_FIRE_DB = "https://perraulthealth-cfc33.firebaseio.com";
       // private static final String GEO_FIRE_REF = GEO_FIRE_DB + "/geofire";
        private static final String geofire = "geofire";
        private GoogleMap mMap;
        private MapView mMapView;
        private Circle searchCircle;
        private GeoFire geoFire;
        private GeoQuery geoQuery;
        private Map<String,Marker> markers;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            FragmentManager fm = getFragmentManager();

            mMapFragment = (MapFragment) fm.findFragmentById(R.id.map);


            View rootView = inflater.inflate(R.layout.fragment_map, container, false);



            mMapFragment.getMapAsync(new OnMapReadyCallback() {


                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    System.out.println("googleMap" + mMap);

                    // Add a marker in Sydney and move the camera
                    // LatLng sydney = new LatLng(-34, 151);
                    LatLng latLngCenter = new LatLng(INITIAL_CENTER.latitude, INITIAL_CENTER.longitude);
                    searchCircle = mMap.addCircle(new CircleOptions().center(latLngCenter).radius(1000));
                    searchCircle.setFillColor(Color.argb(66, 255, 0, 255));
                    searchCircle.setStrokeColor(Color.argb(66, 0, 0, 0));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, INITIAL_ZOOM_LEVEL));
                    mMap.addMarker(new MarkerOptions().position(latLngCenter).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngCenter));

                    // setup GeoFire
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    Log.d("GeoFire", "onMapReady:" + mRootRef);


                    geoFire = new GeoFire(mRootRef.child(geofire).getRef());
                    Log.d("GeoFire", "onMapReady:mRootRef.child(geofire).getRef()" + geoFire);
                    geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));


                    // radius in km
                    //geoQuery = geoFire.queryAtLocation(INITIAL_CENTER, 1);
                    // setup markers
                   // markers = new HashMap<String, Marker>();
                }
            });
return rootView;
        }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

        @Override
        public void onStop() {
            super.onStop();
            // remove all event listeners to stop updating in the background
            this.geoQuery.removeAllListeners();
            for (Marker marker: this.markers.values()) {
                marker.remove();
            }
            this.markers.clear();
        }

        @Override
        public void onStart() {
            super.onStart();
            // add an event listener to start updating locations again
           //this.geoQuery.addGeoQueryEventListener(this);
        }

        @Override
        public void onKeyEntered(String key, GeoLocation location) {
            // Add a new marker to the map
            Marker marker = this.mMap.addMarker(new MarkerOptions().position(new LatLng(location.latitude, location.longitude)));
            this.markers.put(key, marker);
        }

        @Override
        public void onKeyExited(String key) {
            // Remove any old marker
            Marker marker = this.markers.get(key);
            if (marker != null) {
                marker.remove();
                this.markers.remove(key);
            }
        }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        // Move the marker
        Marker marker = this.markers.get(key);

    }

    @Override
        public void onGeoQueryReady() {
        }

    @Override
    public void onGeoQueryError(DatabaseError error) {

    }

}

