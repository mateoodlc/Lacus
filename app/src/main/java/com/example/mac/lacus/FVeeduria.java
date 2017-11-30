package com.example.mac.lacus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import tab.MyAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FVeeduria.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FVeeduria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FVeeduria extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mPager;

    //Toolbar mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
    //mToolbar.setSupportActionBar(mToolbar);


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public FVeeduria() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View result = inflater.inflate(R.layout.fragment_fveeduria, container, false);

        ViewPager mPager = (ViewPager) result.findViewById(R.id.view_pager);



        Toolbar mToolbar = (Toolbar) result.findViewById(R.id.app_toolbar);

        mPager.setAdapter(buildAdapter());
       // mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), this));

        TabLayout mTabLayout = (TabLayout) result.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);
        return result;
    }

    private PagerAdapter buildAdapter(){
        return (new MyAdapter(getActivity(), getChildFragmentManager()));
    }


}
