package bws.customerrelation.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class MainActivity extends AppCompatActivity {
    TextView _txtUserData;
    ImageView _bwsNet;
    Button _btnDownloadList;
    LinearLayout _linearlayoutListView;
    BEUser _user;
    ArrayList<BEClient> _allClients;
    ArrayList<BEClient> _selectedClients;
    ClientController _clientController;
    UserController _userController;
    private static String TAG = "MainActivity";
    Bundle _tempSavedInstanceState;
    InflateAdapter _adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _tempSavedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        _user = (BEUser) b.getSerializable(SharedConstants.USER);
        _userController = new UserController(this);
        _clientController = new ClientController(this);
        findViews();
        setListeners();
        setUserData();
        populateClientList();

        if (savedInstanceState == null) {
            _adapter = new InflateAdapter(this, _allClients, _linearlayoutListView);
            _adapter.inflateView();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        ArrayList<BEClient> _selectedClients = _adapter.getSelectedClients();
        if (_selectedClients != null) {
            savedInstanceState.putSerializable(SharedConstants.SELECTEDCLIENTLIST, _selectedClients);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _adapter = new InflateAdapter(this, _allClients, _linearlayoutListView);
        _adapter.inflateView();
        /**
         * TEST FOR NULL ???
         * TODO
         */

        ArrayList<BEClient> cl = (ArrayList<BEClient>) savedInstanceState.getSerializable((SharedConstants.SELECTEDCLIENTLIST));
        if (!cl.isEmpty()) {
            _adapter.setSelectedClients(cl);
        }else{
            _selectedClients = cl;
        }

    }

    private void findViews() {
        _txtUserData = (TextView) findViewById(R.id.userData);
        _bwsNet = (ImageView) findViewById(R.id.bwsNet);
        _bwsNet.setImageResource(R.mipmap.ic_launcher);
        _btnDownloadList = (Button) findViewById(R.id.btnDownloadList);
        _linearlayoutListView = (LinearLayout) findViewById(R.id.linear_listview);
    }

    private void populateClientList() {
        _allClients = _clientController.getAllClients();
    }

    private void setUserData() {
        _txtUserData.setText("Logged in as: " + _user.getFirstname() + " " + _user.getLastname());
    }

    private void setListeners() {
//
        _btnDownloadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClickDownloadList();
            }
        });
    }

    //SELECT OR DESELECT ITEMS ON LIST
    private void onClientListItemClick(int position, View view) {

    }

    //DOWNLOAD THE SELECTED ITEMS TO DB
//    private void onClickDownloadList() {
//        //DU SKAL vælge kunder for at kunne DL
//        if (selectedItems.isEmpty()) {
//            Toast.makeText(this, "Du har ikke valgt nogle kunder", Toast.LENGTH_SHORT).show();
//        } else {
//            //Tilføjer kunder til liste for at sende til device
//            ArrayList<BEClient> tempClientList = new ArrayList<BEClient>();
//            for (int x : selectedItems) {
////                tempClientList.add((BEClient) clientListView.getItemAtPosition(x));
//            }
//            //HVIS der kun er en gå direkte til ClientDataActivity
//            if (tempClientList.size() == 1) {
//                Intent showClientIntent = new Intent();
//                showClientIntent.setClass(this, ClientDataActivity.class);
//                _clientController.createClientList(tempClientList);
//                showClientIntent.putExtra(SharedConstants.CLIENT, tempClientList.get(0));
//                startActivity(showClientIntent);
//                //Send listen med flere items til ClientActivity
//            } else {
//                Intent clientIntent = new Intent();
//                clientIntent.setClass(this, ClientActivity.class);
//                _clientController.createClientList(tempClientList);
//                startActivity(clientIntent);
//            }
//        }
//    }


}

