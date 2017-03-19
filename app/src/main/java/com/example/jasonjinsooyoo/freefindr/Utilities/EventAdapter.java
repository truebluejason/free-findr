package com.example.jasonjinsooyoo.freefindr.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jasonjinsooyoo.freefindr.Event;
import com.example.jasonjinsooyoo.freefindr.EventManager;
import com.example.jasonjinsooyoo.freefindr.R;

import java.util.ArrayList;

/**
 * Created by jasonjinsooyoo on 2017-03-18.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    // Number of items to be stored
    int numOfItems;


    public EventAdapter(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Expands the parent's context
        Context context = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(context);
        boolean shouldAttachImm = false;
        int layoutID = R.layout.event_list_item;
        View myView = inflate.inflate(layoutID,parent,shouldAttachImm);

        // Creates an instance of viewholder
        EventViewHolder myEVH = new EventViewHolder(myView);
        return myEVH;
    }

    // Binds data to newly created view holder
    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numOfItems;
    }

    // Custom Viewholder Class
    public class EventViewHolder extends RecyclerView.ViewHolder {

        // Space for name to be stored
        TextView eventNameSpace;
        // Space for category to be stored
        TextView categorySpace;
        // 15 Events to be displayed
        ArrayList displayedEvents;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventNameSpace = (TextView) itemView.findViewById(R.id.viewholder_instance);
            categorySpace = (TextView) itemView.findViewById(R.id.viewholder_instance_category);
            displayedEvents = EventManager.getInstance().getEventList();
        }

        // Binds data to view
        public void bind(int viewContent) {
            eventNameSpace.setText(((Event) displayedEvents.get(viewContent)).getName());
            categorySpace.setText(((Event) displayedEvents.get(viewContent)).getDescription());
        }
    }
}
