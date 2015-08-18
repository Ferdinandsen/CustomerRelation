package bws.customerrelation.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.R;

public class ClientDataActivity extends AppCompatActivity {

    TextView firstName;
    TextView lastName;
    BEClient selectedClient;
    Button btnDLClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);
        Bundle b = getIntent().getExtras();
        selectedClient = (BEClient)b.getSerializable(SharedConstants.CLIENT);
        findViews();
        populateData();

    }

    private void populateData() {
        firstName.setText(selectedClient.getFirstName());
        lastName.setText(selectedClient.getLastName());
    }

    private void findViews() {
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        btnDLClients = (Button) findViewById(R.id.btnDLClients);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_client_data, menu);
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
}
