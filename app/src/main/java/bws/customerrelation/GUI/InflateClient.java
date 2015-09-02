package bws.customerrelation.GUI;

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
public class InflateClient {
    LinearLayout mLinearListLayout;
    ArrayList<BECompany> _allClients;
    static BECompany _SELECTEDCOMPANY;
    Context _context;
    View oldView;

    public InflateClient(Context context, ArrayList<BECompany> list, LinearLayout layout) {
        _allClients = list;
        _context = context;
        mLinearListLayout = layout;
    }

    public void inflateView() {
        for (final BECompany c : _allClients) {
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
            final CheckBox mCheckBox = (CheckBox) mLinearView
                    .findViewById(R.id.checkbox);
            mCheckBox.setVisibility(View.INVISIBLE);
            /**
             * set item into row
             */

            final String fName = c.getM_companyName();
            mFirstName.setText(fName);

            /**
             * changes background to white
             */

            mLinearListLayout.setBackgroundColor(Color.parseColor("#ffffff"));

            /**
             * add oldView in top linear
             */

            mLinearListLayout.addView(mLinearView);

            /**
             * IF the client is in_selectedClients, highlight it again
             */

            if (_SELECTEDCOMPANY != null) {
                if (_SELECTEDCOMPANY.getM_companyId().equals(c.getM_companyId()))
                    mLinearView.setBackgroundColor(Color.parseColor("#00B2EE")); //blåååååå
            }

            /**
             * get item row on click
             */
            mLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean isChecked = mCheckBox.isChecked();

                    mCheckBox.setChecked(!isChecked);

                    if (_SELECTEDCOMPANY != null && isChecked) {
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                        _SELECTEDCOMPANY = null;
                        oldView = null;
                    } else {
                        if (oldView != null) {
                            v.setBackgroundColor(Color.parseColor("#00B2EE"));
                            ((CheckBox) oldView.findViewById(R.id.checkbox)).setChecked(false);
                            _SELECTEDCOMPANY = c;
                            oldView.setBackgroundColor(Color.parseColor("#ffffff")); // hvid
                            oldView = v;
                        } else {
                            v.setBackgroundColor(Color.parseColor("#00B2EE")); // blååååå
                            oldView = v;
                            _SELECTEDCOMPANY = c;
                        }
                    }
                    ClientActivity.SELECTEDCOMPANY = _SELECTEDCOMPANY;
                }
            });
        }
    }

    public BECompany getSelectedClient() {
        return _SELECTEDCOMPANY;
    }
}
