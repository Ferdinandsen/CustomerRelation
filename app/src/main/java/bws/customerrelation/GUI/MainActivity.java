package bws.customerrelation.GUI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    InflateClients _adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        _user = (BEUser) b.getSerializable(SharedConstants.USER);
        _userController = new UserController(this);
        _clientController = new ClientController(this);
        _selectedClients = new ArrayList<>();

        findViews();
        setListeners();
        setUserData();
        populateClientList();

        if (savedInstanceState == null) {
            _adapter = new InflateClients(this, _allClients, _linearlayoutListView);
            _adapter.inflateView();
        } else {
            rotateScreenSaver();
        }
    }

    private void rotateScreenSaver() {
        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation == 1) {
            Toast.makeText(this, "hej landscape", Toast.LENGTH_SHORT).show();

            if (_selectedClients.isEmpty()) {
                _selectedClients = _adapter.getSelectedClients();
            }
            _adapter.setSelectedClients(_selectedClients);
//            instanceState.putSerializable(SharedConstants.SELECTEDCLIENTLIST, _selectedClients);

        } else {
            Toast.makeText(this, "hej portrait", Toast.LENGTH_SHORT).show();
            //TODO!!!!!!!!!!!!!!!!!!!
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (_selectedClients.isEmpty()) {
            _selectedClients = _clientController.getAllClientsFromDevice();
            _adapter.setSelectedClients(_selectedClients);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

//        if (_selectedClients.isEmpty()) {
//            _selectedClients = _adapter.getSelectedClients();
//        }
//        _adapter.setSelectedClients(_selectedClients);
//        savedInstanceState.putSerializable(SharedConstants.SELECTEDCLIENTLIST, _selectedClients);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _adapter = new InflateClients(this, _allClients, _linearlayoutListView);
        _selectedClients = (ArrayList<BEClient>) savedInstanceState.getSerializable((SharedConstants.SELECTEDCLIENTLIST));
        if (!_selectedClients.isEmpty()) {
            _adapter.setSelectedClients(_selectedClients);
        }
        _adapter.inflateView();
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
                onClickDownloadList();
            }
        });
    }


    //DOWNLOAD THE SELECTED ITEMS TO DB
    private void onClickDownloadList() {
        ArrayList<BEClient> selectedClients = _adapter.getSelectedClients();
        if (selectedClients.isEmpty()) {
            Toast.makeText(this, "Du har ikke valgt kunder", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Er dette irriterende ??
             */
            if (selectedClients.size() == 1) {
                Intent showClientIntent = new Intent();
                showClientIntent.setClass(this, ClientDataActivity.class);
                _clientController.createClientList(selectedClients);
                showClientIntent.putExtra(SharedConstants.CLIENT, selectedClients.get(0));
                startActivity(showClientIntent);
                //Send listen med flere items til ClientActivity
            } else {
                Intent clientIntent = new Intent();
                clientIntent.setClass(this, ClientActivity.class);
                clientIntent.putExtra(SharedConstants.SELECTEDCLIENTLIST, selectedClients);
                _clientController.createClientList(selectedClients);
                startActivity(clientIntent);
            }
        }
    }
}

