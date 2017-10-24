package com.example.username.careertracker.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.username.careertracker.PathsActivity;
import com.example.username.careertracker.QueryData.QueryData;
import com.example.username.careertracker.QueryData.WriteData;
import com.example.username.careertracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplicationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplicationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BUNDLE_TITLE = "title";
    private Switch aswitch;
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    private TextView pinComName;
    private TextView pinInterviewTime;
    private TextView pinDaysLeft;
    private TextView pinNumDay;




    QueryData query;
    WriteData write;
    private DatabaseReference userRef;
    private FirebaseUser roofRef;
    DatabaseReference pathsRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/";


    // TODO: Rename and change types of parameters
    private String mtitle;

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public ApplicationsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ApplicationsFragment newInstance(String title) {
            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_TITLE, title);
            ApplicationsFragment fragment = new ApplicationsFragment();
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mtitle = getArguments().getString(BUNDLE_TITLE);
        }
        userRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        roofRef = FirebaseAuth.getInstance().getCurrentUser();
        write = new WriteData(userRef, roofRef);
        query = new QueryData(userRef, roofRef);
        pathsRef = query.userRef.child("applications");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_card_view, container, false);
        listView = (ListView) view.findViewById(R.id.card_listView);
        aswitch = (Switch) view.findViewById(R.id.switch_ft_ps);

        pinComName  = (TextView)view.findViewById(R.id.pinComName);
        pinInterviewTime  = (TextView)view.findViewById(R.id.pinInterviewTime);
        pinDaysLeft  = (TextView)view.findViewById(R.id.pinDayLeft);
        pinNumDay  = (TextView)view.findViewById(R.id.pinNumDay);

//        applciationbtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ContactsDetails.class));
//            }
//        });
//        listView.addHeaderView(view);
//        listView.addFooterView(view);



        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
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

    private int getCountOfDaysLeft(String interviewTime) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm");
        Date curDate = sdf.parse(sdf.format(now));
        Date endDate = sdf.parse(interviewTime);
//        Log.i(TAG, "cur: "+curDate+"___________" + "end: " + endDate);
        long diff = (endDate.getTime() - curDate.getTime())/86400000;
