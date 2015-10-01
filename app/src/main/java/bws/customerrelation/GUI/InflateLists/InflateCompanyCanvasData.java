package bws.customerrelation.GUI.InflateLists;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.GUI.Company.CompanyDataActivity;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateCompanyCanvasData {

    /**
     * Created by Jaje on 20-Aug-15.
     */
    LinearLayout mLinearListLayout;
    ArrayList<BECanvas> _allCanvas;
    static BECanvas _SELECTEDCANVAS;
    Context _context;
    View oldView;
    private static String TAG = "InflateCompanyCanvasData";

    public InflateCompanyCanvasData(Context context, ArrayList<BECanvas> list, LinearLayout layout) {
        _allCanvas = list;
        _context = context;
        mLinearListLayout = layout;
    }

    public void inflateView() {
        int pos = 0;
        for (final BECanvas c : _allCanvas) {
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater.inflate(R.layout.cell_canvas_data, null);
            mLinearView.setTag(pos);
            /**
             * getting id of row.xml
             */

            TextView desc = (TextView) mLinearView
                    .findViewById(R.id.companyName);
            TextView date = (TextView) mLinearView.findViewById(R.id.date);
            final CheckBox mCheckBox = (CheckBox) mLinearView
                    .findViewById(R.id.checkbox);
            mCheckBox.setVisibility(View.INVISIBLE);
            /**
             * set item into row
             */
            final String showDate = c.getM_date();
            final String typeVisit = c.getM_TypeOfVisit();
            final String subject = c.getM_Subject();
            if (subject == null || subject.equals("")) {
                desc.setText(typeVisit);
            } else {
                desc.setText(subject);
            }
            date.setText(showDate);

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
                        setColorOnView(v);
                        _SELECTEDCANVAS = null;
                        oldView = null;
                    } else {
                        if (oldView != null) {
                            v.setBackgroundColor(Color.parseColor("#00B2EE")); // blååååå
                            ((CheckBox) oldView.findViewById(R.id.checkbox)).setChecked(false);
                            _SELECTEDCANVAS = c;
                            setColorOnView(oldView);
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

    public BECanvas getSelectedCanvas() {
        return _SELECTEDCANVAS;
    }
}

