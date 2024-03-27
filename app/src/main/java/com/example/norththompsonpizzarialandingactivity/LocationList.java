package com.example.norththompsonpizzarialandingactivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationList extends AppCompatActivity implements OnMapReadyCallback {

    EditText locationSelect;
    Geocoder geocoder;
    SharedPreferences sharedPreferences;
    GoogleMap myGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        locationSelect = findViewById(R.id.addressET);
        geocoder = new Geocoder(this, Locale.getDefault());
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // Check for permissions to access location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // TextWatcher method to watch what the user is typing for their address then store in saved preferences for later use
        locationSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String address = locationSelect.getText().toString();
                if (!address.isEmpty()){
                    moveToAddressLocation(address);
                    saveSelectedLocation(address);
                }
            }
        });
    }
    // Granted permission by the user and what to do from there
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
    // Save user choice of location in shared preference after they click the checkmark on the keyboard
    private void saveSelectedLocation(String location) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedLocation", location);
        editor.apply();
    }

    // centers the map on the pizzeria location and marks it with a pizza icon and title if clicked on
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myGoogleMap = googleMap;

        LatLng restaurantLocation = new LatLng(50.6761, -120.3408);
        MarkerOptions markerOptions = new MarkerOptions().position(restaurantLocation).title("North Thompson Pizzeria").icon(BitmapDescriptorFactory.fromResource(R.drawable.pepperoni_pizza_rating));
        googleMap.addMarker(markerOptions);
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myGoogleMap.setMyLocationEnabled(true);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }

    // Move to the address location the user enters/ check for empty/list address not found if it doesnt exist
    private void moveToAddressLocation(String address) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(address,1);
            if (!addresses.isEmpty()) {
                Address addr = addresses.get(0);
                LatLng latLng = new LatLng(addr.getLatitude(), addr.getLongitude());

                myGoogleMap.addMarker(new MarkerOptions().position(latLng).title(address));
                myGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Toast.makeText(this,"Address not found",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
