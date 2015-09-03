
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

public class CompanyActivity extends AppCompatActivity {
    LinearLayout _linearLayout;
    Button btnShowClient;
    ArrayList<BECompany> _selectedClients;
    static BECompany SELECTEDCOMPANY;
    ClientController _clientController;
    InflateClient _adapter;
    final static String TAG = "CompanyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        setContentView(R.layout.activity_client);
        _clientController = new ClientController(this);
        findViews();
        PopulateClients();
        setListeners();
            _adapter = new InflateClient(this, _selectedClients, _linearLayout);
            _adapter.inflateView();
        if (_adapter.getSelectedClient() != null) {
            SELECTEDCOMPANY = _adapter.getSelectedClient();
        }

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
        clientDataIntent.setClass(this, CompanyDataActivity.class);
        if (SELECTEDCOMPANY != null) {
            clientDataIntent.putExtra(SharedConstants.CLIENT, SELECTEDCOMPANY);
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
}
