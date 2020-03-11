package com.example.goodb.bighugeproject.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.ui.main.MapFragment;

public class EventActivity extends AppCompatActivity {

    static Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        model.setToolbarVisible(false);
        FragmentManager fm = getSupportFragmentManager();
        Fragment mapFragment = new MapFragment();
        fm.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
    }
}
