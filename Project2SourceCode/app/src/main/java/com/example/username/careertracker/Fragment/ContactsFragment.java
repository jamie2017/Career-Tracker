package com.example.username.careertracker.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.username.careertracker.AddCompanies;
import com.example.username.careertracker.ContactsDetails;
import com.example.username.careertracker.EditApplications;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BUNDLE_TITLE = "title";

    private TableRow addCompanyTR;
    private TableRow addApplicationTR;

    private static final int HIGHLIGHT_COLOR = 0x999be6ff;

    // list of data items
//    String[] tmpComArr = {"Adobe",
//            "Airbnb",
//            "Amazon",
//            "Apple",
//            "Cisco",
//            "Dell",
//            "Dropbox",
//            "eBay",
//            "Facebook",
//            "Google",
//            "HP",
//            "IBM",
//            "Intel",
//            "Linkedin",
//            "Marketo",
//            "Microsoft",
//            "NetApp",
//            "Nokia",
//            "Oracle",
//            "Paypal",
//            "Pinterest",
//            "Qualcomm",
//            "Salesforce",
//            "Samsung",
//            "SAP",
//            "Slack",
//            "Symantec",
//            "Uber",
//            "WalmartLab",
//            "WMware",
//            "Yahoo",
//            "Yelp"};
    List<String> tmpComArr ;
    List<String> tmpContactArr;

    private List<ListData> mDataList;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private ListView listView;
    private SampleAdapter listContactAdp;
    private SampleAdapter listContactDetailAdp;



    WriteData write;
    QueryData query;
    //our database reference object
    private DatabaseReference userLoginRef;
    private FirebaseUser roofRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/";


    private String contactName;
    private String contactEmail;
    private String jobTitle;


    // TODO: Rename and change types of parameters
    private String mtitle;

    private OnFragmentInteractionListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mtitle = getArguments().getString(BUNDLE_TITLE);
        }
        userLoginRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        roofRef = FirebaseAuth.getInstance().getCurrentUser();
        write = new WriteData(userLoginRef,roofRef);
        query = new QueryData(userLoginRef,roofRef);
        tmpComArr = new ArrayList<>();

//        if (tmpComArr == null || tmpComArr.length < 1) {
//            queryFromFirebase();
//        }
//        getListDataFromArray();
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        addCompanyTR = (TableRow) view.findViewById(R.id.fg_add_company);
        addApplicationTR = (TableRow) view.findViewById(R.id.fg_add_application);
        addApplicationTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditApplications.class));
            }
        });
        addCompanyTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCompanies.class));
            }
        });
