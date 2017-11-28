package jakubkarlo.com.goldwise.Downloaders;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import jakubkarlo.com.goldwise.Activities.EventsActivity;
import jakubkarlo.com.goldwise.ImageAdapter;
import jakubkarlo.com.goldwise.Models.Event;

/**
 * Created by Jakub on 28.11.2017.
 */

public class EventsDownloader extends AsyncTask<String, Void, ArrayList<ParseObject>> {
    @Override
    protected ArrayList<ParseObject> doInBackground(String... params) {

        final ArrayList<ParseObject> events = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    Log.i("FindInBackground", "Success");

                }

                else{}

                if (objects.size() > 0){

                    for (ParseObject object : objects){
                        events.add(object);
                        Log.i("Title", object.getString("title"));

                    }

                }

            }
        });

        // NULL EXCEPTIONS?!
        return events;

    }
}
