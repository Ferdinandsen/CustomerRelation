package bws.customerrelation.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateAdapter {
    LinearLayout mLinearListView;
    ArrayList<BEClient> _allClients;
    Context _context;

    ArrayList<BEClient> _selectedClients;
    ArrayList<View> _views;

    public InflateAdapter(Context context, ArrayList<BEClient> list, LinearLayout layout) {
        _allClients = list;
        _context = context;
        mLinearListView = layout;

    }

    public void inflateView() {
        for (final BEClient c : _allClients) {
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater.inflate(R.layout.rowtest, null);
            /**
             * getting id of row.xml
             */

            TextView mFirstName = (TextView) mLinearView
                    .findViewById(R.id.companyName);
            final TextView mLastName = (TextView) mLinearView
                    .findViewById(R.id.canvasAmount);
            final CheckBox mCheckBox = (CheckBox) mLinearView
                    .findViewById(R.id.checkbox);
            /**
             * set item into row
             */
            final String fName = c.getFirstName();
            final String lName = "" + c.getId();
            mFirstName.setText(fName);
            mLastName.setText(lName);

            /**
             * changes background to white
             */

            mLinearListView.setBackgroundColor(Color.parseColor("#ffffff"));

            /**
             * add view in top linear
             */

            mLinearListView.addView(mLinearView);

            /**
             * IF the client is in_selectedClients, highlight it again
             */
            if (_selectedClients != null) {
                for (BEClient cl : _selectedClients) {
                    if (cl.getId() == c.getId()) {
                        mLinearView.setBackgroundColor(Color.parseColor("#00B2EE"));

                    }
                }
            }

            /**
             * get item row on click
             *
             */
            mLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    _views = new ArrayList<View>();
                    boolean isChecked = mCheckBox.isChecked();

                    mCheckBox.setChecked(!isChecked);

                    if (isChecked) {
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                        _selectedClients.remove(c);
                    } else {
                        v.setBackgroundColor(Color.parseColor("#00B2EE"));
                        _selectedClients.add(c);
                    }
                }
            });
        }
    }

    public ArrayList<BEClient> getSelectedClients() {
        return _selectedClients;
    }

    public void setSelectedClients(ArrayList<BEClient> li) {
        _selectedClients = li;
    }
}