//        Log.i(TAG, "Time diff:" +  diff);

        return (int)diff;
    }

    private int compareTwoTime(String s1, String s2) {
        try {
            Date t1 = new SimpleDateFormat("d MMM yyyy HH:mm").parse(s1);
            Date t2 = new SimpleDateFormat("d MMM yyyy HH:mm").parse(s2);
            return t1.compareTo(t2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getCountOfHoursLeft(String interviewTime) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm");
        Date curDate = sdf.parse(sdf.format(now));
        Date endDate = sdf.parse(interviewTime);
//        Log.i(TAG, "cur: "+curDate+"___________" + "end: " + endDate);
        long diff = (endDate.getTime() - curDate.getTime())/3600000;
//        Log.i(TAG, "Time diff:" +  diff);

        return (int)diff;

    }

    @Override
    public void onStart() {
        super.onStart();
        cardArrayAdapter = new CardArrayAdapter(getContext(), R.layout.list_card_view);
        ChildEventListener pathListener = new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashSet<String> cardMap = new HashSet<>();
                for(int i = 0; i < cardArrayAdapter.getCount(); i++) {
                    cardMap.add(cardArrayAdapter.getItem(i).getLine1());
                }
                HashMap<String,String> nodeMap = new HashMap<>();
                for (DataSnapshot stkKey: dataSnapshot.getChildren()) {
                    String targetPathName = "";
                    String comName = "";
                    String jobType = "";
                    String posType = "";
                    String year = "";
                    String inteviewTime = "";
                    String curStep = "";
                    for (DataSnapshot childKey: stkKey.getChildren()) {
                        if (childKey.getKey().equals("curStepInterviewTime")) {
                            inteviewTime = (String) childKey.getValue();
                            year = inteviewTime.split(" ")[2];
                        } else if (childKey.getKey().equals("comName")){
                            comName = (String) childKey.getValue();
                        } else if (childKey.getKey().equals("jobType")){
                            jobType = (String) childKey.getValue();

                        } else if (childKey.getKey().equals("posType")){
                            posType = (String) childKey.getValue();

                        } else if (childKey.getKey().equals("curStep")){
                            curStep = (String) childKey.getValue();

                        }
                    }
                    targetPathName = comName +" " + jobType+" "+posType+" " + year;
                    if (!nodeMap.containsKey(targetPathName)){
                        nodeMap.put(targetPathName,curStep + " : " + inteviewTime);
                    } else {
                        String prevT = nodeMap.get(targetPathName).split(" : ")[1];
                        if (compareTwoTime(prevT,inteviewTime) < 0) {
                            nodeMap.put(targetPathName,curStep + " : " + inteviewTime);
                        }
                    }
//                    Log.i(TAG, nodeMap.keySet()+"");
                }
                for(String pathKey: nodeMap.keySet()) {
                    String latestInterviewTime = nodeMap.get(pathKey).split(" : ")[1];
                    String latestStep = nodeMap.get(pathKey).split(" : ")[0];
                    int dateLeft = 0;
                    try {
                        dateLeft = getCountOfDaysLeft(latestInterviewTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    Log.i(TAG,">>>>> "+pathKey +" !!!!  "+dateLeft);
                    String cardLine1 = pathKey.split(" ")[0]+" : "+ latestStep;
                    String[] quicklookArray = {cardLine1.split(" : ")[0],cardLine1.split(" : ")[1],latestInterviewTime,""+dateLeft,"Day"};
                    if (dateLeft > 0) {
                        quicklookArray[4] = "Days";
                    }
                    if (!cardMap.contains(cardLine1) && dateLeft > 0) {
                        ReminderCard card = new ReminderCard(cardLine1,dateLeft,pathKey,quicklookArray);
                        cardArrayAdapter.add(card);
                    }
                    Collections.sort(cardArrayAdapter.ReminderCardList, new Comparator<ReminderCard>() {
                        @Override
                        public int compare(ReminderCard s1, ReminderCard s2) {
                            int dayLeft1 = s1.line2;
                            int dayLeft2 = s2.line2;
                            return dayLeft1 - dayLeft2;
                        }
                    });
                    aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String[] nextDue = cardArrayAdapter.ReminderCardList.get(0).getquickLookInfo();
                            if (isChecked) {
                                int hoursLeft=0;
                                try {
                                    hoursLeft = getCountOfHoursLeft(nextDue[2]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//                                pinComName.setText(String.format("%s Interview", nextDue[0]));
//                                pinInterviewTime.setText(nextDue[1]);
                                pinDaysLeft.setText(""+hoursLeft);
                                String unit = hoursLeft >1? "hours":"hour";
                                pinNumDay.setText(unit);
                            } else {
//                                pinComName.setText(String.format("%s Interview", nextDue[0]));
//                                pinInterviewTime.setText(nextDue[1]);
                                pinDaysLeft.setText(nextDue[3]);
                                pinNumDay.setText(nextDue[4]);

                            }

                        }
                    });
                    if (cardArrayAdapter.ReminderCardList.size() > 0) {
                        String[] nextDue = cardArrayAdapter.ReminderCardList.get(0).getquickLookInfo();
                        pinComName.setText(String.format("%s %s", nextDue[0],nextDue[1]));
                        pinInterviewTime.setText(nextDue[2]);
                        pinDaysLeft.setText(nextDue[3]);
                        pinNumDay.setText(nextDue[4]);
                        listView.setAdapter(cardArrayAdapter);
                    }

                }

//
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        pathsRef.addChildEventListener(pathListener);



    }

    private class CardArrayAdapter  extends ArrayAdapter<ReminderCard> {
        public static final String TAG = "ReminderCardArrayAdapter";
        private List<ReminderCard> ReminderCardList = new ArrayList<ReminderCard>();



        public CardArrayAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }



        @Override
        public void add(ReminderCard object) {
            ReminderCardList.add(object);
            super.add(object);
        }

        @Override
        public int getCount() {
            return this.ReminderCardList.size();
        }

        @Override
        public ReminderCard getItem(int index) {
            return this.ReminderCardList.get(index);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            final ReminderCardViewHolder viewHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.list_item_reminder_card, parent, false);
                viewHolder = new ReminderCardViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ReminderCardViewHolder)row.getTag();
            }

            ReminderCard item = getItem(position);
            updateClickState(viewHolder,item);
            return row;
        }

        @SuppressLint("LongLogTag")
        private void updateClickState(ReminderCardViewHolder holder, final ReminderCard item) {
            final String  tpName = item.targetPathName;
            holder.line1.setText(item.getLine1());
            holder.line2.setText(""+item.getLine2());
            holder.line2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editPathInent = new Intent(getActivity(), PathsActivity.class);
                    editPathInent.putExtra("path_name", tpName);
                    startActivity(editPathInent);
                }
            });
            holder.line1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editPathInent = new Intent(getActivity(), PathsActivity.class);
                    editPathInent.putExtra("path_name", tpName);
                    startActivity(editPathInent);
                }
            });
            holder.days.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] nextDue = item.getquickLookInfo();
                    pinComName.setText(String.format("%s %s", nextDue[0],nextDue[1]));
                    pinInterviewTime.setText(nextDue[2]);
                    pinDaysLeft.setText(nextDue[3]);
                    pinNumDay.setText(nextDue[4]);

                }
            });
            aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            String curInterviewTime = pinInterviewTime.getText().toString();
                            int tmphoursLeft=0;
                            try {
                                tmphoursLeft = getCountOfHoursLeft(curInterviewTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            pinDaysLeft.setText(""+tmphoursLeft);
                            String unit = tmphoursLeft >1? "hours":"hour";
                            pinNumDay.setText(unit);
                        } else {
                            String curInterviewTime = pinInterviewTime.getText().toString();
                            int tmpdayLeft=0;
                            try {
                                tmpdayLeft = getCountOfDaysLeft(curInterviewTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            pinDaysLeft.setText(""+tmpdayLeft);
                            String unit = tmpdayLeft >1? "days":"day";
                            pinNumDay.setText(unit);

                    }

                }
            });

        }

        public Bitmap decodeToBitmap(byte[] decodedByte) {
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }


    }
    private  static class ReminderCardViewHolder {

        private View view;

        private TextView line1;

        private TextView line2;

        private TextView days;
        private TextView infoDetail;

        private ReminderCardViewHolder(View view) {
            this.view = view;
            line1 = (TextView) view.findViewById(R.id.line1);
            line2 = (TextView) view.findViewById(R.id.line2);
            days = (TextView) view.findViewById(R.id.days);
            infoDetail = (TextView)view.findViewById(R.id.infoDetail);
        }
    }

    private class ReminderCard {
        private String line1;
        private int line2;
        private String targetPathName;
        private String[] infoQuickLook;


        public ReminderCard(String line1, int line2,String targetPathName,String[] infoQuickLook) {
            this.line1 = line1;
            this.line2 = line2;
            this.targetPathName = targetPathName;
            this.infoQuickLook = infoQuickLook;
        }

        public String getLine1() {
            return line1;
        }

        public int getLine2() {
            return line2;
        }
        public String getPathName() {
            return targetPathName;
        }
        public String[] getquickLookInfo() {
            if (infoQuickLook == null) {
                infoQuickLook = new String[5];
            }
            return infoQuickLook;
        }

    }


    @Override
    public void onResume() {

        super.onResume();
        new ApplicationsFragment();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    if(getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    }

                    return true;

                }

                return false;
            }
        });
    }

}
