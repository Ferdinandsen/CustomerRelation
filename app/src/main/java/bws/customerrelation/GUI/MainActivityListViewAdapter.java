package bws.customerrelation.GUI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

/**
 * Created by Jaje on 20-Aug-15.
 */
public class MainActivityListViewAdapter extends ArrayAdapter<BEClient> {
    ArrayList<View> _views;
    ArrayList<Integer> _selectedItems;
    ArrayList<BEClient> _clients;
    BEClient _selectedClient;

    public MainActivityListViewAdapter(Context mainActivity, int resource, ArrayList<BEClient> objects, ArrayList<Integer> selectedItems) {
        super(mainActivity, resource, objects);
        _clients = objects;
        _views = new ArrayList<View>();
        _selectedItems = selectedItems;
    }

    public MainActivityListViewAdapter(Context clientActivity, int resource, ArrayList<BEClient> list, BEClient selectedClient) {
        super(clientActivity, resource, list);
        _clients = list;
        _views = new ArrayList<View>();
        _selectedClient = selectedClient;
    }


//    @Override
//    public View getView(int position, View v, ViewGroup parent) {
//        if (v == null) {
//            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
//                    Context.LAYOUT_INFLATER_SERVICE);
//            v = li.inflate(R.layout.cell_main_activity, null);
//        } else {
//            int i = position - 1;
//            if (position == 0)
//                i = 0;
//            v = _views.get(i);
//        }
//        v.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        BEClient c = _clients.get(position);
//
//        TextView company = (TextView) v.findViewById(R.id.companyName);
//        TextView canvasAmount = (TextView) v.findViewById(R.id.canvasAmount);
//
//        if (company != null) {
//            company.setText(c.getFirstName());
//        }
//        if (company != null) {
//            canvasAmount.setText("" + c.getId());
//        }
//        for (int x : _selectedItems)
//            if (position == x) {
//                v.setBackgroundColor(Color.parseColor("#00B2EE"));
//            }
//
//        if (_views.size() <= position) {
//            _views.add(v);
//        } else {
//            _views.set(position, v);
//        }
//        return v;
}
