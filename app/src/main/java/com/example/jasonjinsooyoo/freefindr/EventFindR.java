package com.example.jasonjinsooyoo.freefindr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jasonjinsooyoo.freefindr.Tasks.RetrieveHttpDataTask;
import com.example.jasonjinsooyoo.freefindr.Utilities.EventAdapter;

public class EventFindR extends AppCompatActivity {

    // RECYCLERVIEW
    protected RecyclerView myRV;
    // Number of items to be stored
    // TODO: Add a list of events to be displayed
    protected static final int NUM_EVENTS = 15;

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
        
        try {
            new RetrieveHttpDataTask().execute("http://10.19.133.105:9859/attractions/");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        // Set adapter
        EventAdapter myAdapter = new EventAdapter(NUM_EVENTS);
        // Passes myAdapter to singleton to facilitate adding
        EventManager.getInstance().setAdapter(myAdapter);
        myRV.setAdapter(myAdapter);

        /* tests
        Event event1 = new Event("e1");
        Event event2 = new Event("e2");
        Event event3 = new Event("e3");
        event1.setID(1);
        event2.setID(2);
        event3.setID(3);
        event1.setDesc("yee");
        event2.setDesc("hee");
        event3.setDesc("bee");
        EventManager.getInstance().addEvent(event1);
        EventManager.getInstance().addEvent(event2);
        EventManager.getInstance().addEvent(event3);
        */
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
