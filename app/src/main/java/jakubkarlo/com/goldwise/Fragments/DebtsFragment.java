package jakubkarlo.com.goldwise.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jakubkarlo.com.goldwise.Downloaders.EventDataDownloader;
import jakubkarlo.com.goldwise.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DebtsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DebtsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebtsFragment extends Fragment {
    private static final String EVENT_ID = "";
    private String eventID;

    ListView debtsListView;
    CheckBox paidCheckbox, notPaidCheckbox, forSavingCheckbox;

    ArrayList<ParseObject> debts;

    // just for now
    ArrayList<String> debtTitles;


    private OnFragmentInteractionListener mListener;

    public DebtsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DebtsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebtsFragment newInstance(String param1) {
        DebtsFragment fragment = new DebtsFragment();
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
        View root = inflater.inflate(R.layout.fragment_debts, container, false);
        debtsListView = (ListView) root.findViewById(R.id.debtsListView);
        paidCheckbox = (CheckBox) root.findViewById(R.id.paidCheckbox);
        notPaidCheckbox = (CheckBox) root.findViewById(R.id.notPaidCheckbox);
        forSavingCheckbox = (CheckBox) root.findViewById(R.id.savingsCheckbox);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventDataDownloader debtsDownloader = new EventDataDownloader();
        debts = null;


        try {
            debts = debtsDownloader.execute(eventID, "Debt").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        debtTitles = new ArrayList<>();
        if (debts != null) {
            for (ParseObject debt : debts) {

                debtTitles.add(debt.getString("who"));

            }
        }

        ArrayAdapter arrayAdapter = null;
        if (!debtTitles.isEmpty()) {
            arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, debtTitles);
            debtsListView.setAdapter(arrayAdapter);
        }

        paidCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whichChecked();

            }
        });

        notPaidCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whichChecked();

            }
        });

        forSavingCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whichChecked();

            }
        });

//        paidCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked){
//                    ArrayList<String> templist = new ArrayList<String>();
//
//                    for (ParseObject temp : finalDebts) {
//                        if (temp.getNumber("haha").equals(2)) {
//                            templist.add(temp.getString("who"));
//                        }
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
//                    debtsListView.setAdapter(adapter);
//
//                }
//                else{
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, debtTitles);
//                    debtsListView.setAdapter(adapter);
//
//                }
//
//            }
//        });

    }


    public void whichChecked() {

        ArrayList<String> templist = new ArrayList<String>();
        ArrayAdapter<String> adapter;


        //when all are checked
        if (paidCheckbox.isChecked() && notPaidCheckbox.isChecked() && forSavingCheckbox.isChecked()) {

            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, debtTitles);
            debtsListView.setAdapter(adapter);

        }

        //when paid checkbox is checked
        else if (paidCheckbox.isChecked()) {

            //paid and not paid are checked
            if (notPaidCheckbox.isChecked()) {

                for (ParseObject temp : debts) {
                    if (temp.getNumber("haha").equals(2) || temp.getNumber("haha").equals(1)) {
                        templist.add(temp.getString("who"));
                    }
                }
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
                debtsListView.setAdapter(adapter);

            }

            //paid and for saving are checked
            else if (forSavingCheckbox.isChecked()) {

                for (ParseObject temp : debts) {
                    if (temp.getNumber("haha").equals(2) || temp.getNumber("haha").equals(3)) {
                        templist.add(temp.getString("who"));
                    }
                }
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
                debtsListView.setAdapter(adapter);

            }

            //only paid is checked
            else {
                for (ParseObject temp : debts) {
                    if (temp.getNumber("haha").equals(2)) {
                        templist.add(temp.getString("who"));
                    }
                }
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
                debtsListView.setAdapter(adapter);
            }


        } else if (notPaidCheckbox.isChecked()) {

            // if not paid and for saving are checked
            if (forSavingCheckbox.isChecked()) {

                for (ParseObject temp : debts) {
                    if (temp.getNumber("haha").equals(1) || temp.getNumber("haha").equals(3)) {
                        templist.add(temp.getString("who"));
                    }
                }
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
                debtsListView.setAdapter(adapter);

            }

            // if only not paid is checked (other variants were done before)
            else {

                for (ParseObject temp : debts) {
                    if (temp.getNumber("haha").equals(1)) {
                        templist.add(temp.getString("who"));
                    }
                }
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
                debtsListView.setAdapter(adapter);


            }

        }

        // if for saving is checked
        else if (forSavingCheckbox.isChecked()) {

            for (ParseObject temp : debts) {
                if (temp.getNumber("haha").equals(3)) {
                    templist.add(temp.getString("who"));
                }
            }
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
            debtsListView.setAdapter(adapter);

        }

        // nothing is checked
        else{

            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, templist);
            debtsListView.setAdapter(adapter);

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
