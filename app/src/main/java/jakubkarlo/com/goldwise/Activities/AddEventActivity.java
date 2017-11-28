package jakubkarlo.com.goldwise.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import jakubkarlo.com.goldwise.R;
import jakubkarlo.com.goldwise.Uploaders.NewEventUploader;


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
            participants.put(new JSONObject().put("name", "rafał").put("share", 20).put("color", Color.argb(255, 64, 100, 0)));
            participants.put(new JSONObject().put("name", "kuuuba").put("share", 50).put("color", Color.argb(255, 255, 0, 0)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // delete afterwards, do it as an asynchronous task
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

        NewEventUploader eventUploader = new NewEventUploader();
        String message = null;
        try {
            message = eventUploader.execute(parseEvent).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (message != null) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

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
