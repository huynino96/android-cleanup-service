package com.example.firebaseauthproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.firebaseauthproject.models.CleaningSite;
import com.example.firebaseauthproject.models.User;
import com.example.firebaseauthproject.models.UserInformation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Arrays;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserReference;
    private static String TAG = MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCHuKb9Ih7QZr50Hbbl4fUHce5veWsWlN8");
        }


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        user = firebaseAuth.getCurrentUser();
        mUserReference = FirebaseDatabase.getInstance().getReference(user.getUid());

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                LatLng latLng = place.getLatLng();
                LatLng location = new LatLng(latLng.latitude, latLng.longitude);
                CleaningSite cleaningSite = new CleaningSite(place.getName(), latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInformation userProfile = snapshot.getValue(UserInformation.class);
                        DatabaseReference newCleaningSite = mDatabase.child("cleaningSites").push();
                        newCleaningSite.getKey();
                        newCleaningSite.setValue(cleaningSite);
                        mDatabase.child("cleaningSites")
                                .child(Objects.requireNonNull(newCleaningSite.getKey()))
                                .child("owner")
                                .setValue(
                                        new User(
                                                user.getUid(),
                                                userProfile.getUserName(),
                                                user.getEmail(),
                                                userProfile.getUserPhone()
                                        )
                                );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MapsActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(10.7123177, 106.7116815)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
        mDatabase.child("cleaningSites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    CleaningSite cleaningSite = snapshot.getValue(CleaningSite.class);
                    LatLng position = new LatLng(cleaningSite.getLatitude(), cleaningSite.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(position).title(cleaningSite.getSiteName()));
                    mMap.setOnMarkerClickListener(marker -> {
                        Intent intent = new Intent(MapsActivity.this, CleaningSiteActivity.class);
                        String uid = null;
                        for (DataSnapshot snap: dataSnapshot.getChildren()) {
                            CleaningSite item = snap.getValue(CleaningSite.class);
                            if (item.getSiteName().equals(marker.getTitle())) {
                                uid = snap.getKey();
                            }
                        }
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        return false;
                    });
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MapsActivity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}