package com.example.goodb.bighugeproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;

public class PersonActivity extends AppCompatActivity {

    static Model model = Model.getInstance();
    private TextView firstName, lastName, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        // Set all the textViews so they represent
        // the information of the person clicked on
        firstName = findViewById(R.id.firstName);
        firstName.setText(model.getPersonModel().getFirstName().toUpperCase());

        lastName = findViewById(R.id.lastName);
        lastName.setText(model.getPersonModel().getLastName().toUpperCase());

        gender = findViewById(R.id.gender);

        if (model.getPersonModel().getGender().toLowerCase().equals("f"))
            gender.setText("FEMALE");
        else
            gender.setText("MALE");
    }
}
