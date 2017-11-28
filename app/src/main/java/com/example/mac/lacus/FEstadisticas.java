package com.example.mac.lacus;

import android.content.Context;
import android.graphics.Color;
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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import estadisticastabs.EstadisticasAdapter;
import estadisticastabs.EstadisticasCategorias;
import tab.MyAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FEstadisticas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FEstadisticas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FEstadisticas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager mPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;

    public FEstadisticas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_festadisticas, container, false);

        ViewPager mPager = (ViewPager) view.findViewById(R.id.view_pager);
        mPager.setAdapter(buildAdapter());

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.app_toolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);


        return view;
    }



    private PagerAdapter buildAdapter(){
        return (new EstadisticasAdapter(getActivity(), getChildFragmentManager()));
    }
}
