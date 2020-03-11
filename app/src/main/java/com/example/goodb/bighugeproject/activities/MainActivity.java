package com.example.goodb.bighugeproject.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.ui.main.LoginFragment;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    static Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model.setToolbarVisible(true);


        // Set up the special pictures and icons to use
        setContentView(R.layout.main_activity);
        Iconify.with(new FontAwesomeModule());
        model.setMainActivityContext(MainActivity.this);

        // Load up the login fragment first
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new LoginFragment();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();

    }

}
