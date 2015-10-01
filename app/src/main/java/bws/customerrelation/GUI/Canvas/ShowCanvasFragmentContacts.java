package bws.customerrelation.GUI.Canvas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bws.customerrelation.GUI.Company.CompanyDataActivity;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.R;

/**
 * Created by jaje on 01-Oct-15.
 */
public class ShowCanvasFragmentContacts extends Fragment {

    View rootView;
    TextView mainContact;
    TextView mainContactPhone;
    TextView mainContactMobile;
    TextView mainContactFax;
    TextView mainContactEmail;
    TextView mainContactDivision;
    TextView secondContact;
    TextView secondContactPhone;
    TextView secondContactMobile;
    TextView thirdContact;
    TextView thirdContactPhone;
    TextView thirdContactMobile;
    TextView fourthContact;
    TextView fourthContactPhone;
    TextView fourthContactMobile;
    private static String TAG = "ShowCanvasActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contacts_main, container, false);
        super.onCreate(savedInstanceState);
        BECanvas canvas = CompanyDataActivity.SELECTEDCANVAS;
        findviews();
        setCanvasData(canvas);

        return rootView;
    }

    private void setCanvasData(BECanvas c) {
        mainContact.setText(c.getM_mainContact());
        mainContactPhone.setText(c.getM_mainContactPhone());
        mainContactMobile.setText(c.getM_mainContactMobile());
        mainContactFax.setText(c.getM_mainContactFax());
        mainContactEmail.setText(c.getM_mainContactEmail());
        mainContactDivision.setText(c.getM_mainContactDivision());
//        mainContactAddress.setText()
        secondContact.setText(c.getM_secondContact());
        secondContactPhone.setText(c.getM_secondContactPhone());
        secondContactMobile.setText(c.getM_secondContactMobile());
//        thirdContact.setText(c.getM_thirdContact());
//        thirdContactPhone.setText(c.getM_thirdContactPhone());
//        thirdContactMobile.setText(c.getM_thirdContactMobile());
//        fourthContact.setText(c.getM_fourthContact());
//        fourthContactPhone.setText(c.getM_fourthContactPhone());
//        fourthContactMobile.setText(c.getM_fourthContactMobile());
    }

    private void findviews() {
        mainContact = (TextView) rootView.findViewById(R.id.contactName);
        mainContactPhone = (TextView) rootView.findViewById(R.id.contactPhone);
        mainContactMobile = (TextView) rootView.findViewById(R.id.contactMobilePhone);
        mainContactFax = (TextView) rootView.findViewById(R.id.contactfax);
        mainContactEmail = (TextView) rootView.findViewById(R.id.contactEmail);
//        mainContactDivision = (TextView) rootView.findViewById(R.id.);
//        mainContactAddress = (Textview) rootView.findViewById(R.id.contactAddress);
        secondContact = (TextView) rootView.findViewById(R.id.addContactName);
        secondContactPhone = (TextView) rootView.findViewById(R.id.addContactPhone);
        secondContactMobile = (TextView) rootView.findViewById(R.id.addContactMobile);
//        thirdContact = (TextView) rootView.findViewById(R.id.followUpDate);
//        thirdContactPhone = (TextView) rootView.findViewById(R.id.subject);
//        thirdContactMobile = (TextView) rootView.findViewById(R.id.date);
//        fourthContact = (TextView) rootView.findViewById(R.id.from);
//        fourthContactPhone = (TextView) rootView.findViewById(R.id.toInternal);
//        fourthContactMobile = (TextView) rootView.findViewById(R.id.comments);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_show_canvas, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}


}
