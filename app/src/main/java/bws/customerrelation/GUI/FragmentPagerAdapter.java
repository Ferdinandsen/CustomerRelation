package bws.customerrelation.GUI;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by jaje on 24-Sep-15.
 */
public class FragmentPagerAdapter  extends android.support.v4.app.FragmentPagerAdapter{
    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new CompanyDataFragment();
            case 1:
                // Games fragment activity
                return new CompanyDataFragment();
            case 2:
                // Movies fragment activity
                return new CompanyDataFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
