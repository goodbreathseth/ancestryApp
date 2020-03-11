package com.example.goodb.bighugeproject.ui.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.activities.MainActivity;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.serverProxy.ServerProxy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import models.EventsModel;
import models.PersonsModel;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventAllResult;
import results.LoginResult;
import results.PersonAllResult;
import results.PersonResult;
import results.RegisterResult;

public class LoginFragment extends Fragment {

    static Model model = Model.getInstance();
    private RadioButton maleBtn, femaleBtn;
    private Button signInBtn, registerBtn;
    public EditText txtHost, txtPort, txtUsername, txtPassword, txtFirstname, txtLastname, txtEmail;
    private boolean boolHostFilled, boolPortFilled, boolUsernameFilled, boolPasswordFilled,
            boolFirstnameFilled, boolLastnameFilled, boolEmailFilled, boolGenderFilled;
    private char gender;
    private RegAsyncTask asyncTask;

    public EditText getTxtHost() {
        return txtHost;
    }

    public EditText getTxtPort() {
        return txtPort;
    }

    public EditText getTxtUsername() {
        return txtUsername;
    }

    public EditText getTxtPassword() {
        return txtPassword;
    }

    public EditText getTxtFirstname() {
        return txtFirstname;
    }

    public EditText getTxtLastname() {
        return txtLastname;
    }

    public EditText getTxtEmail() {
        return txtEmail;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);


        // This will be called if an up button was recently pressed
        // Revert back to the map fragment
        if (model.getAuthToken() != null) {
            //Open up the map fragment
            FragmentManager fm = getFragmentManager();
            Fragment mapFragment = new MapFragment();
            fm.beginTransaction().replace(R.id.fragment_container, mapFragment).commitAllowingStateLoss();
            return v;
        }





