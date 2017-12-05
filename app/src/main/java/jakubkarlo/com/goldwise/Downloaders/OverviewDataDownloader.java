package jakubkarlo.com.goldwise.Downloaders;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakubkarlo.com.goldwise.Fragments.EventOverviewFragment;

/**
 * Created by Jakub on 05.12.2017.
 */

public class OverviewDataDownloader extends AsyncTask<String, Void, Object> {


    @Override
    protected Object doInBackground(String... params) {

        if (Objects.equals(params[1], "budget")) {


        }

        else if (Objects.equals(params[1], "goalAmount")) {
            double goalAmount = 0;
            try {
                //change the conditions for the first one regarding your case!! and do it in async

                ParseObject eventPointer = ParseObject.createWithoutData("Event", params[0]);
                ParseQuery query = ParseQuery.getQuery("Saving");
                ParseObject newestSaving = query.include("eventID").whereEqualTo("eventID", eventPointer).getFirst();
//                if you would like to access the event!
//                ParseObject correspondingEvent = newestSaving.getParseObject("eventID");
                goalAmount = newestSaving.getDouble("goal");
//                amounts[1] = newestSaving.getDouble("currentState");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return goalAmount;
        }


        else if (Objects.equals(params[1], "currentState")) {
            double currentState = 0;
            try {

                ParseObject eventPointer = ParseObject.createWithoutData("Event", params[0]);
                ParseQuery query = ParseQuery.getQuery("Saving");
                ParseObject newestSaving = query.include("eventID").whereEqualTo("eventID", eventPointer).getFirst();
                currentState = newestSaving.getDouble("currentState");

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return currentState;
        }

        else if(Objects.equals(params[1], "debts")) {
            final ArrayList<String> latestDebts = new ArrayList<>();

            ParseObject eventPointer = ParseObject.createWithoutData("Event", params[0]);
            ParseQuery query = ParseQuery.getQuery("Debt").orderByDescending("updatedAt");
            query.include("eventID")
                    .whereEqualTo("eventID", eventPointer)
                    .setLimit(3)
                    .findInBackground(new FindCallback<ParseObject>() {


                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {

                            if (e == null || objects.size() > 0) {

                                for (ParseObject object : objects) {
                                    latestDebts.add(object.getString("who") + " owes " + object.getDouble("amount") + " to " + object.getString("toWhom"));
                                    Log.i("who", object.getString("who"));
                                }

                            } else {
                                e.printStackTrace();
                            }

                        }

                    });

            return latestDebts;
        }


        return null;
    }
}
