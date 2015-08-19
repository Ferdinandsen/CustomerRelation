package bws.customerrelation.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BEClient;
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
    BEClient selectedClient;
    Button btnCreateCanvas;
    ListViewAdapter adapter;
    ArrayList<BECanvas> clientCanvaslist;
    ClientController _clientController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);
        Bundle b = getIntent().getExtras();
        _clientController = new ClientController(this);
        adapter = new ListViewAdapter();
        selectedClient = (BEClient) b.getSerializable(SharedConstants.CLIENT);
        findViews();
        populateData();
        setListeners();
        if (!clientCanvaslist.isEmpty())
            canvasList.setAdapter(adapter.createNewAdapter(this, clientCanvaslist));
    }

    private void populateData() {
        firstName.setText("First Name: " + selectedClient.getFirstName());
        lastName.setText("Last Name: " + selectedClient.getLastName());
        company.setText("Company Name: " + selectedClient.getCompany());
        telephone.setText("Phone Number: " + selectedClient.getPhoneNumber());
        clientCanvaslist = _clientController.getAllCanvasByClientId(selectedClient);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
        canvasList.setAdapter(adapter.createNewAdapter(this, clientCanvaslist));
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

    }

    private void onclickBtnCreateCanvas() {
        Intent canvasIntent = new Intent();
        canvasIntent.setClass(this, CanvasActivity.class);
        canvasIntent.putExtra(SharedConstants.CLIENT, selectedClient);
        startActivity(canvasIntent);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_client_data, menu);
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
