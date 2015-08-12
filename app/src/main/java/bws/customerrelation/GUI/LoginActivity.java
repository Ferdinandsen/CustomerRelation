package bws.customerrelation.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    BEUser _loggedUser;
    UserController _userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setListeners();
        createDummyData();
//        autoLogin();
        _userController = new UserController(this);
    }

    private void findViews() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtLoginUsername);
        txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
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
        // GEM User som _loggedUser
        checkCredentials();
    }

    private void checkNetwork() {
        //PAS!
    }

    private void checkCredentials() {
        String email = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if (userLogin(email, password)) {
            Intent mainActivity = new Intent();
            mainActivity.putExtra(SharedConstants.LOGGED_USER, _loggedUser);
            mainActivity.setClass(this, MainActivity.class);
            startActivity(mainActivity);
        }
    }

    private boolean userLogin(String email, String password) {
        if ((_loggedUser = _userController.getUserByCredentials(email, password)) != null) {
            return true;
        }
        Toast.makeText(this, "Login failed! Please try again...", Toast.LENGTH_LONG).show();
        txtPassword.setText("");
        return false;
    }

    private void createDummyData() {
        _userController.createDummyUsers();
    }

    //    private void autoLogin() {
//        BEUser user = getLatestUser();
//        if (user != null) {
//            txtEmail.setText(user.getEmail());
//            txtPassword.setText(user.getPassword());
//            onClickBtnLogin();
//        }
//    }

}
