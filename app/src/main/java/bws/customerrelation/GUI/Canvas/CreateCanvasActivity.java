package bws.customerrelation.GUI.Canvas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.SettingsController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.GUI.LoginActivity;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BECountry;
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
    Button spinFollowUpDate;
    Spinner spinOffice;
    SettingsController _settingController;
    private int year, month, day;
    private Calendar calendar;
    ArrayList<BECountry> _countryList;

    private static String TAG = "CreateCanvasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_canvas);
        Bundle b = getIntent().getExtras(); //Todo remove?
        _canvasController = CanvasController.getInstance(this);
        _settingController = SettingsController.getInstance(this);
        findViews();
        setListeners();
        createAndSetAdapters();
        populateData(b);
    }

    private void createAndSetAdapters() {
        _countryList = _settingController.populateCountryList();
        spinCountry.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, _countryList));
        spinToActivity.setAdapter(createAdapter(SharedConstants.ACTIVITY));
        spinToTRP.setAdapter(createAdapter(SharedConstants.TRANSPORTTYPE));
        spinToVisit.setAdapter(createAdapter(SharedConstants.VISITTYPE));
        spinBusinessArea.setAdapter(createAdapter(SharedConstants.BUSINESSAREA));
        spinOffice.setAdapter(createAdapter(SharedConstants.OFFICE));
    }

    private void populateData(Bundle b) {
        //Todo Get from static instead
        _selectedCompany = (BECompany) b.getSerializable(SharedConstants.CLIENT);
        _selectedUser = LoginActivity.USER;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(year, month + 1, day);
    }

    private ArrayAdapter createAdapter(String s) {
        return new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, _settingController.populateSettingsLists(s));
    }

    private void setListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnSave(_selectedCompany);
            }
        });
        spinFollowUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void openDatePicker() {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            setDate(arg1, arg2 + 1, arg3);
        }
    };

    private void setDate(int year, int month, int day) {
        String test = "" + day + "-" + month + "-" + year;
        spinFollowUpDate.setText(test);
    }

    private void onClickBtnSave(BECompany comp) {
        String followUpDate, subject, activityType, visitType, transportType, businessArea, country, region, text, user, office;
        BECountry beCountry;
        String canvasId = "" + (Math.random() * 1000 + Math.random() * 1000);

//        Date date = new Date();
//        String res;
//        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
//        res = sdfOut.format(date);

        followUpDate = spinFollowUpDate.getText().toString();
        subject = txtSubject.getText().toString();
        activityType = spinToActivity.getSelectedItem().toString();
        visitType = spinToVisit.getSelectedItem().toString();
        transportType = spinToTRP.getSelectedItem().toString();
        businessArea = spinBusinessArea.getSelectedItem().toString();
        office = spinOffice.getSelectedItem().toString();
        text = txtCanvas.getText().toString();
        user = _selectedUser.getFirstname() + " " + _selectedUser.getLastname();
        beCountry = (BECountry) spinCountry.getSelectedItem();
        country = beCountry.get_name();
        region = beCountry.get_region();

        Date res = new Date();
        SimpleDateFormat sdfout = new SimpleDateFormat("dd-MM-yyyy");
        String today = sdfout.format(res);
        BECanvas canvas = new BECanvas(canvasId, comp.getM_companyId(), subject, user, visitType,
                today,followUpDate,"null", "null", "null", region, country,
                transportType, activityType, businessArea, office, text);
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
        spinFollowUpDate = (Button) findViewById(R.id.followUpSpinner);
        spinToVisit = (Spinner) findViewById(R.id.TypeOfVisitSpinner);
        spinOffice = (Spinner) findViewById(R.id.officeSpinner);
    }

}
