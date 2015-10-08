package bws.customerrelation.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import bws.customerrelation.Controller.SettingsController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.GUI.Company.CompanyActivity;
import bws.customerrelation.GUI.Company.CompanyMainActivity;
import bws.customerrelation.GUI.InflateLists.InflateCompanies;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BEUser;
import bws.customerrelation.R;

public class MainActivity extends AppCompatActivity {
    CompanyController _companyController;
    CanvasController _canvasController;
    SettingsController _settingsController;

    TextView _txtUserData;
    ImageView _bwsNet;
    Button _btnDownloadList;
    LinearLayout _linearlayoutListView;
    EditText _searchView;

    ProgressDialog _dialog;
    ArrayList<BECompany> _allCompanies;
    ArrayList<BECompany> _searchList;
    public static ArrayList<BECompany> SELECTEDCOMPANIES = new ArrayList<BECompany>();
    static int counter = 0;
    BEUser _user;

    private static String TAG = "MainActivity";
    InflateCompanies _adapter;
    InputMethodManager _imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        setContentView(R.layout.activity_main);
        _companyController = CompanyController.getInstance(this);
        _canvasController = CanvasController.getInstance(this);
        _settingsController = SettingsController.getInstance(this);
        _imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        _user = LoginActivity.USER;
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
//        _bwsNet = (ImageView) findViewById(R.id.bwsNet);
//        _bwsNet.setImageResource(R.mipmap.ic_launcher);
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
        counter += SELECTEDCOMPANIES.size();
        if (SELECTEDCOMPANIES.isEmpty()) {
            Toast.makeText(this, "Du har ikke valgt kunder", Toast.LENGTH_SHORT).show();
        } else {
            _dialog = ProgressDialog.show(this, "Please wait ...", "Fetching data", true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Here you should write your time consuming task...
                        if (SELECTEDCOMPANIES.size() == 1) {
                            Intent showCompanyIntent = new Intent();
                            showCompanyIntent.setClass(MainActivity.this, CompanyMainActivity.class);
                            _companyController.createCompanyList(SELECTEDCOMPANIES);
                            _canvasController.createCanvasList();
                            startActivity(showCompanyIntent);
                            _dialog.dismiss();
                        } else {
                            Intent companyIntent = new Intent();
                            companyIntent.setClass(MainActivity.this, CompanyActivity.class);
                            _companyController.createCompanyList(SELECTEDCOMPANIES);
                            _canvasController.createCanvasList();
                            startActivity(companyIntent);
                            _dialog.dismiss();
                        }
                        // Let the progress ring for 5 seconds...
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        Log.e("Login", e.toString());
                    }
                    _dialog.dismiss();
                }
            }).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        setupSearchView(searchItem);
        return true;
    }

    private void setupSearchView(MenuItem searchItem) {
        _searchView = (EditText) MenuItemCompat.getActionView(searchItem);
        _searchView.setSingleLine();
        _searchView.setWidth(300);
        _searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.action_search && !hasFocus) {
                    _imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    _imm.showSoftInput(_searchView, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                searchMethod(this);
                return true;
            case R.id.menu_save:
                saveDialog();
                return true;
            case R.id.menu_delete_all_companies:
                deleteAllCompaniesMenu();
                return true;
            case R.id.menu_updateSettingLists:
                updateSettingsList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSettingsList() {
        _settingsController.deleteDB();
        _settingsController.getAllSettingsFromAPI();
//        _settingsController.getAllCountriesFromAPI();
    }

    private void deleteAllCompaniesMenu() {
        new AlertDialog.Builder(this)
                .setTitle("remove all entries from device")
                .setMessage("Are you sure you want to remove all entries from this device?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        _companyController.deleteAllCompanies();
                        CompanyActivity.SELECTEDCOMPANY = null;
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void saveDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Upload canvas entries")
                .setMessage("Are you sure you want to upload all canvas entries?")
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

    private void searchMethod(final Activity activity) {
        _imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        _searchView.setText("");
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

