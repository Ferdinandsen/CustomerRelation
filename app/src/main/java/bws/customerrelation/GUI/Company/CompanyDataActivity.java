package bws.customerrelation.GUI.Company;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.CompanyController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.GUI.Canvas.CreateCanvasActivity;
import bws.customerrelation.GUI.Canvas.FragmentCanvasPagerAdapter;
import bws.customerrelation.GUI.Canvas.ShowCanvasActivity;
import bws.customerrelation.GUI.InflateLists.InflateCompanyCanvasData;
import bws.customerrelation.GUI.MainActivity;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class CompanyDataActivity extends AppCompatActivity {

    TextView companyName;
    TextView address;
    TextView zipcode_city;
    TextView businessRelation;
    TextView telephone;

    LinearLayout _LinearLayout;
    public static BECanvas SELECTEDCANVAS;
    Button btnCreateCanvas;
    Button btnShowCanvas;
    InflateCompanyCanvasData _adapter;
    CanvasController _canvasController;
    CompanyController _companyController;
    ArrayList<BECanvas> companyCanvaslist;
    BECompany _selectedCompany;
    private static String TAG = "CompanyDataActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_data);
        _canvasController = CanvasController.getInstance(this);
        _companyController = CompanyController.getInstance(this);
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
        companyName.setText(_selectedCompany.getM_companyName());
        address.setText(_selectedCompany.getM_address());
        zipcode_city.setText(_selectedCompany.getM_zipCode() + " " + _selectedCompany.getM_city());
        businessRelation.setText("Business relation: " + _selectedCompany.getM_businessRelation());
        telephone.setText("Phone Number: " + _selectedCompany.getM_telephone());

        companyCanvaslist = _canvasController.getAllCanvasByClientId(_selectedCompany);
    }

    private void findViews() {
        companyName = (TextView) findViewById(R.id.companyName);
        address = (TextView) findViewById(R.id.address);
        telephone = (TextView) findViewById(R.id.telephone);
        businessRelation = (TextView) findViewById(R.id.businessRelation);
        zipcode_city = (TextView) findViewById(R.id.zipcode_city);
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
            showCanvasIntent.setClass(this, FragmentCanvasPagerAdapter.class);
            startActivity(showCanvasIntent);
        }
    }

    private void onclickBtnCreateCanvas() {
        Intent canvasIntent = new Intent();
        canvasIntent.setClass(this, CreateCanvasActivity.class);
        canvasIntent.putExtra(SharedConstants.CLIENT, _selectedCompany);//TODO remove?
        startActivity(canvasIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_delete_by_id) {
            new AlertDialog.Builder(this)
                    .setTitle("Really remove " + "..." + "?")
                    .setMessage("Are you sure you want to remove this company from device?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final String cId = _selectedCompany.getM_companyId();
                            _companyController.deleteCompanyById(cId);
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
