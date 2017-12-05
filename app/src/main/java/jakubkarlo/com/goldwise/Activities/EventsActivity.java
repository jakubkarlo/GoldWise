package jakubkarlo.com.goldwise.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.ParseObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import jakubkarlo.com.goldwise.Downloaders.EventsDownloader;
import jakubkarlo.com.goldwise.Adapters.ImageAdapter;
import jakubkarlo.com.goldwise.R;

public class EventsActivity extends AppCompatActivity {

    private List<ParseObject> events;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        grid = (GridView) findViewById(R.id.eventsGrid);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addEventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

        //read the events using the async task
        EventsDownloader eventsDownloader = new EventsDownloader();
        try {
            events = eventsDownloader.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        grid.setAdapter(new ImageAdapter(EventsActivity.this, events));


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EventsActivity.this, SpecificEventActivity.class);
                intent.putExtra("eventID", events.get(position).getObjectId());
                startActivity(intent);
            }
        });


    }
}
