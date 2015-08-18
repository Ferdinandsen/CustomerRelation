package bws.customerrelation.GUI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

public class ClientActivity extends AppCompatActivity {
    ListView showClientsListview;
    Button btnShowClient;
    ArrayList<BEClient> dlClients;
    BEClient selectedClient;
    View selectedView;
    ClientController _clientController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        _clientController = new ClientController(this);
        findViews();
        PopulateClients();
        setListeners();
        showClientsListview.setAdapter(adapter());
        showClientsListview.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void setListeners() {
        showClientsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClientListItemClick(position, view);
            }
        });
        btnShowClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnShowClient();
            }
        });

    }

    private void onClickBtnShowClient() {
        Intent clientDataIntent = new Intent();
        clientDataIntent.setClass(this, ClientDataActivity.class);
        clientDataIntent.putExtra(SharedConstants.CLIENT, selectedClient);
        startActivity(clientDataIntent);
    }


    private void onClientListItemClick(int position, View view) {
        selectedClient = dlClients.get(position);
        if(selectedView != null)
        selectedView.setBackgroundColor(Color.parseColor("#ffffff"));
        view.setBackgroundColor(Color.parseColor("#00B2EE"));
        selectedView = view;
    }


    private void PopulateClients() {
        dlClients = _clientController.getAllClientsFromDevice();
    }

    private void findViews() {
        btnShowClient = (Button) findViewById(R.id.btnShowClient);
        showClientsListview = (ListView) findViewById(R.id.showClientsListview);
    }

    private ArrayAdapter<BEClient> adapter() {
        return new ArrayAdapter<BEClient>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                dlClients);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_client, menu);
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
