package com.example.jasonjinsooyoo.freefindr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jasonjinsooyoo.freefindr.Tasks.RetrieveHttpDataTask;
import com.example.jasonjinsooyoo.freefindr.Utilities.EventAdapter;

public class EventFindR extends AppCompatActivity {

    // RECYCLERVIEW
    protected RecyclerView myRV;
    // Number of items to be stored
    protected static final int NUM_EVENTS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_find_r);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // RECYCLERVIEW SETUP
        myRV = (RecyclerView) findViewById(R.id.rv_events);
        // Set layout manager
        LinearLayoutManager myMan = new LinearLayoutManager(this);
        myRV.setLayoutManager(myMan);
        // Optimize myRV
        myRV.setHasFixedSize(true);
        // Set adapter
        EventAdapter myAdapter = new EventAdapter(NUM_EVENTS);
        myRV.setAdapter(myAdapter);
        try {
            new RetrieveHttpDataTask().execute("http://10.19.133.105:9859/attractions/");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_find_r, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
