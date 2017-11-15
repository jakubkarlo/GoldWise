package jakubkarlo.com.goldwise;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        GridView grid = (GridView)findViewById(R.id.eventsGrid);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addEventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        items = new ArrayList<>();
        items.add("elo");
        items.add("320");

        grid.setAdapter(new ImageAdapter(this, items));
//        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EventsActivity.this, SpecificEventActivity.class);
                intent.putExtra("eventTitle", items.get(position));
                startActivity(intent);
            }
        });


    }
}
