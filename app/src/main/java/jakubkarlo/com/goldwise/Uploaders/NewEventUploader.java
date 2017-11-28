package jakubkarlo.com.goldwise.Uploaders;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import jakubkarlo.com.goldwise.Activities.AddEventActivity;

/**
 * Created by Jakub on 28.11.2017.
 */

public class NewEventUploader extends AsyncTask<ParseObject, Void, String> {
    @Override
    protected String doInBackground(ParseObject... params) {

        ParseObject parseEvent = params[0];
        final String[] message = new String[1];

        parseEvent.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null){

                   message[0] = "Event uploaded!";

                } else{

                    e.printStackTrace();
                    message[0] =  "Event cannot be uploaded";


                }

            }
        });

        return message[0];

    }

}
