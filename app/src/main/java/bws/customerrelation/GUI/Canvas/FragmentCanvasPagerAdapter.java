package bws.customerrelation.GUI.Canvas;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import bws.customerrelation.GUI.ActivitiesFragment;
import bws.customerrelation.GUI.Canvas.ShowCanvasFragmentMain;
import bws.customerrelation.GUI.Company.CompanyDataFragment;

/**
 * Created by jaje on 24-Sep-15.
 */
public class FragmentCanvasPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    public FragmentCanvasPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new ShowCanvasFragmentMain();
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
