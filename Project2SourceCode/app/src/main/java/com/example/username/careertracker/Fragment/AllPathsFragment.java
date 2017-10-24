package com.example.username.careertracker.Fragment;

/**
 * Created by JMYE on 4/29/17.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.username.careertracker.AnimatorUtils;
import com.example.username.careertracker.QueryData.QueryData;
import com.example.username.careertracker.QueryData.WriteData;
import com.example.username.careertracker.R;
import com.example.username.careertracker.widget.ClipRevealFrame;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.List;


public class AllPathsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BUNDLE_TITLE = "title";

    View mView;

    public static String targetPath ="";

    Toast toast = null;
    View rootLayout;
    ClipRevealFrame menuLayout;
    ArcLayout arcLayout;
//    View centerItem;





    QueryData query;
    WriteData write;
    private DatabaseReference userRef;
    private FirebaseUser roofRef;
    final String refURL = "https://careertrackerv2.firebaseio.com/";

    // TODO: Rename and change types of parameters
    private String mtitle;

    private MeFragment.OnFragmentInteractionListener mListener;

    public AllPathsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AllPathsFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        AllPathsFragment fragment = new AllPathsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AllPathsFragment newInstance(String title, String tPath) {
        setTargetPath(tPath);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        AllPathsFragment fragment = new AllPathsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = mView.inflate(getActivity(), R.layout.like_a_path, null);
        rootLayout = mView.findViewById(R.id.root_layout);
        menuLayout = (ClipRevealFrame) mView.findViewById(R.id.menu_layout);
        arcLayout = (ArcLayout) mView.findViewById(R.id.arc_layout);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //do your operation here
                    if (v.getId() == R.id.fab) {
                        onFabClick(v);
                        return;
                    }

                    if (v instanceof Button) {
                        showToast((Button) v);
                    }
                }
            });
        }

        mView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do your operation here
                if (v.getId() == R.id.fab) {
                    onFabClick(v);
                    return;
                }

                if (v instanceof Button) {
                    showToast((Button) v);
                }
            }
        });

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

//    public void onClick(View v) {
//        if (v.getId() == R.id.fab) {
//            onFabClick(v);
//            return;
//        }
//
//        if (v instanceof Button) {
//            showToast((Button) v);
//        }
//
//    }

    private void showToast(Button btn) {
        if (toast != null) {
            toast.cancel();
        }

        String text = btn.getText()+":  ";
        toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();

    }

    private void onFabClick(View v) {
        int x = (v.getLeft() + v.getRight()) / 2;
        int y = (v.getTop() + v.getBottom()) / 2;
        float radiusOfFab = 1f * v.getWidth() / 2f;
        float radiusFromFabToRoot = (float) Math.hypot(
                Math.max(x, rootLayout.getWidth() - x),
                Math.max(y, rootLayout.getHeight() - y));

        if (v.isSelected()) {
            hideMenu(x, y, radiusFromFabToRoot, radiusOfFab);
        } else {
            showMenu(x, y, radiusOfFab, radiusFromFabToRoot);
        }
        v.setSelected(!v.isSelected());
    }

    private void showMenu(int cx, int cy, float startRadius, float endRadius) {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void hideMenu(int cx, int cy, float startRadius, float endRadius) {
        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = mView.findViewById(R.id.fab).getX() - item.getX();
        float dy = mView.findViewById(R.id.fab).getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = mView.findViewById(R.id.fab).getX() - item.getX();
        float dy = mView.findViewById(R.id.fab).getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

}