        // Listener for the Server Host editText field. If it is not empty, then assign
        // boolHostFilled to true to indicate that something has been written in that field
        txtHost = v.findViewById(R.id.txtHost);
        txtHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    boolHostFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolHostFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });


        // Listener for the Server Port editText field. If it is not empty, then assign
        // boolPortFilled to true to indicate that something has been written in that field
        txtPort = v.findViewById(R.id.txtPort);
        //txtPort.setText("8080");
        txtPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    boolPortFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolPortFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });


        // Listener for the Username editText field. If it is not empty, then assign
        // boolUsernameFilled to true to indicate that something has been written in that field
        txtUsername = v.findViewById(R.id.txtUsername);
        txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolUsernameFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolUsernameFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });


        // Listener for the Password editText field. If it is not empty, then assign
        // boolPasswordFilled to true to indicate that something has been written in that field
        txtPassword = v.findViewById(R.id.txtPassword);
        txtPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolPasswordFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolPasswordFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });





        // Listener for the txtFirstname editText field. If it is not empty, then assign
        // boolFirstNameFilled to true to indicate that something has been written in that field
        txtFirstname = v.findViewById(R.id.txtFirstname);
        txtFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolFirstnameFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolFirstnameFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });


        // Listener for the txtLastname editText field. If it is not empty, then assign
        // boolLastnameFilled to true to indicate that something has been written in that field
        txtLastname = v.findViewById(R.id.txtLastname);
        txtLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolLastnameFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolLastnameFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });


        // Listener for the txtEmail editText field. If it is not empty, then assign
        // boolEmailFilled to true to indicate that something has been written in that field
        txtEmail = v.findViewById(R.id.txtEmail);
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    boolEmailFilled = true;
                    checkIfFieldsAreFilled();
                }
                else {
                    boolEmailFilled = false;
                    checkIfFieldsAreFilled();
                }
            }
        });


        // Listener for the male radio button. Set the field as
        // filled, call the checkIfFieldsAreFilled method to enable
        // the sign in and register buttons, and assign the gender
        // variable to 'm'
        maleBtn = v.findViewById(R.id.radio_male);
        maleBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                boolGenderFilled = true;
                checkIfFieldsAreFilled();
                gender = 'm';
            }
        });


        // Listener for the female radio button. Set the field as
        // filled, call the checkIfFieldsAreFilled method to enable
        // the sign in and register buttons, and assign the gender
        // variable to 'f'
        femaleBtn = v.findViewById(R.id.radio_female);
        femaleBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                boolGenderFilled = true;
                checkIfFieldsAreFilled();
                gender = 'f';
            }
        });

        // Listener for the sign in button.  Is disabled, until required fields are filled
        signInBtn = v.findViewById(R.id.btnSignin);
        signInBtn.setEnabled(false);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                signInBtn.setEnabled(false);

                //Call async task to sign in
                LoginRequest loginRequest = new LoginRequest(txtUsername.getText().toString(),
                        txtPassword.getText().toString());

                model.setHost(txtHost.getText().toString());
                model.setPort(txtPort.getText().toString());
                model.setUserName(txtUsername.getText().toString());
                model.setLoginRequest(loginRequest);

                startLoginAsyncTask(txtHost.getText().toString(), txtPort.getText().toString(),
                        txtUsername.getText().toString(), loginRequest);


                signInBtn.setEnabled(true);


            }
        });


        // Listener for the register button.  Is disabled until all fields are filled
        registerBtn = v.findViewById(R.id.btnRegister);
        registerBtn.setEnabled(false);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                registerBtn.setEnabled(false);

                // Create a register request object and call the asyncTask
                // method "register" with it
                RegisterRequest regRequest = new RegisterRequest(txtUsername.getText().toString(),
                        txtPassword.getText().toString(), txtEmail.getText().toString(), txtFirstname.getText().toString(),
                        txtLastname.getText().toString(), Character.toString(gender));
                new RegAsyncTask(registerBtn, txtHost.getText().toString(), txtPort.getText().toString(),
                        txtFirstname.getText().toString(), txtLastname.getText().toString()).execute(regRequest);


            }
        });

        // This is for testing purposes
        // It helps to keep from having to input the data
        txtHost.setText("10.0.2.2", TextView.BufferType.EDITABLE);
        txtPort.setText("8080", TextView.BufferType.EDITABLE);
        txtUsername.setText("sheila", TextView.BufferType.EDITABLE);
        txtPassword.setText("parker", TextView.BufferType.EDITABLE);


        return v;
    }

    public void startLoginAsyncTask(String host, String port, String userName, LoginRequest loginRequest) {
        new LoginAsyncTask(host, port, userName).execute(loginRequest);
    }


    // Helper method to determine if the sign in and register buttons should be enabled
    // It checks all the fields to see if they are filled in order to know if the buttons
    // should be enabled
    void checkIfFieldsAreFilled() {

        if (boolHostFilled && boolPortFilled && boolUsernameFilled && boolPasswordFilled) {
            signInBtn.setEnabled(true);

            if (boolFirstnameFilled && boolLastnameFilled && boolEmailFilled && boolGenderFilled) {
                registerBtn.setEnabled(true);
            }
            else {
                registerBtn.setEnabled(false);
            }
        }

        else {
            signInBtn.setEnabled(false);
            registerBtn.setEnabled(false);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public class RegAsyncTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {
        private ServerProxy proxy = new ServerProxy();
        private String host, port, firstName, lastName;
        private Button regBtn;


        //Constructor to make
        public RegAsyncTask(Button regBtn, String host, String port, String firstName, String lastName) {
            this.host = host;
            this.port = port;
            this.firstName = firstName;
            this.lastName = lastName;
            this.regBtn = regBtn;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param registerRequests The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected RegisterResult doInBackground(RegisterRequest... registerRequests) {
            return proxy.register(host, port, registerRequests[0]);
        }

        protected void onPostExecute(RegisterResult result) {

            // Check if there is an error message.
            // If there is no message, create a GetDataAsyncTask, which will pull all
            // the info pertaining to the user from the database and display a toast
            if (result.getMessage() == null) {
                String personID = result.getPersonID();
                new GetDataAsyncTask(host, port, result.getAuthToken()).execute(personID);
            }
            else {
                Toast.makeText(model.getMainActivityContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                regBtn.setEnabled(true);
            }
        }
    }


    public class LoginAsyncTask extends AsyncTask<LoginRequest, Void, LoginResult> {
        private ServerProxy proxy = new ServerProxy();
        private String host, port, userName;

        //Constructor to make
        public LoginAsyncTask(String host, String port, String userName) {
            this.host = host;
            this.port = port;
            this.userName = userName;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param loginRequests The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected LoginResult doInBackground(LoginRequest... loginRequests) {

            return proxy.login(host, port, loginRequests[0]);
        }

        protected void onPostExecute(LoginResult result) {
            // Check if there is an error message.
            // If there is no message, create a GetDataAsyncTask, which will pull all
            // the info pertaining to the user from the database and display a toast
            if (result.getMessage() == null) {
                String personID = result.getPersonID();
                new GetDataAsyncTask(host, port, result.getAuthToken()).execute(personID);
            }
            else {
                Toast.makeText(model.getMainActivityContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                if (model.getAuthToken() != null && model.getAuthToken().equals("")) {
                    //Open up the login fragment
                    model.setAuthToken(null);
                    Activity a = (MainActivity) model.getMainActivityContext();
                    FragmentManager fm = ((MainActivity) a).getSupportFragmentManager();
                    Fragment mapFragment = new LoginFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, mapFragment).commitAllowingStateLoss();
                    //return v;
                }
            }
        }
    }


    private class GetDataAsyncTask extends AsyncTask<String, Void, ArrayList<Object>> {
        private ServerProxy proxy = new ServerProxy();
        private String host, port, authToken;

        //Constructor to make
        public GetDataAsyncTask(String host, String port, String authToken) {
            this.host = host;
            this.port = port;
            this.authToken = authToken;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param personIDs The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<Object> doInBackground(String... personIDs) {

            // Contact the server proxy to get the user person, people, and events
            PersonResult person =  proxy.getPerson(host, port, authToken, personIDs[0]);
            PersonAllResult allPeople = proxy.getAllPeople(host, port, authToken);
            EventAllResult allEvents = proxy.getAllEvents(host, port, authToken);

            // Add that data to an arraylist to add to the Model class later
            ArrayList<Object> listOfResults = new ArrayList<Object>();
            listOfResults.add(person);
            listOfResults.add(allPeople);
            listOfResults.add(allEvents);
            return listOfResults;
        }

        protected void onPostExecute(ArrayList<Object> listOfResults) {
            PersonResult person = (PersonResult) listOfResults.get(0);
            PersonAllResult allPeople = (PersonAllResult) listOfResults.get(1);
            EventAllResult allEvents = (EventAllResult) listOfResults.get(2);

            // Set up the toast
            // If there was no error message, display the name of the person
            // else return the error message
            if (person.getMessage() == null) {
                //Show a toast with the name of the person that logged in
                Toast.makeText(model.getMainActivityContext(), person.getFirstName() + " " + person.getLastName(),
                        Toast.LENGTH_SHORT).show();


                // Instantiate the personsMap that is in the model class

                for (PersonsModel p : allPeople.getData()) {
                    if (p == null)
                        continue;

                    model.getPersonsMap().put(p.getPersonID(), p);
                }

                // Instantiate the event maps and eventsToDisplay set
                // Each event map will contain a key of a personID to its corresponding event type
                for (EventsModel e : allEvents.getData()) {
                    if (e == null)
                        continue;

                    model.getAllEvents().add(e);
                    model.getEventsToDisplay().add(e);
                }

                // Instantiate other member variables
                model.setAuthToken(authToken);
                model.setUserModel(person.getPersonID());

                for (PersonsModel female : allPeople.getData()) {
                    if (female == null)
                        continue;

                    if (female.getGender().toLowerCase().equals("f")) {
                        model.getFemalePersonsSet().add(female);
                        model.getFemalePersonsMap().put(female.getPersonID(), female);
                    }
                }

                for (PersonsModel male : allPeople.getData()) {
                    if (male == null)
                        continue;

                    if (male.getGender().toLowerCase().equals("m")) {
                        model.getMalePersonsSet().add(male);
                        model.getMalePersonsMap().put(male.getPersonID(), male);
                    }
                }

                // Add events and boolean values to the different events to
                // be able to filter
                model.getFilterData().initializeMapOfEventsToFilter(model.getEventsToDisplay());

                // Evaluate who is on the mother's side of the family and
                // add them to the matPersonsSet
                if (person.getMother() != null) {
                    PersonsModel mother = model.getPersonsMap().get(person.getMother());
                    addPersonsToSideOfTheFamily(mother, 'm');
                }

                // Evaluate who is on the father's side of the family and
                // add them to the patPersonsSet
                if (person.getFather() != null) {
                    PersonsModel father = model.getPersonsMap().get(person.getFather());
                    addPersonsToSideOfTheFamily(father, 'p');
                }

                //Open up the map fragment

                if (model.getUserName() != null && !model.getUserName().equals("resync")) {
                    FragmentManager fm = getFragmentManager();
                    Fragment mapFragment = new MapFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
                }
                else if (model.getUserName() != null) {
                    model.setUserName("");
                    Activity a = (MainActivity) model.getMainActivityContext();
                    FragmentManager fm = ((MainActivity) a).getSupportFragmentManager();
                    Fragment mapFragment = new MapFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
                }

            }
            else {
                Toast.makeText(model.getMainActivityContext(), person.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public LoginResult waitForAsyncToFinish(String host, String port, String userName, LoginRequest loginRequest) throws ExecutionException, InterruptedException {
        LoginResult r = new LoginAsyncTask(host, port, userName).execute(loginRequest).get();
        return r;
    }

    public PersonsModel calculateMother(PersonsModel p) {
        return model.getPersonsMap().get(p.getMother());
    }

    public PersonsModel calculateFather(PersonsModel p) {
        return model.getPersonsMap().get(p.getFather());
    }

    public PersonsModel calculateSpouse(PersonsModel p) {
        return model.getPersonsMap().get(p.getSpouse());
    }

    // Add all persons on maternal or paternal side of family to their respective set, depending
    // on the char that is passed in
    public void addPersonsToSideOfTheFamily(PersonsModel root, char matOrPatSide){
        if (root.getFather() != null){
            addPersonsToSideOfTheFamily(model.getPersonsMap().get(root.getFather()), matOrPatSide);
        }

        if (root.getMother() != null){
            addPersonsToSideOfTheFamily(model.getPersonsMap().get(root.getMother()), matOrPatSide);
        }

        if (matOrPatSide == 'm') {
            model.getMatAncestorSet().add(root);
            model.getMatAncestorMap().put(root.getPersonID(), root);
        }
        else {
            model.getPatAncestorSet().add(root);
            model.getPatAncestorMap().put(root.getPersonID(), root);
        }
    }


}




















