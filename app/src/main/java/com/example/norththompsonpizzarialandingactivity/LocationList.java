package com.example.norththompsonpizzarialandingactivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationList extends AppCompatActivity {

    AutoCompleteTextView locationSelect;
    Geocoder geocoder;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        locationSelect = findViewById(R.id.locationAutoComplete);
        geocoder = new Geocoder(this, Locale.getDefault());
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        locationSelect.setAdapter(adapter);

        // Check for permissions to access location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // on Item select listener for autofill edit text, post toast after of the selected location
        locationSelect.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLocation = (String) parent.getItemAtPosition(position);
            saveSelectedLocation(selectedLocation);
            Toast.makeText(this, "Selected Location: " + selectedLocation, Toast.LENGTH_SHORT).show();
        });

        // TextWatcher method to watch what the user is typing to autofill and suggest after the specified length greather than 2
        locationSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    searchForLocation(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Granted permission by the user and what to do from there
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    // use geocoder to search for addresses
    private void searchForLocation(String query) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(query, 10);
            adapter.clear();
            for (Address address : addresses) {
                adapter.add(address.getAddressLine(0));
            }
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save user choice of location in shared preference after they click the checkmark on the keyboard
    private void saveSelectedLocation(String location) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedLocation", location);
        editor.apply();
    }
}