//        queryFromFirebase();
        DatabaseReference targetComRef = query.userRef.child("company");
        targetComRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashSet<String> comMap = new HashSet<>();
                for(String pre:tmpComArr) {
                    comMap.add(pre);
                }
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    String curCom = childKey.getValue().toString().toUpperCase();
                    if(!comMap.contains(curCom) && childKey.getKey().equals("comName")) {
                        tmpComArr.add(curCom);
                        comMap.add(curCom);
//                        Log.i(TAG,">>>>>>>>>>>>>>>>>>> "+curCom);
                    }
                }
                getListDataFromArray();
                listContactAdp = new SampleAdapter();
                listView.setAdapter(listContactAdp);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HashSet<String> comMap = new HashSet<>();
                for(String pre:tmpComArr) {
                    comMap.add(pre);
                }
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    String curCom = childKey.getValue().toString().toUpperCase();
                    if(!comMap.contains(curCom) && childKey.getKey().equals("comName")) {
                        tmpComArr.add(curCom);
                        comMap.add(curCom);
                        Log.i(TAG,">>>>>>>>>>>>>>>>>>> "+curCom);
                    }
                }
                getListDataFromArray();
                listContactAdp = new SampleAdapter();
                listView.setAdapter(listContactAdp);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashSet<String> comMap = new HashSet<>();
                for(String pre:tmpComArr) {
                    comMap.add(pre);
                }
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    String curCom = childKey.getValue().toString().toUpperCase();
                    if(!comMap.contains(curCom) && childKey.getKey().equals("comName")) {
                        tmpComArr.add(curCom);
                        comMap.add(curCom);
                        Log.i(TAG,">>>>>>>>>>>>>>>>>>> "+curCom);
                    }
                }
                getListDataFromArray();
                listContactAdp = new SampleAdapter();
                listView.setAdapter(listContactAdp);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });










        return view;
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


    private class SampleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public ListData getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(),R.layout.list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData item = getItem(position);

            // provide support for selected state
            updateCheckedState(holder, item);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when the image is clicked, update the selected state
                    ListData data = getItem(position);
                    data.setChecked(!data.isChecked);
                    updateCheckedState(holder, data);
                }
            });
            holder.textView.setText(item.data);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String curCom = holder.textView.getText().toString();
                    Log.i(TAG, "company is click here");
                    Log.i(TAG, curCom);
                    showContactDetail(curCom);
                }
            });

            return convertView;
        }

        private void updateCheckedState(ViewHolder holder, final ListData item) {
            final String comName = item.data;
            if (item.isChecked) {
                holder.imageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
                holder.view.setBackgroundColor(HIGHLIGHT_COLOR);
                holder.checkIcon.setVisibility(View.VISIBLE);
                addApplicationTR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nextIntent = new Intent(getActivity(), EditApplications.class);
                        nextIntent.putExtra("CompanyName",comName);
                        startActivity(nextIntent);
                    }
                });
                addCompanyTR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nextIntent = new Intent(getActivity(), AddCompanies.class);
                        nextIntent.putExtra("CompanyName",comName);
                        startActivity(nextIntent);
                    }
                });
            }
            else {
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)), mColorGenerator.getColor(item.data));
                holder.imageView.setImageDrawable(drawable);
                holder.view.setBackgroundColor(Color.TRANSPARENT);
                holder.checkIcon.setVisibility(View.GONE);
            }
        }


    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;

        private TextView textView;

        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        }
    }

    private static class ListData {

        private String data;

        private boolean isChecked;

        public ListData(String data) {
            this.data = data;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

    }

    private  void getListDataFromArray(){
        mDataList = new ArrayList<>();
        Collections.sort(tmpComArr);
        for (String a:tmpComArr){
            mDataList.add(new ListData(a));
        }
//        Log.i(TAG,"!!!!!!!!!!!!!! "+mDataList.size());

    }


    @Override
    public void onResume() {

        super.onResume();
        new ContactsFragment();
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

    private void showContactDetail(String curCom) {
        final String targetCom = curCom;
        Log.i(TAG,">>>>>>>>>>>>>>>>>>> "+curCom);

        DatabaseReference targetContactRef = query.userRef.child("contacts");
        targetContactRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String cName = "";
                String contactName = "";
                String conEmail = "";
                String conTitle = "";
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    if (childKey.getKey().equals("comId")) {
                        cName = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("email")){
                        conEmail = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("jobTitle")){
                        conTitle = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("Name")){
                        contactName = childKey.getValue().toString();
                    }
                }
                if (cName.toUpperCase().equals(targetCom.toUpperCase())) {
                    callContactDetailView(cName,contactName,conEmail,conTitle);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String cName = "";
                String contactName = "";
                String conEmail = "";
                String conTitle = "";
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    if (childKey.getKey().equals("comId")) {
                        cName = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("email")){
                        conEmail = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("jobTitle")){
                        conTitle = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("Name")){
                        contactName = childKey.getValue().toString();
                    }
                }
                if (cName.toUpperCase().equals(targetCom.toUpperCase())) {
                    callContactDetailView(cName,contactName,conEmail,conTitle);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String cName = "";
                String contactName = "";
                String conEmail = "";
                String conTitle = "";
                for(DataSnapshot childKey: dataSnapshot.getChildren()) {
                    if (childKey.getKey().equals("comId")) {
                        cName = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("email")){
                        conEmail = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("jobTitle")){
                        conTitle = childKey.getValue().toString();
                    } else if (childKey.getKey().equals("Name")){
                        contactName = childKey.getValue().toString();
                    }
                }
                if (cName.toUpperCase().equals(targetCom.toUpperCase())) {
                    callContactDetailView(cName,contactName,conEmail,conTitle);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }


    private void callContactDetailView(String cName, String contactName,String conEmail, String conTitle) {
        Intent nextInent = new Intent(getActivity(), ContactsDetails.class);
        nextInent.putExtra("contactViewTitle",cName+" " + conTitle+ " "+contactName);
        nextInent.putExtra("comName",cName);
        nextInent.putExtra("contactName",contactName);
        nextInent.putExtra("contactEmail",conEmail);
        nextInent.putExtra("contactTitle",conTitle);
        startActivity(nextInent);
    }

}
