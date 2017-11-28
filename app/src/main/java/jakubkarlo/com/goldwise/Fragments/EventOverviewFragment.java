package jakubkarlo.com.goldwise.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.concurrent.ExecutionException;

import jakubkarlo.com.goldwise.Downloaders.ParticipantsDownloader;
import jakubkarlo.com.goldwise.Models.Event;
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
        arcView = (DecoView)root.findViewById(R.id.dynamicArcView);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParticipantsDownloader participantsDownloader = new ParticipantsDownloader();
        JSONArray participants = null;
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


        if (participants != null) {

            for (int position = 0, minRange = 0, maxRange = 100, currentColor = Color.argb(255, 64, 100, 0); position < participants.length(); position++, currentColor +=25){

                JSONObject currentPerson = null;
                SeriesItem seriesItem = null;

                try {
                    currentPerson = participants.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (currentPerson!= null) {
                    try {
                        seriesItem = new SeriesItem.Builder(currentColor)
                                .setRange(minRange, maxRange, (float)currentPerson.getDouble("share"))
                                .setLineWidth(100f)
                                .setSeriesLabel(new SeriesLabel.Builder(currentPerson.getString("name")).build())
                                .build();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (seriesItem != null) {
                    arcView.addSeries(seriesItem);
                }


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
