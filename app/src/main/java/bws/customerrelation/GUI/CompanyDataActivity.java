package bws.customerrelation.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    Button btnShowCanvas;
    InflateCompanyCanvasData _adapter;
    CanvasController _canvasController;
    ArrayList<BECanvas> companyCanvaslist;
    BECompany _selectedCompany;
    private static String TAG = "CompanyDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_data);
        _canvasController = CanvasController.getInstance(this);
        _selectedCompany = CompanyActivity.SELECTEDCOMPANY != null ? CompanyActivity.SELECTEDCOMPANY : MainActivity.SELECTEDCOMPANIES.get(0);
        findViews();
        populateData();
        setListeners();
        inflateViews();
        SELECTEDCANVAS = _adapter.getSelectedCanvas() != null ? _adapter.getSelectedCanvas() : null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        companyCanvaslist = _canvasController.getAllCanvasByClientId(_selectedCompany);
        _LinearLayout.removeAllViews();
        inflateViews();
    }

    private void inflateViews() {
        _adapter = new InflateCompanyCanvasData(this, companyCanvaslist, _LinearLayout);
        _adapter.inflateView();
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
        btnShowCanvas = (Button) findViewById(R.id.btnShowCanvas);
    }

    private void setListeners() {
        btnCreateCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickBtnCreateCanvas();
            }
        });
        btnShowCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCanvas();
            }
        });
    }

    private void openCanvas() {
        if (SELECTEDCANVAS == null) {
            Toast.makeText(this, "Du har ikke valgt et canvas", Toast.LENGTH_LONG).show();
        } else {
            Intent showCanvasIntent = new Intent();
            showCanvasIntent.setClass(this, ShowCanvasActivity.class);
            startActivity(showCanvasIntent);
        }
    }

    private void onclickBtnCreateCanvas() {
        Intent canvasIntent = new Intent();
        canvasIntent.setClass(this, CreateCanvasActivity.class);
        canvasIntent.putExtra(SharedConstants.CLIENT, _selectedCompany);//TODO remove?
        startActivity(canvasIntent);
    }

}
