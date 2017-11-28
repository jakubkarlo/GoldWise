package jakubkarlo.com.goldwise.Activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import jakubkarlo.com.goldwise.ImageAdapter;
import jakubkarlo.com.goldwise.R;

public class EventsActivity extends AppCompatActivity {

    private List<ParseObject> events;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        grid = (GridView)findViewById(R.id.eventsGrid);
        events = new ArrayList<ParseObject>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addEventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    Log.i("FindInBackground", "Success");


                }

                if (objects.size() > 0){

                    for (ParseObject object : objects){
                        events.add(object);
                        Log.i("Title", object.getString("title"));

                    }
                    grid.setAdapter(new ImageAdapter(EventsActivity.this, events));
                }


            }
        });




//        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EventsActivity.this, SpecificEventActivity.class);
                intent.putExtra("eventTitle", events.get(position).get("title").toString());
                startActivity(intent);
            }
        });


    }
}
