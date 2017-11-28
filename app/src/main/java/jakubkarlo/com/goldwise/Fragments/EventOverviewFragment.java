package jakubkarlo.com.goldwise.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import jakubkarlo.com.goldwise.Downloaders.ParticipantsDownloader;
import jakubkarlo.com.goldwise.Models.Event;
import jakubkarlo.com.goldwise.Models.Person;
import jakubkarlo.com.goldwise.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventOverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventOverviewFragment extends Fragment {

    DecoView arcView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EVENT_ID = "";


    // TODO: Rename and change types of parameters
    private String eventID;


    private OnFragmentInteractionListener mListener;

    public EventOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EventOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventOverviewFragment newInstance(String param1) {
        EventOverviewFragment fragment = new EventOverviewFragment();
        Bundle args = new Bundle();
        fragment.eventID = param1;

        args.putString(EVENT_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_event_overview, container, false);
        arcView = (DecoView) root.findViewById(R.id.dynamicArcView);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParticipantsDownloader participantsDownloader = new ParticipantsDownloader();
        JSONArray participants = null;
        ArrayList<Person> people = new ArrayList<>();

        try {
            participants = participantsDownloader.execute(EventOverviewFragment.this.eventID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .setLineWidth(100f)
                .build());

        //write all JSONs to Person Array and sum their shares
        if (participants != null) {

            double sharesSum = 0;
            for (int i = 0; i < participants.length(); i++) {

                try {
                    JSONObject currentPerson = participants.getJSONObject(i);
                    people.add(new Person(currentPerson.getString("name"), currentPerson.getDouble("share"), currentPerson.getInt("color")));
                    sharesSum += currentPerson.getDouble("share");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            // sort the people in order to get the right chart


            // draw the chart of shares with already calculated sum
            // do it recursively for the right chart
            for (int i = people.size() - 1; i >= 0 ; i--) {
                //seems for the first time it's calculated twice
                double sum = sumPersonShare(people, i);
                drawChart(people, i, sharesSum, sum);
                //add labels and percentages for all participants
                Log.i("Sum", String.valueOf(sum));
            }

        }


    }


    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public double sumPersonShare(ArrayList<Person> people, int position){
        if (position == 0){
            return people.get(position).getShare();
        }
        else{
            return people.get(position).getShare() + sumPersonShare(people, position-1);
        }
    }


    public void drawChart(ArrayList<Person> people, int position, double sharesSum, double currentShare) {


        SeriesItem seriesItem = null;
            seriesItem = new SeriesItem.Builder(people.get(position).getColor())
                    .setRange(0, (float) sharesSum, (float)currentShare)
                    .setLineWidth(100f)
                    .setSeriesLabel(new SeriesLabel.Builder(people.get(position).getName()).build())
                    .build();

            if (seriesItem != null) {
                arcView.addSeries(seriesItem);
            }


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
