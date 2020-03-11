package com.example.goodb.bighugeproject.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.dataHolders.Model;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    static Model model = Model.getInstance();
    private ArrayList<String> eventTitles = new ArrayList<String>();
    private ArrayList<String> eventInfos = new ArrayList<String>();
    private ArrayList<Boolean> filterSwitches = new ArrayList<Boolean>();
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView eventTitle, eventInfo;
        Switch filterSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title);
            eventInfo = itemView.findViewById(R.id.event_info);
            filterSwitch = itemView.findViewById(R.id.filter_switch);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FilterAdapter(ArrayList<String> eventTitles, ArrayList<String> eventInfos, ArrayList<Boolean> filterSwitches, Context context) {
        this.eventTitles = eventTitles;
        this.eventInfos = eventInfos;
        this.filterSwitches = filterSwitches;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_filter, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String event = eventTitles.get(position);
        if (event.charAt(0) == ' ') {
            holder.eventTitle.setText(event.substring(0,1) + event.substring(1,2).toUpperCase() +
                    event.substring(2, event.length()));
        }
        else {
            holder.eventTitle.setText(event.substring(0, 1).toUpperCase() +
                    event.substring(1, event.length()));
        }
        String eventInfo = "";
        if (position < 4) {
            switch (position) {
                case 0:
                    eventInfo = "Filter events based on gender";
                    break;
                case 1:
                    eventInfo = "Filter events based on gender";
                    break;
                case 2:
                    eventInfo = "Filter by Mother's side of family";
                    break;
                case 3:
                    eventInfo = "Filter by Father's side of family";
                    break;
            }

            holder.eventInfo.setText(eventInfo);
        }

        else
            holder.eventInfo.setText("Filter by " + event + " events");

        holder.filterSwitch.setChecked(filterSwitches.get(position));


        // When a switch a clicked, update the filterData's boolean values of what should be displayed
        holder.filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String key = holder.eventTitle.getText().toString().toLowerCase();
                model.getFilterData().getEventsToFilter().put(key, isChecked);

                switch (key) {
                    case " female" :
                        model.getFilterData().setShowFemaleEvents(isChecked);
                        break;
                    case " male" :
                        model.getFilterData().setShowMaleEvents(isChecked);
                        break;
                    case " maternal" :
                        model.getFilterData().setShowMothersSide(isChecked);
                        break;
                    case " paternal" :
                        model.getFilterData().setShowFathersSide(isChecked);
                        break;
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventTitles.size();
    }
}
