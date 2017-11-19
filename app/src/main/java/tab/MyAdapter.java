package tab;

/**
 * Created by mac on 16/11/17.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.example.mac.lacus.R;

import fragments.NorteFragment;

//*
/**
 * Created by Ujang Wahyu on 18/08/2016.
*/
public class MyAdapter extends FragmentPagerAdapter {
    private String[] tabtitle = new String[]{"Norte", "Sur", "Oeste", "Oriente", "Jamundí", "Pance"};
    Context context;
    private int itemCount = 6;

    public MyAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

            if(position == 0) {
                fragment = new NorteFragment();
            }else if(position == 1) {
                fragment = new SurFragment();
            }else if(position == 2) {
                fragment = new OesteFragment();
            }else if(position == 3) {
                fragment = new OrienteFragment();
            }else if(position == 4) {
                fragment = new JamundiFragment();
            }else if(position == 5) {
                fragment = new PanceFragment();
            }
            Bundle b = new Bundle();
            b.putInt("position", position);
            fragment.setArguments(b);
            return fragment;
    }

    @Override
    public int getCount() {
        return itemCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitle[position];
    }
}
