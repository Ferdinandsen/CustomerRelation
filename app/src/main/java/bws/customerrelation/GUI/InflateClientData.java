package bws.customerrelation.GUI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateClientData {

    /**
     * Created by Jaje on 20-Aug-15.
     */
    LinearLayout mLinearListLayout;
    ArrayList<BECanvas> _allCanvas;
    static BECanvas _SELECTEDCANVAS;
    Context _context;
    View oldView;

    public InflateClientData(Context context, ArrayList<BECanvas> list, LinearLayout layout) {
        _allCanvas = list;
        _context = context;
        mLinearListLayout = layout;
    }

    public void inflateView() {
        for (final BECanvas c : _allCanvas) {
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater.inflate(R.layout.rowtest, null);

            /**
             * getting id of row.xml
             */

            TextView desc = (TextView) mLinearView
                    .findViewById(R.id.companyName);
            final CheckBox mCheckBox = (CheckBox) mLinearView
                    .findViewById(R.id.checkbox);
            mCheckBox.setVisibility(View.INVISIBLE);
            /**
             * set item into row
             */

            final String typeVisit = c.getM_TypeOfVisit();
            final String subject = c.getM_Subject();
            if (subject == null || subject.equals("")) {
                desc.setText(typeVisit);
            } else {
                desc.setText(subject);
            }

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

            if (_SELECTEDCANVAS != null) {
                if (_SELECTEDCANVAS.getM_canvasId().equals(c.getM_canvasId())) {
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

                    if (_SELECTEDCANVAS != null && isChecked && _SELECTEDCANVAS.getM_canvasId().equals(c.getM_canvasId())) {
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                        _SELECTEDCANVAS = null;
                        oldView = null;
                    } else {
                        if (oldView != null) {
                            v.setBackgroundColor(Color.parseColor("#00B2EE"));
                            ((CheckBox) oldView.findViewById(R.id.checkbox)).setChecked(false);
                            _SELECTEDCANVAS = c;
                            oldView.setBackgroundColor(Color.parseColor("#ffffff")); // hvid
                            oldView = v;
                        } else {
                            v.setBackgroundColor(Color.parseColor("#00B2EE")); // blååååå
                            oldView = v;
                            _SELECTEDCANVAS = c;
                        }
                    }
                    CompanyDataActivity.SELECTEDCANVAS = _SELECTEDCANVAS;
                }
            });
        }
    }

    public BECanvas getSelectedCanvas() {
        return _SELECTEDCANVAS;
    }
}

