package com.example.goodb.bighugeproject.ui.main;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.activities.FilterActivity;
import com.example.goodb.bighugeproject.activities.MainActivity;
import com.example.goodb.bighugeproject.activities.MapsActivity;
import com.example.goodb.bighugeproject.activities.PersonActivity;
import com.example.goodb.bighugeproject.activities.SearchActivity;
import com.example.goodb.bighugeproject.activities.SettingsActivity;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.EventsModel;
import models.PersonsModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    // Instantiate ImageView to use Android Iconify
    SupportMapFragment mapFragment;
    static Model model = Model.getInstance();
    Map<Marker, EventsModel> mapOfMarkersToEvents = new HashMap<Marker, EventsModel>();
    public ImageView genderImageView;
    public TextView txt_person_name, txt_event_location;
    private LinearLayout info;
    private Set<Polyline> setOfLines = new HashSet<Polyline>();

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(model.isToolbarVisible());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Call this method to update what events will be shown on the map
        updateEventsToDisplay();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // Initialize the bottom area where information is showed
        genderImageView = v.findViewById(R.id.genderImage);
        txt_person_name = v.findViewById(R.id.txt_person_name);
        txt_event_location = v.findViewById(R.id.txt_event_location);
        info = v.findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                // This will start the Person Activity
                PersonsModel p = model.getPersonModel();
                Activity a = (MainActivity) model.getMainActivityContext();
                Intent intent = new Intent(a, PersonActivity.class);
                a.startActivity(intent);
            }
        });

        return v;
    }

    // This inflates the menu bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    // This function gets called when one of the icons in the menu bar is clicked on
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            // This will start the search activity
            case R.id.search_bar:
                intent = new Intent(this.getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;

            // This will start the filter activity
            case R.id.filter_button:
                intent = new Intent(this.getActivity(), FilterActivity.class);
                startActivity(intent);
                return true;

            // This starts the settings activity
            case R.id.settings_button:
                intent = new Intent(this.getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;

        }
        return true;
    }

    // This will run when the map is finished building
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMapType(model.getSettingsData().getMapType() + 1);


        // Set the icon                                                   // Select the icon here
        final Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).
                                // Choose the color here
                        colorRes(R.color.android).sizeDp(40);
        genderImageView.setImageDrawable(genderIcon);
        setOfLines.clear();


        // Add the markers
        addMarkers(googleMap, model.getEventsToDisplay());
        googleMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //model.setMarker(marker);

                // Remove all polylines from the map and
                // display the correct information about the event
                for (Polyline p : setOfLines)
                    p.remove();

                EventsModel event = mapOfMarkersToEvents.get(marker);
                PersonsModel person = model.getPersonsMap().get(event.getPersonID());
                model.setPersonModel(person);
                txt_person_name.setText(person.getFirstName() + " " + person.getLastName());
                txt_event_location.setText(event.getEventType() + " (" + event.getYear() + ")"
                        + "\n" + event.getCity() + "\n" + event.getCountry());

                // Set the correct icon of male or female
                if (person.getGender().toLowerCase().equals("f")) {
                    final Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                                    colorRes(R.color.female_icon).sizeDp(40);
                    genderImageView.setImageDrawable(genderIcon);
                }
                else {
                    final Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                            colorRes(R.color.male_icon).sizeDp(40);
                    genderImageView.setImageDrawable(genderIcon);
                }

                // This fills the lifeEventsMap in the model class with the life events of the person
                // that was clicked on. NOTE: the map will be ordered by year
                fillInLifeEvents(person);


                // Display lines linking the selected event to the birth event of the person’s
                //spouse. If their birth event is currently invisible due to the current
                //event filter settings, the earliest available event for the spouse is used instead
                // No line is drawn if there is no spouse
                addLineToSpouseBirth(marker, person, googleMap);


                // A line is drawn from the selected event to the birth events of both the person's
                // parents.  A line a drawn from those parents to their parents' births events as
                // well and recursively continue.  Lines should get thinner for each generation
                createLineToDirectParents(person, marker, googleMap);
                fillInLifeEvents(person);


                // A green line is drawn along all events tied to this user in chronological order,
                // starting with a birth event and ending with a death event if those exist.
                // Other events should be order chronologically
                addLinesToLifeEventsPartOne(googleMap);

                return false;
            }
        }); // End of listener

        if (!model.isToolbarVisible()) {
            // This will select the marker and place it in the center of the screen
            for (Map.Entry<Marker, EventsModel> entry : mapOfMarkersToEvents.entrySet()) {
                if (entry.getValue().equals(model.getEventsModel())) {
                    LatLng position = entry.getKey().getPosition();

                    // Center the map on the event
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 3));
                    Marker marker = entry.getKey();
                    marker.showInfoWindow();

                    // Update the info according to the event that is selected
                    EventsModel event = mapOfMarkersToEvents.get(marker);
                    PersonsModel person = model.getPersonsMap().get(event.getPersonID());
                    txt_person_name.setText(person.getFirstName() + " " + person.getLastName());
                    txt_event_location.setText(event.getEventType() + " (" + event.getYear() + ")"
                            + "\n" + event.getCity() + "\n" + event.getCountry());

                    // Set the correct icon of male or female
                    if (person.getGender().toLowerCase().equals("f")) {
                        final Drawable icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                                colorRes(R.color.female_icon).sizeDp(40);
                        genderImageView.setImageDrawable(icon);
                    } else {
                        final Drawable icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                                colorRes(R.color.male_icon).sizeDp(40);
                        genderImageView.setImageDrawable(icon);
                    }

                    // This fills the lifeEventsMap in the model class with the life events of the person
                    // that was clicked on. NOTE: the map will be ordered by year
                    fillInLifeEvents(person);


                    // Display lines linking the selected event to the birth event of the person’s
                    //spouse. If their birth event is currently invisible due to the current
                    //event filter settings, the earliest available event for the spouse is used instead
                    // No line is drawn if there is no spouse
                    addLineToSpouseBirth(marker, person, googleMap);


                    // A line is drawn from the selected event to the birth events of both the person's
                    // parents.  A line a drawn from those parents to their parents' births events as
                    // well and recursively continue.  Lines should get thinner for each generation
                    createLineToDirectParents(person, marker, googleMap);
                    fillInLifeEvents(person);


                    // A green line is drawn along all events tied to this user in chronological order,
                    // starting with a birth event and ending with a death event if those exist.
                    // Other events should be order chronologically
                    addLinesToLifeEventsPartOne(googleMap);

                    break;
                }

            }
        }

    }

    public void createLineToDirectParents(PersonsModel person, Marker marker, GoogleMap googleMap) {
        if (!model.getSettingsData().isShowFamilyTreeLines())
            return;

        LatLng startPoint = marker.getPosition();
        int color = decipherLineColor(model.getSettingsData().getFamilyTreeLineColor());

        if (person.getFather() != null) {

            PersonsModel father = model.getPersonsMap().get(person.getFather());
            fillInLifeEvents(father);

            //If there are no events to display, then return
            if (!model.getLifeEventsMap().isEmpty()) {

                EventsModel firstEvent = model.getLifeEventsMap().entrySet().iterator().next().getValue();
                LatLng endPoint = new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude());
                PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint)
                        .color(color).width(9);
                setOfLines.add(googleMap.addPolyline(options));
                recursivelyConnectAncesters(father, 9, googleMap);
            }
        }

        if (person.getMother() != null) {
            PersonsModel mother = model.getPersonsMap().get(person.getMother());
            fillInLifeEvents(mother);

            //If there are no events to display, then return
            if (!model.getLifeEventsMap().isEmpty()) {

                EventsModel firstEvent = model.getLifeEventsMap().entrySet().iterator().next().getValue();
                LatLng endPoint = new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude());
                PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint)
                        .color(color).width(9);
                setOfLines.add(googleMap.addPolyline(options));

                recursivelyConnectAncesters(mother, 9, googleMap);
            }
        }

    }

    public void recursivelyConnectAncesters(PersonsModel root, int lineSize, GoogleMap googleMap){
        if (lineSize > 2)
            lineSize-=2;
        if (root.getFather() != null){
            drawAncestorLine(root, model.getPersonsMap().get(root.getFather()), lineSize, googleMap);
            recursivelyConnectAncesters(model.getPersonsMap().get(root.getFather()), lineSize, googleMap);
        }

        if (root.getMother() != null){
            drawAncestorLine(root, model.getPersonsMap().get(root.getMother()), lineSize, googleMap);
            recursivelyConnectAncesters(model.getPersonsMap().get(root.getMother()), lineSize, googleMap);
        }
    }

    private void drawAncestorLine(PersonsModel root, PersonsModel parent, int lineSize, GoogleMap googleMap) {
        fillInLifeEvents(root);

        // If there are no events, then return
        if (model.getLifeEventsMap().isEmpty()) {
            fillInLifeEvents(parent);
            return;
        }

        EventsModel firstEvent = model.getLifeEventsMap().entrySet().iterator().next().getValue();
        LatLng startPoint = new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude());

        fillInLifeEvents(parent);

        // If there are no events, then return
        if (model.getLifeEventsMap().isEmpty())
            return;

        firstEvent = model.getLifeEventsMap().entrySet().iterator().next().getValue();
        LatLng endPoint = new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude());
        int color = decipherLineColor(model.getSettingsData().getFamilyTreeLineColor());
        PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint)
                .color(color).width(lineSize);
        setOfLines.add(googleMap.addPolyline(options));
    }

    // This fills the lifeEventsMap in the model class with the life events of the person
    // that was clicked on. NOTE: the map will be ordered by year
    public void fillInLifeEvents(PersonsModel person) {
        model.getLifeEventsMap().clear();
        for (EventsModel e : model.getEventsToDisplay()) {

            // Add in the events of the appropriate person
            // Events of the same year will have extra " "
            // at the end so they can still be added to the map
            if (e.getPersonID().equals(person.getPersonID())) {

                PersonsModel temp = model.getPersonsMap().get(e.getPersonID());
                String key = Integer.toString(e.getYear());
                while (model.getLifeEventsMap().containsKey(key))
                    key += " ";
                model.getLifeEventsMap().put(key, e);
            }
        }
    }

    public void addLinesToLifeEventsPartOne(GoogleMap googleMap) {
        if (!model.getSettingsData().isShowLifeStoryLines())
            return;

        int count = 0;
        EventsModel beginEvent = null;
        EventsModel endEvent = null;

        // If there are no events, then return
        if (model.getLifeEventsMap().isEmpty())
            return;

        for (Map.Entry<String, EventsModel> entry : model.getLifeEventsMap().entrySet()) {
            if (count == 0) {
                beginEvent = entry.getValue();
            } else if (count == 1) {
                endEvent = entry.getValue();
                addLinesToLifeEventsPartTwo(beginEvent, endEvent, googleMap);
            } else {
                beginEvent = endEvent;
                endEvent = entry.getValue();
                addLinesToLifeEventsPartTwo(beginEvent, endEvent, googleMap);
            }

            count++;
        }
    }

    public void addLinesToLifeEventsPartTwo(EventsModel firstEvent, EventsModel secondEvent, GoogleMap googleMap) {
        LatLng startPoint = new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude());
        LatLng endPoint = new LatLng(secondEvent.getLatitude(), secondEvent.getLongitude());
        int color = decipherLineColor(model.getSettingsData().getLifeStoryLineColor());
        PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint)
                .color(color).width(10);
        setOfLines.add(googleMap.addPolyline(options));

    }

    public void addLineToSpouseBirth(Marker marker, PersonsModel person, GoogleMap googleMap) {
        if (!model.getSettingsData().isShowSpouseLines())
            return;

        LatLng startPoint = marker.getPosition();
        PersonsModel spouse = null;
        if (person.getSpouse() != null && !person.getSpouse().equals("n/a")) {
            spouse = model.getPersonsMap().get(person.getSpouse());
            fillInLifeEvents(spouse);

            // If there is no spouse, or there are no events, then return
            if (model.getLifeEventsMap().isEmpty()) {
                fillInLifeEvents(person);
                return;
            }

            EventsModel firstEventInLife = model.getLifeEventsMap().entrySet().iterator().next().getValue();
            LatLng endPoint = new LatLng(firstEventInLife.getLatitude(), firstEventInLife.getLongitude());
            int color = decipherLineColor(model.getSettingsData().getSpouseLineColor());
            PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint)
                    .color(color).width(10);
            setOfLines.add(googleMap.addPolyline(options));
            fillInLifeEvents(person);
        }
    }

    public void addMarkers(GoogleMap googleMap, Set<EventsModel> events) {
        // Add all markers to the google map, and to the map of events
        googleMap.clear();
        mapOfMarkersToEvents.clear();
        float color = 4;

        for (EventsModel event : events) {
            String eventType = event.getEventType();
            color = BitmapDescriptorFactory.HUE_GREEN;

            // Select the color of the marker depending on the event that it is
            switch (eventType.toLowerCase()) {
                case "birth" :
                    color = BitmapDescriptorFactory.HUE_AZURE;
                    break;
                case "baptism" :
                    color = BitmapDescriptorFactory.HUE_BLUE;
                    break;
                case "marriage" :
                    color = BitmapDescriptorFactory.HUE_ROSE;
                    break;
                case "death" :
                    color = BitmapDescriptorFactory.HUE_VIOLET;
                    break;
            }

            mapOfMarkersToEvents.put(googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(event.getLatitude(), event.getLongitude()))
                    .title(event.getEventType()).icon(BitmapDescriptorFactory.defaultMarker(color))), event);
        }
    }

    public void updateEventsToDisplay() {
        model.getEventsToDisplay().clear();

        // If both the maternal and paternal ancestor switches are
        // selected, then add the events to the eventsToDisplay set
        if (model.getFilterData().getShowFathersSide() == true &&
                model.getFilterData().getShowMothersSide() == true) {

            // Go through the dynamically created list of current events that a person might have
            for (String key : model.getFilterData().getEventsToFilter().keySet()) {

                // If the event is marked as true in the filterData, then put it in the eventsToDisplay set
                if (model.getFilterData().getEventsToFilter().get(key))
                    for (EventsModel e : model.getAllEvents())
                        if (e.getEventType().toLowerCase().equals(key))
                            addEventToEventsToDisplay(e);
            }
        }

        // If only paternal ancestors should be displayed, then only
        // add paternal events to the eventsToDisplay set
        else if (model.getFilterData().getShowFathersSide() == true) {
            // Go through the dynamically created list of current events that a person might have
            for (String key : model.getFilterData().getEventsToFilter().keySet()) {

                // If the event is marked as true in the filterData, then put it in the eventsToDisplay set
                if (model.getFilterData().getEventsToFilter().get(key))
                    for (EventsModel e : model.getAllEvents())
                        if (e.getEventType().toLowerCase().equals(key) && model.getPatAncestorMap().containsKey(e.getPersonID()))
                            addEventToEventsToDisplay(e);
            }
        }

        // If only maternal events are selected, then only allow
        // maternal events be added to the eventsToDisplay set
        else if (model.getFilterData().getShowMothersSide() == true) {
            // Go through the dynamically created list of current events that a person might have
            for (String key : model.getFilterData().getEventsToFilter().keySet()) {

                // If the event is marked as true in the filterData, then put it in the eventsToDisplay set
                if (model.getFilterData().getEventsToFilter().get(key))
                    for (EventsModel e : model.getAllEvents())
                        if (e.getEventType().toLowerCase().equals(key) && model.getMatAncestorMap().containsKey(e.getPersonID()))
                            addEventToEventsToDisplay(e);
            }
        }

        // If neither of the sides are selected, then only show
        // the events of the current user (the 1st generation person)
        else {
            // Go through the dynamically created list of current events that a person might have
            for (String key : model.getFilterData().getEventsToFilter().keySet()) {

                // If the event is marked as true in the filterData, then put it in the eventsToDisplay set
                // Check if it is a direct event of the user's spouse
                if (model.getFilterData().getEventsToFilter().get(key))
                    for (EventsModel e : model.getAllEvents()) {
                    // This will add the person's events, if they have any
                        if (e.getEventType().toLowerCase().equals(key) &&
                                e.getPersonID().equals(model.getUserModel().getPersonID()))
                            addEventToEventsToDisplay(e);

                        // This will add the spouse events, if the person has a spouse
                        if (e.getEventType().toLowerCase().equals(key) &&
                                model.getPersonsMap().get(e.getPersonID()).getSpouse() != null &&
                                e.getPersonID().equals(model.getPersonsMap().get(model.getUserModel().getPersonID()).getSpouse()) &&
                                !model.getPersonsMap().get(e.getPersonID()).getSpouse().equals("n/a"))
                            addEventToEventsToDisplay(e);

                    }

            }
        }

    }

    private void addEventToEventsToDisplay(EventsModel e) {
        if (model.getFilterData().getShowMaleEvents() &&
                model.getFilterData().getShowFemaleEvents()) {
            model.getEventsToDisplay().add(e);
        }
        else if (model.getFilterData().getShowMaleEvents()) {
            if (model.getMalePersonsMap().containsKey(e.getPersonID()))
                model.getEventsToDisplay().add(e);
        }
        else if (model.getFilterData().getShowFemaleEvents()) {
            if (model.getFemalePersonsMap().containsKey(e.getPersonID()))
                model.getEventsToDisplay().add(e);
        }
    }

    private int decipherLineColor(int lineColor) {
        switch (lineColor) {
            case 0 :
                return Color.RED;
            case 1 :
                return Color.GREEN;
            case 2 :
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }
}
