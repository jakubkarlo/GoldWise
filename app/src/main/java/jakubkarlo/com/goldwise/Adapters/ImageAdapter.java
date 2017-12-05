package jakubkarlo.com.goldwise.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import jakubkarlo.com.goldwise.R;

/**
 * Created by Jakub on 07.11.2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<ParseObject> events;
    Bitmap eventImage;

    public ImageAdapter(Context context, List<ParseObject> events) {
        this.context = context;
        this.events = events;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;


        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.events_grid_item, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(events.get(position).get("title").toString());

            // set image based on selected text
            final ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            // code to obtain image YOU'LL NEED PHOTO COMPRESSION
            ParseFile file = (ParseFile)events.get(position).get("image");

            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {

                    if (e == null & data != null){

                        eventImage = BitmapFactory.decodeByteArray(data,0, data.length);
                        imageView.setImageBitmap(eventImage);

                    }

                }
            });



        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // not sure
    }

}