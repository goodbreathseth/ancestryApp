package com.example.goodb.bighugeproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.recyclerView.FilterAdapter;
import com.example.goodb.bighugeproject.recyclerView.SearchAdapter;

import java.util.ArrayList;

import models.EventsModel;
import models.PersonsModel;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Object> listOfSearchResults = new ArrayList<>();
    static Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        model.setSearchActivityContext(SearchActivity.this);

        mRecyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //search(query.toLowerCase());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText.toLowerCase());
                return false;
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new SearchAdapter(listOfSearchResults, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void search(String query) {
        // Go through each person that exists and check if their name contains
        // the query.  If so, make sure they (the persons) are added first to
        // the list of search results
        listOfSearchResults.clear();
        model.setPersonCount(0);
        if (!query.equals("")) {
            for (PersonsModel p : model.getPersonsMap().values()) {
                if (p.getFirstName().toLowerCase().contains(query) ||
                        p.getLastName().toLowerCase().contains(query)) {
                    listOfSearchResults.add(p);
                    model.incrementPersonCount();
                }

            }

        // Go through each event that is not being filtered and check if the country, city,
        // event type, or year contain the query.  If so, add them to the list of things
        // to display
        for (EventsModel e : model.getEventsToDisplay()) {
            if (e.getCountry().toLowerCase().contains(query) ||
                    e.getCity().toLowerCase().contains(query) ||
                    e.getEventType().toLowerCase().contains(query) ||
                    Integer.toString(e.getYear()).contains(query))
                listOfSearchResults.add(e);
        }

        }

        // Call this to update the recyclerView list
        mAdapter.notifyDataSetChanged();


    }
}
