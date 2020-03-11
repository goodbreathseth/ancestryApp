package com.example.goodb.bighugeproject.dataHolders;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import models.EventsModel;

public class FilterData {

    // Member data
    private boolean showFathersSide,
    showMothersSide, showMaleEvents, showFemaleEvents;
    private Map<String, Boolean> eventsToFilter = new TreeMap<String, Boolean>();

    public FilterData() {

        showFathersSide = true;
        showMothersSide = true;
        showMaleEvents = true;
        showFemaleEvents = true;
    }

    // Put these in first so they will appear at the top of the filter list
    public void initializeMapOfEventsToFilter(Set<EventsModel> eventsToDisplay) {
        eventsToFilter.put(" paternal", showFathersSide);
        eventsToFilter.put(" maternal", showMothersSide);
        eventsToFilter.put(" male", showMaleEvents);
        eventsToFilter.put(" female", showFemaleEvents);

        for (EventsModel e : eventsToDisplay) {
            eventsToFilter.put(e.getEventType().toLowerCase(), true);
        }
    }

    public Map<String, Boolean> getEventsToFilter() {
        return eventsToFilter;
    }


    public boolean getShowFathersSide() {
        return showFathersSide;
    }

    public void setShowFathersSide(boolean showFathersSide) {
        this.showFathersSide = showFathersSide;
    }

    public boolean getShowMothersSide() {
        return showMothersSide;
    }

    public void setShowMothersSide(boolean showMothersSide) {
        this.showMothersSide = showMothersSide;
    }

    public boolean getShowMaleEvents() {
        return showMaleEvents;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        this.showMaleEvents = showMaleEvents;
    }

    public boolean getShowFemaleEvents() {
        return showFemaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        this.showFemaleEvents = showFemaleEvents;
    }
}
