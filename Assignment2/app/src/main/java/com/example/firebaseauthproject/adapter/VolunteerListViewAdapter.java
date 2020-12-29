package com.example.firebaseauthproject.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firebaseauthproject.R;
import com.example.firebaseauthproject.models.User;

import java.util.ArrayList;

public class VolunteerListViewAdapter extends BaseAdapter {

    final ArrayList<User> users;

    public VolunteerListViewAdapter(ArrayList<User> listUser) {
        this.users = listUser;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewUser;
        if (convertView == null) {
            viewUser = View.inflate(parent.getContext(), R.layout.volunteer_view, null);
        } else viewUser = convertView;

        User user = (User) getItem(position);
        ((TextView) viewUser.findViewById(R.id.name)).setText(String.format("Name = %s", user.getName()));
        ((TextView) viewUser.findViewById(R.id.email)).setText(String.format("Email : %s", user.getEmail()));
        ((TextView) viewUser.findViewById(R.id.phone)).setText(String.format("Phone: %s", user.getPhone()));


        return viewUser;
    }
}
