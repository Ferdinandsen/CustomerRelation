package bws.customerrelation.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class ClientDataActivity extends AppCompatActivity {

    TextView firstName;
    TextView lastName;
    TextView address;
    TextView telephone;
    TextView company;
    TextView seNumber;
    TextView zipcode_city;

    ListView canvasList;
    BECompany selectedClient;
    Button btnCreateCanvas;
    MainActivityListViewAdapter adapter;
    ClientController _clientController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);
        Bundle b = getIntent().getExtras();
        _clientController = new ClientController(this);
        selectedClient = (BECompany) b.getSerializable(SharedConstants.CLIENT);
        findViews();
//        populateData();
        setListeners();
        if (savedInstanceState == null) {
            canvasList.setAdapter(adapter);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(SharedConstants.SELECTEDCLIENT, selectedClient);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedClient = (BECompany) savedInstanceState.getSerializable(SharedConstants.SELECTEDCLIENT);
        canvasList.setAdapter(adapter);
    }

//    private void populateData() {
//        firstName.setText("First Name: " + selectedClient.getFirstName());
//        lastName.setText("Last Name: " + selectedClient.getLastName());
//        company.setText("Company Name: " + selectedClient.getCompany());
//        telephone.setText("Phone Number: " + selectedClient.getPhoneNumber());
//        clientCanvaslist = _clientController.getAllCanvasByClientId(selectedClient);
//    }

    @Override
    public void onResume() {
        super.onResume();
//        populateData();
    }

    private void findViews() {
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        address = (TextView) findViewById(R.id.address);
        telephone = (TextView) findViewById(R.id.telephone);
        company = (TextView) findViewById(R.id.company);
        seNumber = (TextView) findViewById(R.id.seNumber);
        zipcode_city = (TextView) findViewById(R.id.zipcode_city);
        btnCreateCanvas = (Button) findViewById(R.id.btnCreateCanvas);
        canvasList = (ListView) findViewById(R.id.canvasList);
    }

    private void setListeners() {
        btnCreateCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickBtnCreateCanvas();
            }
        });
        canvasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickSelectedCanvas();
            }
        });

    }

    private  void onClickSelectedCanvas() {
        //TODO
    }

    private void onclickBtnCreateCanvas() {
        Intent canvasIntent = new Intent();
        canvasIntent.setClass(this, CanvasActivity.class);
        canvasIntent.putExtra(SharedConstants.CLIENT, selectedClient);
        startActivity(canvasIntent);
    }
}
