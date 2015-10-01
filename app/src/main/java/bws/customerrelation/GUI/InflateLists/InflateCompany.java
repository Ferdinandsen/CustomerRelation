package bws.customerrelation.GUI.InflateLists;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.GUI.Company.CompanyActivity;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateCompany {
    LinearLayout mLinearListLayout;
    ArrayList<BECompany> _allClients;
    static BECompany _SELECTEDCOMPANY;
    Context _context;
    View oldView;
    private static String TAG = "InflateCompany";

    public InflateCompany(Context context, ArrayList<BECompany> list, LinearLayout layout) {
        _allClients = list;
        _context = context;
        mLinearListLayout = layout;
    }

    public void inflateView() {
        int pos = 0;
        for (final BECompany c : _allClients) {
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater.inflate(R.layout.cell_inflate_company, null);
            mLinearView.setTag(pos);
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

            setColorOnView(mLinearView);

            /**
             * add oldView in top linear
             */

            mLinearListLayout.addView(mLinearView);

            /**
             * IF the client is in_selectedClients, highlight it again
             */

            if (_SELECTEDCOMPANY != null) {
                if (_SELECTEDCOMPANY.getM_companyId().equals(c.getM_companyId())) {
                    mLinearView.setBackgroundColor(Color.parseColor("#00B2EE")); //blåååååå
                    oldView = mLinearView;
                    mCheckBox.setChecked(true);
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

                    if (_SELECTEDCOMPANY != null && isChecked && _SELECTEDCOMPANY.getM_companyId().equals(c.getM_companyId())) {
                        setColorOnView(v);
                        _SELECTEDCOMPANY = null;
                        oldView = null;
                    } else {
                        if (oldView != null) {
                            v.setBackgroundColor(Color.parseColor("#00B2EE")); // blååååå
                            _SELECTEDCOMPANY = c;
                            setColorOnView(oldView);
                            oldView = v;
                        } else {
                            v.setBackgroundColor(Color.parseColor("#00B2EE")); // blååååå
                            oldView = v;
                            _SELECTEDCOMPANY = c;
                        }
                    }
                    CompanyActivity.SELECTEDCOMPANY = _SELECTEDCOMPANY;
                }
            });
            pos++;
        }
    }

    private void setColorOnView(View mView) {
        int localpos = (Integer) mView.getTag();
        if (localpos % 2 == 0) {
            mView.setBackgroundColor(Color.parseColor("#B2E5FF"));
        } else {
            mView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    public BECompany getSelectedClient() {
        return _SELECTEDCOMPANY;
    }
}
