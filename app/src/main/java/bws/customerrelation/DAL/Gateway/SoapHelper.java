package bws.customerrelation.DAL.Gateway;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Jacob Ferdinandsen on 27-08-2015.
 */
public class SoapHelper extends AsyncTask<String, Void, ArrayList<?>> {

    final String NAMESPACE = "http://www.w3.org/2001/XMLSchema/";
    final String METHOD_NAME1 = "GetAllCompanies";
    final String SOAP_ACTION1 = "http://www.w3.org/2001/XMLSchema/GetAllCompanies";
    final String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/CreateClient?WSDL";

    ArrayList<?> list;


    public ArrayList<?> getAllCompanies() {
        list = new ArrayList<>();

        //Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

        //Use this to add parameters
//    request.addProperty("Fahrenheit",txtFar.getText().toString());

        //Declare the version of the SOAP request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;

        try {
            SoapObject result;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            //this is the actual part that will call the webservice
            androidHttpTransport.call(SOAP_ACTION1, envelope);

            // Get the SoapResult from the envelope body.
//            SoapObject result = (SoapObject) envelope.bodyIn;
            try {
                result = (SoapObject) envelope.getResponse();
            } catch (ClassCastException e) {
                result = (SoapObject) envelope.bodyIn;
            }

            Log.v("SOAP Helper", "TEST STRING : " + result.toString());
            if (result != null) {
                String test = result.toString();
                Log.v("SOAP Helper", "TEST STRING : " + test);
                //Get the first property and change the label text
//                txtCel.setText(result.getProperty(0).toString());
//                JSONObject res = new JSONObject(result);

            } else {
                Log.v("SOAP Helper", "Error in result");
//                Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected ArrayList<?> doInBackground(String... params) {
        Log.v("SOAP Helper", "Do in background");
        return getAllCompanies();
    }
}
