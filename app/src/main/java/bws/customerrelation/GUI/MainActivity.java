package bws.customerrelation.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.ClientController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class MainActivity extends AppCompatActivity {
    TextView _txtUserData;
    ImageView _bwsNet;
    Button _btnDownloadList;
    LinearLayout _linearlayoutListView;
    EditText _searchView;
    BEUser _user;

    ArrayList<BECompany> _allClients;
    ArrayList<BECompany> _searchList;
    static ArrayList<BECompany> _SELECTEDCLIENTS = new ArrayList<BECompany>();

    ClientController _clientController;
    UserController _userController;
    CanvasController _canvasController;

    private static String TAG = "MainActivity";
    InflateClients _adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        _user = (BEUser) b.getSerializable(SharedConstants.USER);
        _userController = new UserController(this);
        _clientController = new ClientController(this);
        _canvasController = new CanvasController(this);

        findViews();
        setListeners();
        setUserData();
        populateCompanyList();

        _adapter = new InflateClients(this, _allClients, _linearlayoutListView);
        _adapter.inflateView();

        if (_adapter.getSelectedClients() != null) {
            _SELECTEDCLIENTS = _adapter.getSelectedClients();
        }
    }

    private void findViews() {
        _txtUserData = (TextView) findViewById(R.id.userData);
        _bwsNet = (ImageView) findViewById(R.id.bwsNet);
        _bwsNet.setImageResource(R.mipmap.ic_launcher);
        _btnDownloadList = (Button) findViewById(R.id.btnDownloadList);
        _linearlayoutListView = (LinearLayout) findViewById(R.id.linear_listview);
    }

    private void populateCompanyList() {
        _allClients = _clientController.getCompanyFromApi();
    }

    private void setUserData() {
        _txtUserData.setText("Logged in as: " + _user.getFirstname() + " " + _user.getLastname());
    }

    private void setListeners() {
        _btnDownloadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDownloadList();
            }
        });

    }

    //DOWNLOAD THE SELECTED ITEMS TO DB
    private void onClickDownloadList() {
        if (_SELECTEDCLIENTS.isEmpty()) {
            Toast.makeText(this, "Du har ikke valgt kunder", Toast.LENGTH_SHORT).show();
        } else {
            if (_SELECTEDCLIENTS.size() == 1) {
                Intent showClientIntent = new Intent();
                showClientIntent.setClass(this, CompanyDataActivity.class);
                _clientController.createCompanyList(_SELECTEDCLIENTS);
                _canvasController.createCanvasList();
                startActivity(showClientIntent);
            } else {
                Intent clientIntent = new Intent();
                clientIntent.setClass(this, CompanyActivity.class);
                _clientController.createCompanyList(_SELECTEDCLIENTS);
                _canvasController.createCanvasList();
                startActivity(clientIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        _searchView = (EditText) MenuItemCompat.getActionView(searchItem);
        _searchView.setSingleLine();
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_search) {
            searchMethod(this);
//            doSearchAnimation();

            return true;
        }
        /*if (id == R.id.action_select_delete) {
            Toast.makeText(this, "select delete ", Toast.LENGTH_SHORT).show();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void searchMethod(final Activity activity) {
        _searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                _searchList = _clientController.getCompaniesByInput(_searchView.getText().toString().toLowerCase());

                _linearlayoutListView.removeAllViews();
                _adapter = new InflateClients(activity, _searchList, _linearlayoutListView);
                _adapter.inflateView();


// _linearlayoutListView.updateViewLayout(_adapter.test(_searchList),null);

            }
        });
    }


    private void doSearchAnimation() {
//        if (_txtSearch.getVisibility() == View.GONE) {
//            _txtSearch.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
//            _txtSearch.setVisibility(View.VISIBLE);
//        } else {
//            _txtSearch.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
//            _txtSearch.setText("");
//            _txtSearch.setVisibility(View.GONE);
//        }
    }
}

