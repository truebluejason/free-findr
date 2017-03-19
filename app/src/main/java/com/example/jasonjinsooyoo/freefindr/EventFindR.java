package com.example.jasonjinsooyoo.freefindr;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jasonjinsooyoo.freefindr.ENUM.Categories;
import com.example.jasonjinsooyoo.freefindr.Tasks.RetrieveHttpDataTask;
import com.example.jasonjinsooyoo.freefindr.Utilities.EventAdapter;

public class EventFindR extends AppCompatActivity {

    protected ListView listView;

    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_find_r);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EventFindR thisActivity = this;

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event("abcd");
                event.setID(49);
                event.setDesc("THIS IS A DESCRIPTION");
                event.setLat(3.44);
                event.setLon(5.664);
                event.setType(Categories.ART);
                Intent intent = new Intent(thisActivity, SingleEventActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });

        eventAdapter = new EventAdapter(this);

        listView = (ListView) findViewById(R.id.event_list);

        listView.setAdapter(eventAdapter);

        EventManager.getInstance().setAdapter(eventAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
        
        try {
            new RetrieveHttpDataTask().execute("http://10.19.133.195:9859/attractions/");
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
