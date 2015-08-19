package bws.customerrelation.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
    ListView clientListView;
    Button btnDownloadList;
    BEUser _user;
    ArrayList<BEClient> allClients;
    ClientController _clientController;
    UserController _userController;
    ArrayList<Integer> selectedItems;
    private static String TAG = "MainActivity";
    ListViewAdapter adapter;
    Bundle tempSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempSavedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        adapter = new ListViewAdapter();
        _user = (BEUser) b.getSerializable(SharedConstants.USER);
        _userController = new UserController(this);
        _clientController = new ClientController(this);
        selectedItems = new ArrayList<Integer>();
        findViews();
        setListeners();
        setUserData();
        populateClientList();
        clientListView.setAdapter(adapter.createNewAdapter(this, allClients));
        clientListView.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(SharedConstants.STATE, selectedItems);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void populateClientList() {
        allClients = _clientController.getAllClients();
    }

    private void setUserData() {
        txtUserData.setText("Logged in as: " + _user.getFirstname() + " " + _user.getLastname());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedItems = (ArrayList<Integer>) savedInstanceState.getSerializable(SharedConstants.STATE);
       //FIX DETTE farve på baggrund af de gamle selectede

    }




    private void setListeners() {
        clientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClientListItemClick(position, view);
            }
        });
        btnDownloadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDownloadList();
            }
        });
    }

    //SELECT OR DESELECT ITEMS ON LIST
    private void onClientListItemClick(int position, View view) {
        Object pos = position; // for at vi kan fjerne den korrekte
        if (!selectedItems.contains(position)) {
            selectedItems.add(position);
            view.setBackgroundColor(Color.parseColor("#00B2EE"));
        } else {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            selectedItems.remove(pos);
        }
    }

    //DOWNLOAD THE SELECTED ITEMS TO DB
    private void onClickDownloadList() {
        //DU SKAL vælge kunder for at kunne DL
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "Du har ikke valgt nogle kunder", Toast.LENGTH_SHORT).show();
        } else {
            //Tilføjer kunder til liste for at sende til device
            ArrayList<BEClient> tempClientList = new ArrayList<BEClient>();
            for (int x : selectedItems) {
                tempClientList.add((BEClient) clientListView.getItemAtPosition(x));
            }
            //HVIS der kun er en gå direkte til ClientDataActivity
            if (tempClientList.size() == 1) {
                Intent showClientIntent = new Intent();
                showClientIntent.setClass(this, ClientDataActivity.class);
                _clientController.createClientList(tempClientList);
                showClientIntent.putExtra(SharedConstants.CLIENT, tempClientList.get(0));
                startActivity(showClientIntent);
                //Send listen med flere items til ClientActivity
            } else {
                Intent clientIntent = new Intent();
                clientIntent.setClass(this, ClientActivity.class);
                _clientController.createClientList(tempClientList);
//                clientIntent.putExtra(SharedConstants.CLIENTLIST, tempClientList);
                startActivity(clientIntent);
            }
        }
    }

    private void findViews() {
        txtUserData = (TextView) findViewById(R.id.userData);
        bwsNet = (ImageView) findViewById(R.id.bwsNet);
        bwsNet.setImageResource(R.mipmap.ic_launcher);
        clientListView = (ListView) findViewById(R.id.clientListView);
        btnDownloadList = (Button) findViewById(R.id.btnDownloadList);
    }


//    private ArrayAdapter<BEClient> createNewAdapter() {
//        return new ArrayAdapter<BEClient>(
//                this,
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                allClients);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
