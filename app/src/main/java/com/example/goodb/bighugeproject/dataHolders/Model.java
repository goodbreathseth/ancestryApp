package com.example.goodb.bighugeproject.dataHolders;

import android.content.Context;

import com.google.android.gms.maps.model.Marker;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import models.EventsModel;
import models.PersonsModel;
import models.UserModel;
import requests.LoginRequest;

public class Model {

    // There are no comments considering all the variables have names
    // that are quite explanatory
    public static Model single_instance = null;
    private Set<EventsModel> allEvents = new HashSet<EventsModel>(); // This will get instantiated one time in the loginFragment
    private Map<String, PersonsModel> personsMap = new TreeMap<String, PersonsModel>(); // Used to quickly get access to a PersonModel
    private Set<EventsModel> eventsToDisplay = new HashSet<EventsModel>(); // This gets updated each time a filter is selected
    private Set<PersonsModel> matAncestorSet = new HashSet<PersonsModel>(); // This will be use when a filter to show the maternal line is selected
    private Map<String, PersonsModel> matAncestorMap = new TreeMap<String, PersonsModel>();
    private Set<PersonsModel> patAncestorSet = new HashSet<PersonsModel>(); // This will be used when a filter to show the paternal line is selected
    private Map<String, PersonsModel> patAncestorMap = new TreeMap<String, PersonsModel>();
    private Set<PersonsModel> malePersonsSet = new HashSet<PersonsModel>(); // This will be used when the filter to show male events is selected
    private Map<String, PersonsModel> malePersonsMap = new TreeMap<String, PersonsModel>();
    private Set<PersonsModel> femalePersonsSet = new HashSet<PersonsModel>(); // This will be used when the filter to show female events is selected
    private Map<String, PersonsModel> femalePersonsMap = new TreeMap<String, PersonsModel>();
    private Map<String, EventsModel> lifeEventsMap = new TreeMap<String, EventsModel>(); // For the person fragment
    private Map<String, PersonsModel> familyMembersMap = new TreeMap<String, PersonsModel>(); // Also for the person fragment to show their relatives
    private UserModel userModel;
    private String authToken;
    private FilterData filterData = new FilterData();
    private SettingsData settingsData = new SettingsData();
    private String host;
    private String port;
    private String userName;
    private LoginRequest loginRequest;
    private Context mainActivityContext;
    private Context searchActivityContext;
    private Context currentContext;
    private int personCount = 0;
    private Marker marker;
    private PersonsModel p;
    private EventsModel e;
    private boolean toolbarVisible = true;

    public Model() {

    }

    public static Model getInstance() {
        if (single_instance == null) {
            single_instance = new Model();
        }
        return single_instance;
    }

    public void clearInstance() {
        setAuthToken(null);
        setUserModel(null);
        allEvents = new HashSet<EventsModel>();
        personsMap = new TreeMap<String, PersonsModel>();
        eventsToDisplay = new HashSet<EventsModel>();
        matAncestorSet = new HashSet<PersonsModel>();
        matAncestorMap = new TreeMap<String, PersonsModel>();
        patAncestorSet = new HashSet<PersonsModel>();
        patAncestorMap = new TreeMap<String, PersonsModel>();
        malePersonsSet = new HashSet<PersonsModel>();
        malePersonsMap = new TreeMap<String, PersonsModel>();
        femalePersonsSet = new HashSet<PersonsModel>();
        femalePersonsMap = new TreeMap<String, PersonsModel>();
        lifeEventsMap = new TreeMap<String, EventsModel>();
        familyMembersMap = new TreeMap<String, PersonsModel>();
        filterData = new FilterData();
        settingsData = new SettingsData();
        personCount = 0;
        toolbarVisible = true;
    }

    public Context getSearchActivityContext() {
        return searchActivityContext;
    }

    public void setSearchActivityContext(Context searchActivityContext) {
        this.searchActivityContext = searchActivityContext;
    }

    public boolean isToolbarVisible() {
        return toolbarVisible;
    }

    public void setToolbarVisible(boolean toolbarVisible) {
        this.toolbarVisible = toolbarVisible;
    }

