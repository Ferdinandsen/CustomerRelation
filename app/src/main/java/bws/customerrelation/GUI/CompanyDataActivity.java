package bws.customerrelation.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class CompanyDataActivity extends AppCompatActivity {

    TextView companyName;
    TextView address;
    TextView zipcode_city;
    TextView businessRelation;
    TextView group;
    TextView telephone;
    TextView seNumber;

    LinearLayout _LinearLayout;
    static BECanvas SELECTEDCANVAS;
    Button btnCreateCanvas;
    InflateClientData _adapter;
    CanvasController _canvasController;
    ArrayList<BECanvas> companyCanvaslist;
    BECompany _selectedCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);
        _canvasController = new CanvasController(this);

        _selectedCompany = CompanyActivity.SELECTEDCOMPANY != null ? CompanyActivity.SELECTEDCOMPANY : MainActivity._SELECTEDCLIENTS.get(0);
        findViews();
        populateData();
        setListeners();
        _adapter = new InflateClientData(this, companyCanvaslist, _LinearLayout);
        _adapter.inflateView();
        if (_adapter.getSelectedCanvas() != null) {
            SELECTEDCANVAS = _adapter.getSelectedCanvas();
        }
    }

    private void populateData() {
        companyName.setText("Company name:      " + _selectedCompany.getM_companyName());
        address.setText("Address:           " + _selectedCompany.getM_address());
        zipcode_city.setText("Zip code & city:   " + _selectedCompany.getM_zipCode() + " " + _selectedCompany.getM_city());
        businessRelation.setText("business relation: " + _selectedCompany.getM_businessRelation());
        group.setText("Group:             " + _selectedCompany.getM_group());
        telephone.setText("Phone Number:      " + _selectedCompany.getM_telephone());
        seNumber.setText("SE Number:         " + _selectedCompany.getM_seNo());

        companyCanvaslist = _canvasController.getAllCanvasByClientId(_selectedCompany);
    String test = "";
    }

    private void findViews() {
        companyName = (TextView) findViewById(R.id.companyName);
        address = (TextView) findViewById(R.id.address);
        telephone = (TextView) findViewById(R.id.telephone);
        businessRelation = (TextView) findViewById(R.id.businessRelation);
        seNumber = (TextView) findViewById(R.id.seNumber);
        zipcode_city = (TextView) findViewById(R.id.zipcode_city);
        group = (TextView) findViewById(R.id.group);
        btnCreateCanvas = (Button) findViewById(R.id.btnCreateCanvas);
        _LinearLayout = (LinearLayout) findViewById(R.id.linear_listview);
    }

    private void setListeners() {
        btnCreateCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickBtnCreateCanvas();
            }
        });
    }

    private void onClickSelectedCanvas() {
        //TODO
    }

    private void onclickBtnCreateCanvas() {
        Intent canvasIntent = new Intent();
        canvasIntent.setClass(this, CanvasActivity.class);
        canvasIntent.putExtra(SharedConstants.CLIENT, _selectedCompany);
        startActivity(canvasIntent);
    }
}
