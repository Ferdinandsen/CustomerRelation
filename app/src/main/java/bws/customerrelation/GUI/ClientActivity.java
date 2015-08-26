
package bws.customerrelation.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class ClientActivity extends AppCompatActivity {
    LinearLayout _linearLayout;
    Button btnShowClient;
    ArrayList<BECompany> _selectedClients;
    BECompany _selectedClient;
    ClientController _clientController;
    InflateClient _adapter;
    final static String TAG = "ClientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        setContentView(R.layout.activity_client);
        _clientController = new ClientController(this);
        _selectedClients = (ArrayList<BECompany>) b.getSerializable(SharedConstants.SELECTEDCLIENTLIST);
        findViews();
        PopulateClients();
        setListeners();
        if (savedInstanceState == null) {
            _adapter = new InflateClient(this, _selectedClients, _linearLayout);
            _adapter.inflateView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        _selectedClient = _adapter.getSelectedClient();
        savedInstanceState.putSerializable(SharedConstants.CLIENT, _selectedClient);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _selectedClient = (BECompany) savedInstanceState.getSerializable(SharedConstants.CLIENT);
        _adapter = new InflateClient(this, _selectedClients, _linearLayout);
        _adapter.setSelectedClient(_selectedClient);
        _adapter.inflateView();
    }

    private void setListeners() {
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
        _selectedClient = _adapter.getSelectedClient();
        if (_selectedClient != null) {
            clientDataIntent.putExtra(SharedConstants.CLIENT, _selectedClient);
            startActivity(clientDataIntent);
        } else {
            Toast.makeText(this, "Du skal vælge en kunde på listen", Toast.LENGTH_SHORT).show();
        }
    }

    private void PopulateClients() {
        _selectedClients = _clientController.getAllClientsFromDevice();
    }

    private void findViews() {
        btnShowClient = (Button) findViewById(R.id.btnShowClient);
        _linearLayout = (LinearLayout) findViewById(R.id.linear_listview);
    }

//    private ArrayAdapter<BECompany> createNewAdapter() {
//        return new ArrayAdapter<BECompany>(
//                this,
//                android.R.layout.simple_list_item_1,
//                android.R.id.text1,
//                _INFLATECLIENTS);
//    }

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
