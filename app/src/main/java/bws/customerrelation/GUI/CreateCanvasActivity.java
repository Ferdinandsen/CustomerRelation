package bws.customerrelation.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import bws.customerrelation.Controller.CompanyController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class CreateCanvasActivity extends AppCompatActivity {
    Button btnSave;
    EditText txtCanvas;
    CompanyController _companyController;
    BECompany selectedClient;
    private static String TAG = "CreateCanvasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_canvas);
        Bundle b = getIntent().getExtras(); //Todo remove?
        _companyController = CompanyController.getInstance(this);
        findViews();
        setListeners();
        populateData(b);
    }

    private void populateData(Bundle b) {
        //Todo Get from static instead
        selectedClient = (BECompany)b.getSerializable(SharedConstants.CLIENT);
    }

    private void setListeners() {
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickBtnSave();
//            }
//        });
    }

//    private void onClickBtnSave() {
//        BECanvas canvas = txtCanvas.getText().toString();
//    _canvasController.saveCanvas(canvas);
//        finish();
//
//    }

    private void findViews() {
        btnSave = (Button) findViewById(R.id.btnSave);
        txtCanvas = (EditText) findViewById(R.id.newCanvas);
    }

}
