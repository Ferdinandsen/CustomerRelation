package bws.customerrelation.GUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    Button btnCache;
    BEUser _user;
    ArrayList<BEClient> allClients;
    ArrayList<Integer> cliList;
    ClientController _clientController;
    UserController _userController;
    ArrayList<Integer> selectedItems;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        _user = (BEUser) b.getSerializable(SharedConstants.USER);
        _userController = new UserController(this);
        _clientController = new ClientController(this);
        cliList = new ArrayList<Integer>();
        selectedItems = new ArrayList<Integer>();
        findViews();
        setListeners();
        setUserData();
        setImageView();
        populateClientList();
        clientListView.setAdapter(adapter());
        clientListView.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void populateClientList() {
        allClients = _clientController.getAllClients();
    }

    private void setImageView() {
        bwsNet.setImageResource(R.mipmap.ic_launcher);
    }

    private void setUserData() {
        txtUserData.setText("Logged in as: " + _user.getFirstname() + " " + _user.getLastname());
    }

    private void setListeners() {
        clientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClientListItemClick(position, view);
            }
        });

        btnCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCache();
            }
        });

        btnDownloadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDownloadList();
            }
        });

    }

    private void onClientListItemClick(int position, View view) {
        if (!selectedItems.contains(position)) {
            selectedItems.add(position);
            view.setBackgroundColor(Color.parseColor("#00B2EE"));
        } else {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            selectedItems.remove(position);
        }

    }


    private void onClickCache() {
        //TODO

    }

    private void onClickDownloadList() {
        ArrayList<BEClient> _dlClientList = new ArrayList<BEClient>();
        for (int x : selectedItems) {
            _dlClientList.add((BEClient) clientListView.getItemAtPosition(x));
        }
        Intent clientIntent = new Intent();
        clientIntent.setClass(this, clientActivity.class);
        clientIntent.putExtra(SharedConstants.CLIENT, _dlClientList);

    }

    private void findViews() {
        txtUserData = (TextView) findViewById(R.id.userData);
        bwsNet = (ImageView) findViewById(R.id.bwsNet);
        clientListView = (ListView) findViewById(R.id.clientListView);
        btnDownloadList = (Button) findViewById(R.id.btnDownloadList);
        btnCache = (Button) findViewById(R.id.btnCache);

    }


    private ArrayAdapter<BEClient> adapter() {
        return new ArrayAdapter<BEClient>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                allClients);
    }
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
