package jakubkarlo.com.goldwise.Downloaders;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 05.12.2017.
 */

public class LatestDebtsDownloader extends AsyncTask<String, Void, ArrayList<String>> {


    @Override
    protected ArrayList<String> doInBackground(String... params) {
        final ArrayList<String> latestDebts = new ArrayList<>();

        ParseObject eventPointer = ParseObject.createWithoutData("Event", params[0]);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Debt").orderByDescending("updatedAt");
        query.include("eventID")
                .whereEqualTo("eventID", eventPointer)
                .setLimit(Integer.parseInt(params[1]))
                .findInBackground(new FindCallback<ParseObject>() {


                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null || objects.size() > 0){

                            for (ParseObject object:objects) {
                                latestDebts.add(object.getString("who") + " owes " + object.getDouble("amount") + " to " + object.getString("toWhom"));
                            }

                        }

                        else{
                            e.printStackTrace();
                        }

                    }

                });
        return latestDebts;
    }
}
