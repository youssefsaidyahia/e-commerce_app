package com.example.android.project_mc.buyer;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.project_mc.Model.myLocationListener;
import com.example.android.project_mc.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    EditText addressText;
    LocationManager locManager;
    myLocationListener locListener;
    Button getLocation;
    private int id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addressText=findViewById(R.id.editText);
        getLocation=findViewById(R.id.b1) ;
        id=getIntent().getExtras().getInt("id");
        locListener=new myLocationListener(getApplicationContext());
        locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);
        }
        catch (SecurityException e){
            Toast.makeText(getApplicationContext(),"you are not to allowed to access",Toast.LENGTH_LONG).show();
        }
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;
                try {
                    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch (SecurityException x) {
                    Toast.makeText(getApplicationContext(), "You did not allow to access the current location"
                            , Toast.LENGTH_LONG).show();
                }
                if (loc != null) {
                    LatLng myPosition = new LatLng(loc.getLatitude(), loc.getLongitude());
                    try {
                        addressList = coder.getFromLocation(myPosition.latitude, myPosition.longitude, 1);
                        if (!addressList.isEmpty()) {
                            String address = "";
                            for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++)
                                address += addressList.get(0).getAddressLine(i) + ", ";

                            mMap.addMarker(new MarkerOptions().position(myPosition)
                                    .title("MY location").snippet(address)).setDraggable(true);

                            addressText.setText(address);

                        }
                    } catch (IOException e) {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("my location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
                } else
                    Toast.makeText(getApplicationContext(), "Please wait untill your position is determined",
                            Toast.LENGTH_LONG).show();
            }  });
      mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
          @Override
          public void onMarkerDragStart(Marker marker) {

          }

          @Override
          public void onMarkerDrag(Marker marker) {

          }

          @Override
          public void onMarkerDragEnd(Marker marker) {
              Geocoder coder = new Geocoder(getApplicationContext());
              List<Address> addressList;
              try {
                  addressList = coder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                  if (!addressList.isEmpty()) {
                      String address = "";
                      for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++)
                          address+= addressList.get(0).getAddressLine(i) + ", ";
                      addressText.setText(address);
                  } else {
                      Toast.makeText(getApplicationContext(), "NO Adress for this location", Toast.LENGTH_LONG).show();
                      addressText.getText().clear();
                  }
              } catch (IOException e) {
                  Toast.makeText(getApplicationContext(), "check your network", Toast.LENGTH_LONG).show();
              }
          }
      });
        Button b=(Button)findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=addressText.getText().toString();
                if(id==1) {
                    Intent i = new Intent(MapsActivity.this, SettingsActivity.class);
                    i.putExtra("address", s);
                    startActivity(i);
                }
                else if(id==2){
                    Intent i = new Intent(MapsActivity.this, Confirmed_order.class);
                    i.putExtra("address", s);
                    startActivity(i);
                }
            }
        });
    }
}