package bws.customerrelation.GUI.InflateLists;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import bws.customerrelation.GUI.MainActivity;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateCompanies {
    LinearLayout _mLinearListView;
    ArrayList<BECompany> _allClients;
    Activity _context;
    static ArrayList<BECompany> _INFLATECLIENTS;
    final static String TAG = "InflateCompanies";

    public InflateCompanies(Activity context, ArrayList<BECompany> list, LinearLayout layout) {
        _allClients = list;
        _context = context;
        _mLinearListView = layout;
        if (_INFLATECLIENTS == null) {
            _INFLATECLIENTS = new ArrayList<BECompany>();

        }
    }

    public void inflateView() {

        int pos = 0;
        for (final BECompany c : _allClients) {
            /**
             * inflate items/ add items in a linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mView = inflater.inflate(R.layout.cell_main_activity, null);
            mView.setTag(pos);
            /**
             * getting id of row.xml
             */

            TextView mFirstName = (TextView) mView
                    .findViewById(R.id.companyName);
            final CheckBox mCheckBox = (CheckBox) mView
                    .findViewById(R.id.checkbox);
            mCheckBox.setVisibility(View.INVISIBLE);

            /**
             * set item into row
             */

            final String fName = c.getM_companyName();
            mFirstName.setText(fName);

            /**
             * changes background of the view
             */
            setColorOnView(mView);


            /**
             * add oldView in top linear
             */
            _mLinearListView.addView(mView);

            /**
             * IF the client is in_selectedClients, highlight it again
             */

            if (_INFLATECLIENTS != null) {
                for (BECompany cl : _INFLATECLIENTS) {
                    if (cl.getM_companyId().equals(c.getM_companyId())) {
                        mView.setBackgroundColor(Color.parseColor("#00B2EE"));
                    }
                }
            }

            /**
             * get item row on click
             */

            mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onMulitipleClickitemlist(v);
                }

                private void onMulitipleClickitemlist(View v) {
                    boolean isChecked = mCheckBox.isChecked();
                    mCheckBox.setChecked(!isChecked);

                    if (isChecked) {
                        setColorOnView(v);
                        _INFLATECLIENTS.remove(c);
                    } else {
                        v.setBackgroundColor(Color.parseColor("#00B2EE"));
                        _INFLATECLIENTS.add(c);
                    }
                    MainActivity.SELECTEDCOMPANIES = _INFLATECLIENTS;
                }
            });
            pos++;
        }
    }

    public ArrayList<BECompany> getSelectedClients() {
        return _INFLATECLIENTS;
    }

    private void setColorOnView(View mView) {
        int localpos = (Integer) mView.getTag();
        if (localpos % 2 == 0) {
            mView.setBackgroundColor(Color.parseColor("#B2E5FF"));
        } else {
            mView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
}
