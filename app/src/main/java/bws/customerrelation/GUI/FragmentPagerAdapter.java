package bws.customerrelation.GUI;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import bws.customerrelation.GUI.Company.CompanyDataFragment;

/**
 * Created by jaje on 24-Sep-15.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.v("pager", "lavet");
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new CompanyDataFragment();
            case 1:
                // Games fragment activity
                return new ActivitiesFragment();
            default:
                return new CompanyDataFragment();
        }
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
