package bws.customerrelation.GUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class ClientDataActivityListViewAdapter extends ArrayAdapter<BECanvas> {
    ArrayList<View> _views;
    BECompany _selectedClient;
    ArrayList<BECanvas> _canvas;

    public ClientDataActivityListViewAdapter(Context activity, int resource, ArrayList<BECanvas> list, BECompany selectedClient) {
        super(activity, resource, list);
        _canvas = list;
        _views = new ArrayList<View>();
        _selectedClient = selectedClient;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.cell_main_activity, null);
        } else {
            int i = position - 1;
            if (position == 0)
                i = 0;
            v = _views.get(i);
        }

        BECanvas c = _canvas.get(position);


        TextView company = (TextView) v.findViewById(R.id.companyName);
        TextView canvasAmount = (TextView) v.findViewById(R.id.canvasAmount);

        if (company != null) {
            company.setText(c.getId());
        }
        if (company != null) {
            canvasAmount.setText("" + _selectedClient.getFirstName());
        }
//        for (int x : _canvas)
//            if (position == x) {
//                v.setBackgroundColor(Color.parseColor("#00B2EE"));
//            }

        if (_views.size() <= position) {
            _views.add(v);
        } else {
            _views.set(position, v);
        }
        return v;
    }
}
