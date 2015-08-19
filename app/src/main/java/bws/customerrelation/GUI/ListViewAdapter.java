package bws.customerrelation.GUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

/**
 * Created by Jaje on 19-Aug-15.
 */
public class ListViewAdapter {
    Context _context;

    public ArrayAdapter<?> createNewAdapter(Context activity, ArrayList<?> list) {
        _context = activity;
        return new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                list);
    }
}