package bws.customerrelation.GUI;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class InflateAdapter extends AppCompatActivity {
    LinearLayout mLinearListView;
    ArrayList<BEClient> clients;
    Context _context;

    public InflateAdapter(Context context, ArrayList<BEClient> list, LinearLayout layout) {
        clients = list;
        _context = context;
        mLinearListView = layout;
    }

    public void inflateView() {
        for (int i = 0; i < clients.size(); i++) {
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
            TextView mLastName = (TextView) mLinearView
                    .findViewById(R.id.canvasAmount);
            CheckBox mCheckBox = (CheckBox) mLinearView
                    .findViewById(R.id.checkbox);
            /**
             * set item into row
             */
            final String fName = clients.get(i).getFirstName();
            final String lName = "" + clients.get(i).getId();
            mFirstName.setText(fName);
            mLastName.setText(lName);

            /**
             * add view in top linear
             */
            mLinearListView.setBackgroundColor(Color.parseColor("#ffffff"));
            mLinearListView.addView(mLinearView);

            /**
             * get item row on click
             *
             */
            mLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    boolean test = ((CheckBox) mLinearView.findViewById(R.id.checkbox)).isChecked();

                    CheckBox box = (CheckBox) v.findViewById(R.id.checkbox);
                    box.setChecked(!test);

                    if (test) {
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                    } else {
                        v.setBackgroundColor(Color.parseColor("#00B2EE"));
                    }
                }
            });
        }
    }
}
