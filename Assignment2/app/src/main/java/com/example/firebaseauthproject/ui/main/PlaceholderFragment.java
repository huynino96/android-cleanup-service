package com.example.firebaseauthproject.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebaseauthproject.MapsActivity;
import com.example.firebaseauthproject.R;
import com.example.firebaseauthproject.adapter.VolunteerListViewAdapter;
import com.example.firebaseauthproject.models.CleaningSite;
import com.example.firebaseauthproject.models.User;
import com.example.firebaseauthproject.models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView volunteerListView;
    private VolunteerListViewAdapter volunteerListViewAdapter;
    private ArrayList<User> volunteers;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cleaning_site, container, false);
        final TextView siteNameTextView = root.findViewById(R.id.site_name);
        final TextView nameTextView = root.findViewById(R.id.name);
        final TextView emailTextView = root.findViewById(R.id.email);
        final TextView phoneTextView = root.findViewById(R.id.phone);

        Intent intent = getActivity().getIntent();
        String uid = intent.getStringExtra("uid");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("cleaningSites").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CleaningSite cleaningSite = dataSnapshot.getValue(CleaningSite.class);
                UserInformation userInformation = dataSnapshot.child("owner").getValue(UserInformation.class);

                int index = 1;
                if (getArguments() != null) {
                    index = getArguments().getInt(ARG_SECTION_NUMBER);
                }
                if (index == 1) {
                    siteNameTextView.setText("Site Name: " + cleaningSite.getSiteName());
                    nameTextView.setText("Name: " + userInformation.getUserName());
                    emailTextView.setText("Email: " + user.getEmail());
                    phoneTextView.setText("Phone: " + userInformation.getUserPhone());
                }

                if (index == 2) {
                    volunteers = new ArrayList<>();

                    for (DataSnapshot snapshot: dataSnapshot.child("volunteers").getChildren()) {
                        User user = snapshot.getValue(User.class);
                        volunteers.add(user);
                    }

                    volunteerListViewAdapter = new VolunteerListViewAdapter(volunteers);

                    volunteerListView = getActivity().findViewById(R.id.volunteers);
                    volunteerListView.setAdapter(volunteerListViewAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}