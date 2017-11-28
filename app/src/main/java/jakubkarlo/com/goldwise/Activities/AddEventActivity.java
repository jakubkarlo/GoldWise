package jakubkarlo.com.goldwise.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jakubkarlo.com.goldwise.Models.Person;
import jakubkarlo.com.goldwise.R;

public class AddEventActivity extends AppCompatActivity {

    ImageView eventImage;
    EditText eventTitle;
    Bitmap eventBitmap;

    //REMEMBER ABOUT PERMISSIONS

    public void changePhoto(View view){

        getPhoto();

    }

    public void getPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public void addEvent(View view){

        // for testing
        JSONArray participants = new JSONArray();


        try {
            participants.put(new JSONObject().put("name", "rafał").put("share", 20));
            participants.put(new JSONObject().put("name", "kuuuba").put("share", 50));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // delete afterwards
        ParseObject parseEvent = new ParseObject("Event");
        parseEvent.put("title", eventTitle.getText().toString());
        parseEvent.put("description", "random");// just for now
        parseEvent.put("participants", participants); // another just for now

        // null conditions required too
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        eventBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        ParseFile imageFile = new ParseFile("image.png", byteArray);
        parseEvent.put("image", imageFile);
        parseEvent.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null){

                    Toast.makeText(AddEventActivity.this, "Event uploaded!", Toast.LENGTH_SHORT).show();

                } else{

                    Toast.makeText(AddEventActivity.this, "Event cannot be uploaded", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }
        });

        // add code to go back to event list


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        eventImage = (ImageView) findViewById(R.id.addEventImage);
        eventTitle = (EditText) findViewById(R.id.eventTitleEditText);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();

            try {
                eventBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                eventImage.setImageBitmap(eventBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
