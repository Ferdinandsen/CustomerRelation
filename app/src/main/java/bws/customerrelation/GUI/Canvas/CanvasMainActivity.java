package bws.customerrelation.GUI.Canvas;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import bws.customerrelation.R;

/**
 * Created by jaje on 01-Oct-15.
 */
public class CanvasMainActivity extends ActionBarActivity {

    private ViewPager viewPager;
    private FragmentCanvasPagerAdapter mAdapter;
    private android.support.v7.app.ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Canvas", "Contacs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_tabs);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);

        actionBar = getSupportActionBar();
        mAdapter = new FragmentCanvasPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener abTl = new testTablistner();
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(abTl));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class testTablistner implements ActionBar.TabListener {

        @Override
        public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        }
    }
}

