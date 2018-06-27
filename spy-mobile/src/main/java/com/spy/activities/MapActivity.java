package com.spy.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spyapp.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 111;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        getLocationPermission();
    }

    private void getLocationPermission() {
        String[] permissions = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
        if (isPermissionGranted(ACCESS_FINE_LOCATION) && isPermissionGranted(ACCESS_COARSE_LOCATION)) {
            initMap();
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                initMap();
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng kpi = new LatLng(50.447727, 30.456707);
        googleMap.addMarker(new MarkerOptions()
                .position(kpi)
                .icon(BitmapDescriptorFactory.defaultMarker(HUE_ROSE))
                .title("KPI Maths Olympiad"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.447932, 30.451809))
                .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN))
                .title("Iryna Ziabkina"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.449491, 30.457411))
                .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN))
                .title("Ivan Ivanov"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kpi, 15));
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this.getApplicationContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
