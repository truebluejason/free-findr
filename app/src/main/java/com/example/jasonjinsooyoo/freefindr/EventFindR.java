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

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        eventAdapter = new EventAdapter(this);

        listView = (ListView) findViewById(R.id.event_list);

        listView.setAdapter(eventAdapter);

        EventManager.getInstance().setAdapter(eventAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                displaySingleEventActivity((Event)adapterView.getAdapter().getItem(position));
            }
        });
        
        try {
            new RetrieveHttpDataTask().execute("http://10.19.133.195:9859/attractions/");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void displaySingleEventActivity(Event event) {
        String[] eventData = new String[4];
        eventData[0] = event.getName();
        eventData[1] = event.getType().toString();
        eventData[2] = "12345";
        eventData[3] = event.getDescription();
        Intent intent = new Intent(this, SingleEventActivity.class);
        intent.putExtra("event", eventData);
        startActivity(intent);
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
