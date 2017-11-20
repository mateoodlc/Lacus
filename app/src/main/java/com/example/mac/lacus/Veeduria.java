package com.example.mac.lacus;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tab.MyAdapter;

public class Veeduria extends AppCompatActivity {

  //  private MyPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mPager;
    static TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veeduria);

        //mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);


        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(new MyAdapter(this, getSupportFragmentManager()));

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Veeduria.this, MapsActivity.class);
                startActivity(intent);
                finish();            }
        });
       // mText = (TextView) findViewById(R.id.mText);

    }

 /*   public static class MyFragment extends Fragment{
        public MyFragment(){

        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            TextView myText = new TextView(getActivity());
            myText.setText("Estoy funcionando");
            mText.setText("holi");
            myText.setGravity(Gravity.CENTER);
            return myText;
        }
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter{

        public MyPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Veeduria.MyFragment myFragment = new Veeduria.MyFragment();
            return myFragment;
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
*/
}
