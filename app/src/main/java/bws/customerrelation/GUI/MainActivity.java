package bws.customerrelation.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class MainActivity extends AppCompatActivity {
    TextView _txtUserData;
    ImageView _bwsNet;
    Button _btnDownloadList;
    LinearLayout _linearlayoutListView;
    BEUser _user;
    ArrayList<BECompany> _allClients;
    static ArrayList<BECompany> _SELECTEDCLIENTS = new ArrayList<BECompany>();
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

        findViews();
        setListeners();
        setUserData();
        populateClientList();

        _adapter = new InflateClients(this, _allClients, _linearlayoutListView);
        _adapter.inflateView();
        if (_adapter.getSelectedClients() != null) {
            _SELECTEDCLIENTS = _adapter.getSelectedClients();
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

//        _allClients = _clientController.getCompanyFromApi();
    }

    private void setUserData() {
        _txtUserData.setText("Logged in as: " + _user.getFirstname() + " " + _user.getLastname());
    }

    private void setListeners() {
        _btnDownloadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDownloadList();
            }
        });
    }

    //DOWNLOAD THE SELECTED ITEMS TO DB
    private void onClickDownloadList() {
        if (_SELECTEDCLIENTS.isEmpty()) {
            Toast.makeText(this, "Du har ikke valgt kunder", Toast.LENGTH_SHORT).show();
        } else {
            if (_SELECTEDCLIENTS.size() == 1) {
                Intent showClientIntent = new Intent();
                showClientIntent.setClass(this, ClientDataActivity.class);
                _clientController.createClientList(_SELECTEDCLIENTS);
                showClientIntent.putExtra(SharedConstants.CLIENT, _SELECTEDCLIENTS.get(0));
                startActivity(showClientIntent);
            } else {
                Intent clientIntent = new Intent();
                clientIntent.setClass(this, ClientActivity.class);
                clientIntent.putExtra(SharedConstants.SELECTEDCLIENTLIST, MainActivity._SELECTEDCLIENTS);
                _clientController.createClientList(_SELECTEDCLIENTS);
                startActivity(clientIntent);
            }
        }
    }
}

