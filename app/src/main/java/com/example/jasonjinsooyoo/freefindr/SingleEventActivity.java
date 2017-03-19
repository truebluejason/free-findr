package com.example.jasonjinsooyoo.freefindr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Alec on 2017-03-19.
 */

public class SingleEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        Intent intent = getIntent();
        Event event = (Event)intent.getParcelableExtra("event");

        TextView nameView = (TextView)findViewById(R.id.name_view);
        TextView typeView = (TextView)findViewById(R.id.type_view);
        TextView distanceView = (TextView)findViewById(R.id.distance_view);
        TextView descriptionView = (TextView)findViewById(R.id.description_view);

        nameView.setText(event.getName());
        typeView.setText(event.getType());
        distanceView.setText(event.getType());
        descriptionView.setText(event.getDescription());
    }


}
