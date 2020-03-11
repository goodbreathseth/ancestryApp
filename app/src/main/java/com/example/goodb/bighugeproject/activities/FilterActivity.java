package com.example.goodb.bighugeproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.recyclerView.FilterAdapter;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FilterAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static Model model = Model.getInstance();


    // Member variables
    private ArrayList<String> eventTitles = new ArrayList<String>();
    private ArrayList<String> eventInfos = new ArrayList<String>();
    private ArrayList<Boolean> filterSwitches = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initEventFilters();


        mRecyclerView = findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new FilterAdapter(eventTitles, eventInfos, filterSwitches, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    // Call all events and boolean values that are saved in the eventsMap from FilterData class
    private void initEventFilters() {

        eventTitles.addAll(model.getFilterData().getEventsToFilter().keySet());

        for (int i = 0; i < eventTitles.size(); i++)
            eventInfos.add("Enable event to be visible or not");

        filterSwitches.addAll(model.getFilterData().getEventsToFilter().values());

    }
}
