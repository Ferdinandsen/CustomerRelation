package bws.customerrelation.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.CompanyController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Controller.UserController;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class MainActivity extends AppCompatActivity {
    CompanyController _companyController;
    CanvasController _canvasController;

    TextView _txtUserData;
    ImageView _bwsNet;
    Button _btnDownloadList;
    LinearLayout _linearlayoutListView;
    EditText _searchView;
    BEUser _user;

    ArrayList<BECompany> _allCompanies;
    ArrayList<BECompany> _searchList;
    static ArrayList<BECompany> SELECTEDCOMPANIES = new ArrayList<BECompany>();

    private static String TAG = "MainActivity";
    InflateCompanies _adapter;
    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras(); //Todo remove?
        _user = (BEUser) b.getSerializable(SharedConstants.USER);
        _companyController = CompanyController.getInstance(this);
        _canvasController = CanvasController.getInstance(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        findViews();
        setListeners();
        setUserData();
        populateCompanyList();
        inflateViews();
        SELECTEDCOMPANIES = _adapter.getSelectedClients() != null ? _adapter.getSelectedClients() : null;
    }

    private void inflateViews() {
        _adapter = new InflateCompanies(this, _allCompanies, _linearlayoutListView);
        _adapter.inflateView();
    }

    private void findViews() {
        _txtUserData = (TextView) findViewById(R.id.userData);
        _bwsNet = (ImageView) findViewById(R.id.bwsNet);
        _bwsNet.setImageResource(R.mipmap.ic_launcher);
        _btnDownloadList = (Button) findViewById(R.id.btnDownloadList);
        _linearlayoutListView = (LinearLayout) findViewById(R.id.linear_listview);
    }

    private void populateCompanyList() {
        _allCompanies = _companyController.getCompanies();
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

    //DOWNLOAD THE SELECTED ITEMS TO DB + start appropriate activity
    private void onClickDownloadList() {
        if (SELECTEDCOMPANIES.isEmpty()) {
            Toast.makeText(this, "Du har ikke valgt kunder", Toast.LENGTH_SHORT).show();
        } else {
            if (SELECTEDCOMPANIES.size() == 1) {
                Intent showCompanyIntent = new Intent();
                showCompanyIntent.setClass(this, CompanyDataActivity.class);
                _companyController.createCompanyList(SELECTEDCOMPANIES);
                _canvasController.createCanvasList();
                startActivity(showCompanyIntent);
            } else {
                Intent companyIntent = new Intent();
                companyIntent.setClass(this, CompanyActivity.class);
                _companyController.createCompanyList(SELECTEDCOMPANIES);
                _canvasController.createCanvasList();
                startActivity(companyIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        _searchView = (EditText) MenuItemCompat.getActionView(searchItem);
        _searchView.setSingleLine();
        _searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.action_search && !hasFocus) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    imm.showSoftInput(_searchView, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            searchMethod(this);
            return true;
        }
        if (id == R.id.menu_save) {
            new AlertDialog.Builder(this)
                    .setTitle("Save entries")
                    .setMessage("Are you sure you want to save all entries?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            _canvasController.PostCanvasToNotes();
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
                _searchList = _companyController.getCompaniesByInput(_searchView.getText().toString().toLowerCase());
                _linearlayoutListView.removeAllViews();
                _adapter = new InflateCompanies(activity, _searchList, _linearlayoutListView);
                _adapter.inflateView();
            }
        });
        _searchView.requestFocus();
    }
}