    public EventsModel getEventsModel() {
        return e;
    }

    public void setEventsModel(EventsModel e) {
        this.e = e;
    }

    public PersonsModel getPersonModel() {
        return p;
    }

    public void setPersonModel(PersonsModel p) {
        this.p = p;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void incrementPersonCount() {
        personCount++;
    }

    public void setPersonCount(int count) {
        personCount = count;
    }

    public int getPersonCount() {
        return personCount;
    }

    public Context getMainActivityContext() {
        return mainActivityContext;
    }

    public void setMainActivityContext(Context mcontext) {
        this.mainActivityContext = mcontext;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public Map<String, PersonsModel> getPersonsMap() {
        return personsMap;
    }

    public Map<String, PersonsModel> getMalePersonsMap() {
        return malePersonsMap;
    }

    public Map<String, PersonsModel> getFemalePersonsMap() {
        return femalePersonsMap;
    }

    public void setPersonsMap(Map<String, PersonsModel> personsMap) {
        this.personsMap = personsMap;
    }

    public void setAllEvents(Set<EventsModel> allEvents) {
        this.allEvents = allEvents;
    }

    public void setMatAncestorMap(Map<String, PersonsModel> matAncestorMap) {
        this.matAncestorMap = matAncestorMap;
    }

    public void setPatAncestorMap(Map<String, PersonsModel> patAncestorMap) {
        this.patAncestorMap = patAncestorMap;
    }

    public void setMalePersonsMap(Map<String, PersonsModel> malePersonsMap) {
        this.malePersonsMap = malePersonsMap;
    }

    public void setFemalePersonsMap(Map<String, PersonsModel> femalePersonsMap) {
        this.femalePersonsMap = femalePersonsMap;
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    public Set<EventsModel> getAllEvents() {
        return allEvents;
    }

    public Set<PersonsModel> getMalePersonsSet() {
        return malePersonsSet;
    }

    public void setMalePersonsSet(Set<PersonsModel> malePersonsSet) {
        this.malePersonsSet = malePersonsSet;
    }

    public Set<EventsModel> getEventsToDisplay() {
        return eventsToDisplay;
    }

    public void setEventsToDisplay(Set<EventsModel> eventsToDisplay) {
        this.eventsToDisplay = eventsToDisplay;
    }

    public Map<String, PersonsModel> getPatAncestorMap() {
        return patAncestorMap;
    }

    public Set<PersonsModel> getFemalePersonsSet() {
        return femalePersonsSet;
    }

    public void setFemalePersonsSet(Set<PersonsModel> femalePersonsSet) {
        this.femalePersonsSet = femalePersonsSet;
    }

    public Set<PersonsModel> getMatAncestorSet() {
        return matAncestorSet;
    }

    public void setMatAncestorSet(Set<PersonsModel> matAncestorSet) {
        this.matAncestorSet = matAncestorSet;
    }

    public Set<PersonsModel> getPatAncestorSet() {
        return patAncestorSet;
    }

    public void setPatAncestorSet(Set<PersonsModel> patAncestorSet) {
        this.patAncestorSet = patAncestorSet;
    }

    public Map<String, EventsModel> getLifeEventsMap() {
        return lifeEventsMap;
    }

    public void setLifeEventsMap(Map<String, EventsModel> lifeEventsMap) {
        this.lifeEventsMap = lifeEventsMap;
    }

    public Map<String, PersonsModel> getFamilyMembersMap() {
        return familyMembersMap;
    }

    public void setFamilyMembersMap(Map<String, PersonsModel> familyMembersMap) {
        this.familyMembersMap = familyMembersMap;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(String personID) {
        userModel = new UserModel();
        userModel.setPersonID(personID);
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public FilterData getFilterData() {
        return filterData;
    }

    public void setFilterData(FilterData filterData) {
        this.filterData = filterData;
    }

    public Map<String, PersonsModel> getMatAncestorMap() {
        return matAncestorMap;
    }

    public SettingsData getSettingsData() {
        return settingsData;
    }

    public void setSettingsData(SettingsData settingsData) {
        this.settingsData = settingsData;
    }
}
