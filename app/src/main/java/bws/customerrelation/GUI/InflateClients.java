package bws.customerrelation.GUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateClients {
    LinearLayout _mLinearListView;
    ArrayList<BECompany> _allClients;
    Activity _context;
    static ArrayList<BECompany> _INFLATECLIENTS;
    final static String TAG = "Inflate";


    public InflateClients(Activity context, ArrayList<BECompany> list, LinearLayout layout) {
        _allClients = list;
        _context = context;
        _mLinearListView = layout;
        if (_INFLATECLIENTS == null) {
            _INFLATECLIENTS = new ArrayList<BECompany>();

        }
    }

    public void inflateView() {
        for (final BECompany c : _allClients) {
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mView = inflater.inflate(R.layout.rowtest, null);

            /**
             * getting id of row.xml
             */

            TextView mFirstName = (TextView) mView
                    .findViewById(R.id.companyName);
            final TextView mLastName = (TextView) mView
                    .findViewById(R.id.canvasAmount);
            final CheckBox mCheckBox = (CheckBox) mView
                    .findViewById(R.id.checkbox);
            mCheckBox.setVisibility(View.INVISIBLE);
            /**
             * set item into row
             */

            final String fName = c.getM_companyName();
            final String canvas = "Canvas amount: " + c.getM_telephone();
            mFirstName.setText(fName);
            mLastName.setText(canvas);

            /**
             * changes background to white
             */

            _mLinearListView.setBackgroundColor(Color.parseColor("#ffffff"));

            /**
             * add view in top linear
             */
            _mLinearListView.addView(mView);

            /**
             * IF the client is in_selectedClients, highlight it again
             */

            if (_INFLATECLIENTS != null) {
                for (BECompany cl : _INFLATECLIENTS) {
                    if (cl.getM_CompanyId() == c.getM_CompanyId()) {
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
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                        _INFLATECLIENTS.remove(c);
                    } else {
                        v.setBackgroundColor(Color.parseColor("#00B2EE"));
                        _INFLATECLIENTS.add(c);
                    }
                    MainActivity._SELECTEDCLIENTS = _INFLATECLIENTS;
                }
            });
        }
    }

    public ArrayList<BECompany> getSelectedClients() {
        return _INFLATECLIENTS;
    }
}
