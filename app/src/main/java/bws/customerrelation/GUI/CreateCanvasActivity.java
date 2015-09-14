package bws.customerrelation.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class CreateCanvasActivity extends AppCompatActivity {
    Button btnSave;
    EditText txtCanvas;
    EditText txtSubject;
    CanvasController _canvasController;
    BECompany _selectedCompany;
    BEUser _selectedUser;
    Spinner spinToTRP;
    Spinner spinToVisit;
    Spinner spinToActivity;
    Spinner spinBusinessArea;
    Spinner spinCountry;
    Spinner spinFollowUpDate;

    private static String TAG = "CreateCanvasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_canvas);
        Bundle b = getIntent().getExtras(); //Todo remove?
        _canvasController = CanvasController.getInstance(this);
        findViews();
        setListeners();
        populateData(b);
    }

    private void populateData(Bundle b) {
        //Todo Get from static instead
        _selectedCompany = (BECompany) b.getSerializable(SharedConstants.CLIENT);
        _selectedUser = LoginActivity.USER;
        String[] stringaraa = new String[]{"#", "hejsa", "DU TYYYYYYYYYK"};
//        ArrayAdapter<String> typeOfTransport = new ArrayAdapter<String>(this,R.layout.simple_list_item_1,stringaraa);
        //TODO
//        spinToTRP.setAdapter(typeOfTransport);
    }

    private void setListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnSave(_selectedCompany);
            }
        });
    }

    private void onClickBtnSave(BECompany comp) {
        String canvasId = "" + (Math.random() * 1000 + Math.random() * 1000);

        Date date = new Date();
        String res;
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
        res = sdfOut.format(date);

        BECanvas canvas = new BECanvas(canvasId, comp.getM_companyId(), txtSubject.getText().toString(), _selectedUser.getFirstname() + " " + _selectedUser.getLastname(), res, txtCanvas.getText().toString());
        if (_canvasController.saveCanvas(canvas) != -1) {
            finish();
        }

    }

    private void findViews() {
        btnSave = (Button) findViewById(R.id.btnSave);
        txtCanvas = (EditText) findViewById(R.id.newCanvas);
        txtSubject = (EditText) findViewById(R.id.txtSubject);
        spinToTRP = (Spinner) findViewById(R.id.typeOfTransportSpinner);
        spinCountry = (Spinner) findViewById(R.id.countrySpinner);
        spinBusinessArea = (Spinner) findViewById(R.id.businessAreaSpinner);
        spinToActivity = (Spinner) findViewById(R.id.activitySpinner);
        spinFollowUpDate = (Spinner) findViewById(R.id.followUpSpinner);
        spinToVisit = (Spinner) findViewById(R.id.TypeOfVisitSpinner);
    }

}
