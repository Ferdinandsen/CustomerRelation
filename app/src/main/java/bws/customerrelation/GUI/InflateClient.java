package bws.customerrelation.GUI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateClient {
    LinearLayout mLinearListView;
    ArrayList<BEClient> _allClients;
    BEClient _selectedClient;
    Context _context;
    ArrayList<BEClient> _selectedClients;
    View test;


    public InflateClient(Context context, ArrayList<BEClient> list, LinearLayout layout) {
        _selectedClients = new ArrayList<BEClient>();
        _selectedClient = null;
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
             */
            mLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean isChecked = mCheckBox.isChecked();

                    mCheckBox.setChecked(!isChecked);

                    if (_selectedClient != null && isChecked) {
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                        _selectedClient = null;
                    } else {
                        if (test != null) {
                            v.setBackgroundColor(Color.parseColor("#00B2EE"));
                            ((CheckBox) test.findViewById(R.id.checkbox)).setChecked(false);
                            _selectedClient = c;
                            test.setBackgroundColor(Color.parseColor("#ffffff"));
                            test = v;
                        } else {
                            v.setBackgroundColor(Color.parseColor("#00B2EE"));
                            test = v;
                        }
                    }
                }
            });
        }
    }

    public BEClient getSelectedClient() {
        return _selectedClient;
    }

    public void setSelectedClient(BEClient cli) {
        _selectedClient = cli;
        _selectedClients.add(cli);
    }
}
