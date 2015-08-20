package bws.customerrelation.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
    TextView txtUserData;
    ImageView bwsNet;
    Button btnDownloadList;
    BEUser _user;
    ArrayList<BEClient> allClients;
    ClientController _clientController;
    UserController _userController;
    private static String TAG = "MainActivity";
    Bundle tempSavedInstanceState;
    InflateAdapter adapter;
    LinearLayout linearlayoutListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempSavedInstanceState = savedInstanceState;
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
            adapter = new InflateAdapter(this, allClients, linearlayoutListView);
            adapter.inflateView();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter = new InflateAdapter(this, allClients, linearlayoutListView);
        adapter.inflateView();
    }

    private void findViews() {
        txtUserData = (TextView) findViewById(R.id.userData);
        bwsNet = (ImageView) findViewById(R.id.bwsNet);
        bwsNet.setImageResource(R.mipmap.ic_launcher);
        btnDownloadList = (Button) findViewById(R.id.btnDownloadList);
        linearlayoutListView = (LinearLayout) findViewById(R.id.linear_listview);
    }

    private void populateClientList() {
        allClients = _clientController.getAllClients();
    }

    private void setUserData() {
        txtUserData.setText("Logged in as: " + _user.getFirstname() + " " + _user.getLastname());
    }

    private void setListeners() {
//
        btnDownloadList.setOnClickListener(new View.OnClickListener() {
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

