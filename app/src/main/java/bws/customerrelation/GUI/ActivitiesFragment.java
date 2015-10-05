package bws.customerrelation.GUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bws.customerrelation.Model.BECompany;
import bws.customerrelation.R;

public class ActivitiesFragment extends Fragment {
    View rootView;
    TextView contactNameLbL;
    TextView contactName;
    TextView contactPhone;
    TextView contactEmail;
    TextView contactFax;
    TextView contactAddress;
    TextView contactZipcode;
    TextView contactCountry;
    TextView contactMobile;

    BECompany _selectedCompany;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contacts_main, container, false);
        super.onCreate(savedInstanceState);
//        _selectedCompany = CompanyActivity.SELECTEDCOMPANY != null ? CompanyActivity.SELECTEDCOMPANY : MainActivity.SELECTEDCOMPANIES.get(0);
        findViews();
        populateData();
//        setListeners();
//        inflateViews();

        return rootView;

    }

    private void populateData() {
        contactNameLbL.setText("");
        contactName.setText("Under Construction");
//        contactPhone.setText("test1");
//        contactEmail.setText("test2");
//        contactFax.setText("test3");
//        contactAddress.setText("test4");
//        contactZipcode.setText("test5");
//        contactCountry.setText("test6");
//        contactMobile.setText("test7");
    }

    private void findViews() {
        contactNameLbL = (TextView) rootView.findViewById(R.id.contactNameLbL);
        contactName = (TextView) rootView.findViewById(R.id.contactName);
//        contactPhone = (TextView) rootView.findViewById(R.id.contactPhone);
//        contactEmail = (TextView) rootView.findViewById(R.id.contactEmail);
//        contactFax = (TextView) rootView.findViewById(R.id.contactfax);
//        contactAddress = (TextView) rootView.findViewById(R.id.contactAddress);
//        contactZipcode = (TextView) rootView.findViewById(R.id.contactZipcode);
//        contactCountry = (TextView) rootView.findViewById(R.id.contactCountry);
//        contactMobile = (TextView) rootView.findViewById(R.id.contactMobilePhone);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_client_data, menu);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.menu_delete_by_id) {
//            new AlertDialog.Builder(getActivity())
//                    .setTitle("Really delete " + "..." + "?")
//                    .setMessage("Are you sure you want to delete this company?")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            final String cId = _selectedCompany.getM_companyId();
//                            _companyController.deleteCompanyById(cId);
//                            //TODO lukke den her og åbne noget andet !??!?!?!?!?!??!?!?!?!??
//                            // TODO !=!==!=!=!=!?!?!"=
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
