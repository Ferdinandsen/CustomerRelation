package bws.customerrelation.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class LoginActivity extends Activity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    BEUser _user;
    private static String TAG = "LoginActivity";

    UserController _userController;
    ClientController _clientController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _userController = new UserController(this);
        _clientController = new ClientController(this);
        findViews();
        setListeners();
        testSetup();

    }

    private void testSetup() {
        _userController.createDummyUsers();
        Log.v(TAG, "TEST - Users: " + _userController.getAllUsers().size());
        _clientController.createDummyClients();
        Log.v(TAG, "TEST - Clients: " + _clientController.getAllClients().size());
    }

    private void findViews() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtLoginUsername);
        txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
        txtPassword.setText("");
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
        //PAS!
    }

    private void checkCredentials() {
        String email = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString();
        if (userLogin(email, password)) {

            Intent mainActivity = new Intent();
            mainActivity.setClass(this, MainActivity.class);
            mainActivity.putExtra(SharedConstants.USER, _user);
            startActivity(mainActivity);
        }
    }

    private boolean userLogin(String email, String password) {
        _user = _userController.getUserByCredentials(email, password);
        if (_user != null) {
            return true;
        }
        Toast.makeText(this, "Login failed! Please try again...", Toast.LENGTH_LONG).show();
        txtPassword.setText("");
        return false;
    }

}
