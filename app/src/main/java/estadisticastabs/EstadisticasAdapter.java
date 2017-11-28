package estadisticastabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tab.JamundiFragment;
import tab.NorteFragment;
import tab.OesteFragment;
import tab.OrienteFragment;
import tab.PanceFragment;
import tab.SurFragment;

/**
 * Created by mac on 27/11/17.
 */

public class EstadisticasAdapter extends FragmentPagerAdapter {
    private String[] tabtitle = new String[]{"Categorías", "Problemáticas"};
    Context context;
    private int itemCount = 2;
    private int test = 0;   

    public EstadisticasAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position == 0) {
            fragment = new EstadisticasCategorias();
        }else if(position == 1) {
            fragment = new SurFragment();
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

