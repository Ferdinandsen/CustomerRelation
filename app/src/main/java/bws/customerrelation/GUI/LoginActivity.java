package bws.customerrelation.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.CompanyController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    static BEUser USER;
    private static String TAG = "LoginActivity";
    CompanyController _companyController;
    CanvasController _canvasController;
    UserController _userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _userController = new UserController(this);
        findViews();
        setListeners();
        testSetup();
        getInstances();
    }

    private void testSetup() {
        _userController.createDummyUsers();
    }

    private void findViews() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtLoginUsername);
        txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
        txtUsername.setText("a");
        txtPassword.setText("a");
    }

    private void setListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonLogin();
            }
        });
    }

    private void onClickButtonLogin() {
        //Check om personen er:
        //  - på BWS netværk
//        checkNetwork();
        //  - i databasen
        checkCredentials();
    }

    private void checkNetwork() {
        //Todo Andre har måske noget
    }

    private void checkCredentials() {
        String email = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString();
        if (userLogin(email, password)) {

            clearDBClientList(); //TODO KUN TIL TEST!
            Intent mainActivity = new Intent();
            mainActivity.setClass(this, MainActivity.class);
            mainActivity.putExtra(SharedConstants.USER, USER); //Todo remove?
            startActivity(mainActivity);
        }
    }

    private void clearDBClientList() {
        _companyController.deleteAllCompanies();
        _canvasController.deleteAllCanvas();
        _canvasController.deleteAllCanvasFromUpload();
    }

    private void getInstances() {
        _companyController = CompanyController.getInstance(this);
        _canvasController = CanvasController.getInstance(this);
    }

    private boolean userLogin(String email, String password) {
        USER = _userController.getUserByCredentials(email, password);
        if (USER != null) {
            return true;
        }
        Toast.makeText(this, "Login failed! Please try again...", Toast.LENGTH_LONG).show();
        txtPassword.setText("");
        return false;
    }

}
