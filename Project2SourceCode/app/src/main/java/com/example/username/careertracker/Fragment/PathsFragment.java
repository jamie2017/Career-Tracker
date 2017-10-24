package com.example.username.careertracker.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.example.username.careertracker.QueryData.QueryData;
import com.example.username.careertracker.QueryData.WriteData;
import com.example.username.careertracker.R;
import com.example.username.careertracker.SendEmail;
import com.example.username.careertracker.UpdatePathActivity;
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
import java.util.List;


public class PathsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BUNDLE_TITLE = "title";

    View mView;
    private VerticalStepView mSetpview0;
    private FloatingActionButton followupEmail;
    private FloatingActionButton editPath;
    private TextView PathComName;


    public static String targetPath ="";

    // value to pass in new activity
    private String receiver_email; //
    private String apply_position; // Eg. backend
    private String company_name;
    private String position_type; // Eg. full-time
    private String job_Source;






    private List<String> curStepList;
    QueryData query;
    WriteData write;
    private DatabaseReference userRef;
    private FirebaseUser roofRef;
    DatabaseReference appStackRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/";

    // TODO: Rename and change types of parameters
    private String mtitle;

    private MeFragment.OnFragmentInteractionListener mListener;

    public PathsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PathsFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        PathsFragment fragment = new PathsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static PathsFragment newInstance(String title,String tPath) {
        setTargetPath(tPath);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        PathsFragment fragment = new PathsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private static void  setTargetPath(String tPath) {
        targetPath = tPath;
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
        appStackRef = query.userRef.child("applications").child(targetPath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = mView.inflate(getActivity(), R.layout.fragment_paths, null);
        PathComName = (TextView)mView.findViewById(R.id.PathComName);

        mSetpview0 = (VerticalStepView) mView.findViewById(R.id.step_view0);
        followupEmail = (FloatingActionButton)mView.findViewById(R.id.followup_email);
        editPath = (FloatingActionButton)mView.findViewById(R.id.edit_path);

        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void onStart() {
        super.onStart();
        PathComName.setText(targetPath);
        curStepList = new ArrayList<>();
        ChildEventListener pathListener = new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (curStepList.size() > 0) {
                    curStepList.remove(curStepList.size() - 1);
                }
                String curStep = dataSnapshot.getKey();
                String stepInfo = "";
                for (DataSnapshot childKey: dataSnapshot.getChildren()) {
                    if (childKey.getKey().equals("curStepInterviewTime")) {
                        stepInfo = (String) childKey.getValue();
                    } else if (childKey.getKey().equals("posType")) {
                        position_type = (String) childKey.getValue();
                    } else if (childKey.getKey().equals("jobType")) {
                        apply_position = (String) childKey.getValue();
                    } else if (childKey.getKey().equals("contactEmail")) {
                        receiver_email = (String) childKey.getValue();
                    } else if (childKey.getKey().equals("submitType")){
                        job_Source = (String) childKey.getValue();
                    } else if (childKey.getKey().equals("comName")) {
                        company_name = (String) childKey.getValue();
                    }
                }
                curStepList.add(curStep + " : " + stepInfo);
                Collections.sort(curStepList, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        try {
                            Date t1 = new SimpleDateFormat("d MMM yyyy HH:mm").parse(s1.split(" : ")[1]);
                            Date t2 = new SimpleDateFormat("d MMM yyyy HH:mm").parse(s2.split(" : ")[1]);
                            return t1.compareTo(t2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return -1;
                    }
                });
                curStepList.add("Offer");
                mSetpview0.reverseDraw(true)//default is true
                        .setStepsViewIndicatorComplectingPosition(curStepList.size() - 2)
                        .setLinePaddingProportion(1)
                        .setTextSize(16)
                        .setStepViewTexts(curStepList)
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.black))
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.black))
                        .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.black))
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.black))
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_check_circle_black_24dp))//Fail
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_marker_active))//pass
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//pending
                editPath.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent editPathInent = new Intent(getActivity(),UpdatePathActivity.class);
                        editPathInent.putExtra("CompanyName", company_name);
                        editPathInent.putExtra("ApplyPosition", apply_position);
                        editPathInent.putExtra("PositionType", position_type);
                        editPathInent.putExtra("SubmitType",job_Source);
                        editPathInent.putExtra("receiver_email",receiver_email);
                        startActivity(editPathInent);
                    }
                });
//                Log.i(TAG,">>>>> "+apply_position);
                followupEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent followupInent = new Intent(getActivity(),SendEmail.class);
                        followupInent.putExtra("receiver_email",receiver_email);
                        startActivity(followupInent);
                    }
                });

//
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                curStepList = new ArrayList<>();
//                for (DataSnapshot childKey : dataSnapshot.getChildren()) {
//                    curStepList.add(childKey.getKey());
//                }
//                curStepList.add("Offer!");
//                mSetpview0.reverseDraw(false)//default is true
//                        .setLinePaddingProportion(1)
//                        .setTextSize(14)
//                        .setStepViewTexts(curStepList)
//                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.black))
//                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.black))
//                        .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.black))
//                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.black))
//                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_check_circle_black_24dp))//Fail
//                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_marker_active))//pass
//                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//pending
//

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

        appStackRef.addChildEventListener(pathListener);


    }

    @Override
    public void onResume() {

        super.onResume();
        new PathsFragment();
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
