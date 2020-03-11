package com.example.goodb.bighugeproject.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goodb.bighugeproject.R;
import com.example.goodb.bighugeproject.activities.EventActivity;
import com.example.goodb.bighugeproject.activities.FilterActivity;
import com.example.goodb.bighugeproject.activities.MainActivity;
import com.example.goodb.bighugeproject.activities.PersonActivity;
import com.example.goodb.bighugeproject.activities.SearchActivity;
import com.example.goodb.bighugeproject.dataHolders.Model;
import com.example.goodb.bighugeproject.ui.main.MapFragment;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import models.EventsModel;
import models.PersonsModel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    static Model model = Model.getInstance();
    private ArrayList<Object> listOfSearchResults = new ArrayList<>();
    private Context mContext;

    public SearchAdapter(ArrayList<Object> listOfSearchResults, Context mContext) {
        this.listOfSearchResults = listOfSearchResults;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_search, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        // Display all the persons related to the search query, up to the
        // correct position amount set in the model class
        if (position < model.getPersonCount())
            displayPersons(viewHolder, position);


        // When the persons have all been pumped through the set,
        // then switch to events
        else
            displayEvents(viewHolder, position);


        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.txt2.getText().toString().equals("")) {
                    // This will store the information about the person
                    // and start the person activity
                    PersonsModel p = (PersonsModel) listOfSearchResults.get(position);
                    model.setPersonModel(p);
                    Activity a = (SearchActivity) model.getSearchActivityContext();
                    Intent intent = new Intent(a, PersonActivity.class);
                    a.startActivity(intent);

                } else {

                    // This will store the information about the event
                    // and start the event activity
                    EventsModel e = (EventsModel) listOfSearchResults.get(position);
                    PersonsModel p = model.getPersonsMap().get(e.getPersonID());
                    model.setEventsModel(e);
                    model.setPersonModel(p);
                    Activity a = (SearchActivity) model.getSearchActivityContext();
                    Intent intent = new Intent(a, EventActivity.class);
                    a.startActivity(intent);


                }
            }
        });
    }


    private void displayEvents(ViewHolder viewHolder, int position) {
        EventsModel p = (EventsModel) listOfSearchResults.get(position);
        viewHolder.txt1.setText(p.getEventType() + ": " + p.getCity() + ", " + p.getCountry() +
                " (" + p.getYear() + ")");
        viewHolder.txt2.setText(model.getPersonsMap().get(p.getPersonID()).getFirstName() + " " +
                model.getPersonsMap().get(p.getPersonID()).getLastName());
        Activity a = (MainActivity) model.getMainActivityContext();

        // Set the correct icon of event
        final Drawable genderIcon = new IconDrawable(a, FontAwesomeIcons.fa_compass).
                colorRes(R.color.common_google_signin_btn_text_light).sizeDp(6);
        viewHolder.imageView.setImageDrawable(genderIcon);
    }

    private void displayPersons(ViewHolder viewHolder, int position) {
        PersonsModel p = (PersonsModel) listOfSearchResults.get(position);
        viewHolder.txt1.setText(p.getFirstName() + " " + p.getLastName());
        viewHolder.txt2.setText("");
        Activity a = (MainActivity) model.getMainActivityContext();

        // Set the correct icon of male or female
        if (p.getGender().toLowerCase().equals("f")) {
            final Drawable genderIcon = new IconDrawable(a, FontAwesomeIcons.fa_female).
                    colorRes(R.color.female_icon).sizeDp(10);
            viewHolder.imageView.setImageDrawable(genderIcon);
        }
        else {
            final Drawable genderIcon = new IconDrawable(a, FontAwesomeIcons.fa_male).
                    colorRes(R.color.male_icon).sizeDp(10);
            viewHolder.imageView.setImageDrawable(genderIcon);
        }
    }

    @Override
    public int getItemCount() {
        return listOfSearchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txt1, txt2;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            layout = itemView.findViewById(R.id.layout);
        }
    }

}
