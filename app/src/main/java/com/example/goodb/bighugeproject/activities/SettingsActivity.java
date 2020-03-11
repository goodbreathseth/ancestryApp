package com.example.goodb.bighugeproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.ui.main.LoginFragment;
import com.example.goodb.bighugeproject.ui.main.MapFragment;
import com.google.android.gms.maps.GoogleMap;

import java.util.concurrent.ExecutionException;

import results.LoginResult;

public class SettingsActivity extends AppCompatActivity {

    static Model model = Model.getInstance();
    private Spinner lifeStoryLinesSpinner, familyTreeLinesSpinner, spouseLinesSpinner, mapTypeSpinner;
    private Switch lifeStoryLinesSwitch, familyTreeLinesSwitch, spouseLinesSwitch;
    private LinearLayout resyncLayout, logoutLayout;
    private boolean lifeStoryLinesSpinnerIsFirstTime, familyTreeLinesSpinnerIsFirstTime,
            spouseLinesSpinnerIsFirstTime, mapTypeSpinnerIsFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        lifeStoryLinesSpinnerIsFirstTime = true;
        familyTreeLinesSpinnerIsFirstTime = true;
        spouseLinesSpinnerIsFirstTime = true;
        mapTypeSpinnerIsFirstTime = true;

        // Initialize all the widgets that a user can interact with
        lifeStoryLinesSpinner = findViewById(R.id.lifeStoryLinesSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.line_colors, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lifeStoryLinesSpinner.setAdapter(adapter1);
        lifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lifeStoryLinesSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        familyTreeLinesSpinner = findViewById(R.id.familyTreeLinesSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.line_colors, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyTreeLinesSpinner.setAdapter(adapter2);
        familyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                familyTreeLinesSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spouseLinesSpinner = findViewById(R.id.spouseLinesSpinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.line_colors, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spouseLinesSpinner.setAdapter(adapter3);
        spouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spouseLinesSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.map_type, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter4);
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mapTypeSpinnerSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Initialize the switches and add their listeners
        lifeStoryLinesSwitch = findViewById(R.id.lifeStoryLinesSwitch);
        lifeStoryLinesSwitch.setChecked(model.getSettingsData().isShowLifeStoryLines());
        lifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getSettingsData().setShowLifeStoryLines(isChecked);
            }
        });
        familyTreeLinesSwitch = findViewById(R.id.familyTreeLinesSwitch);
        familyTreeLinesSwitch.setChecked(model.getSettingsData().isShowFamilyTreeLines());
        familyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getSettingsData().setShowFamilyTreeLines(isChecked);
            }
        });
        spouseLinesSwitch = findViewById(R.id.spouseLinesSwitch);
        spouseLinesSwitch.setChecked(model.getSettingsData().isShowSpouseLines());
        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getSettingsData().setShowSpouseLines(isChecked);
            }
        });

        // Initialize the resync layout so a user can click on it to then enter the
        // server proxy again to resync all the data
        resyncLayout = findViewById(R.id.resyncLayout);
        resyncLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    resyncLayoutClicked(v);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Initialize the logout layout so a user can click on it to then have
        // their cache data removed and switch to the login fragment
        logoutLayout = findViewById(R.id.logoutLayout);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutLayoutClicked(v);
            }
        });

    }

    private void lifeStoryLinesSpinnerSelected(int position) {
        // Initially opening the settings menu will call this method.  Don't call it
        // if it is being initially opened
        if (lifeStoryLinesSpinnerIsFirstTime) {
            lifeStoryLinesSpinnerIsFirstTime = false;
            lifeStoryLinesSpinner.setSelection(model.getSettingsData().getLifeStoryLineColor());
        }
        else {
            model.getSettingsData().setLifeStoryLineColor(position);
        }

    }

    private void familyTreeLinesSpinnerSelected(int position) {
        // Initially opening the settings menu will call this method.  Don't call it
        // if it is being initially opened
        if (familyTreeLinesSpinnerIsFirstTime) {
            familyTreeLinesSpinnerIsFirstTime = false;
            familyTreeLinesSpinner.setSelection(model.getSettingsData().getFamilyTreeLineColor());
        }
        else {
            model.getSettingsData().setFamilyTreeLineColor(position);
        }
    }

    private void spouseLinesSpinnerSelected(int position) {
        // Initially opening the settings menu will call this method.  Don't call it
        // if it is being initially opened
        if (spouseLinesSpinnerIsFirstTime) {
            spouseLinesSpinnerIsFirstTime = false;
            spouseLinesSpinner.setSelection(model.getSettingsData().getSpouseLineColor());
        }
        else {
            model.getSettingsData().setSpouseLineColor(position);
        }
    }

    private void mapTypeSpinnerSelected(int position) {
        // Initially opening the settings menu will call this method.  Don't call it
        // if it is being initially opened
        if (mapTypeSpinnerIsFirstTime) {
            mapTypeSpinnerIsFirstTime = false;
            mapTypeSpinner.setSelection(model.getSettingsData().getMapType());
        }
        else {
            model.getSettingsData().updateMapType(position);
        }
    }

    private void logoutLayoutClicked(View v) {
        model.clearInstance();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void resyncLayoutClicked(View v) throws ExecutionException, InterruptedException {
        model.clearInstance();
        model.setAuthToken("");
        LoginFragment loginFrag = new LoginFragment();
        model.setUserName("resync");
        LoginResult r = loginFrag.waitForAsyncToFinish(model.getHost(), model.getPort(), model.getUserName(), model.getLoginRequest());

        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
