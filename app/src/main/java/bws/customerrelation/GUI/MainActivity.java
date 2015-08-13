package bws.customerrelation.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class MainActivity extends Activity {

    TextView txtUserData;
    ImageView bwsNet;
    ListView clientListView;
    Button btnDownloadList;
    Button btnCache;
    BEUser _user;
    ArrayList<BEClient> clientList;
    ClientController _clientController;
    UserController _userController;

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
        setImageView();
        populateClientList();
        clientListView.setAdapter(adapter());
    }

    private void populateClientList() {
        clientList = _clientController.getAllClients();
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
                onClientListItemClick();
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

    private void onClientListItemClick() {
        //TODO

    }

    private void onClickCache() {
        //TODO

    }

    private void onClickDownloadList() {
        //TODO

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
                clientList);
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
