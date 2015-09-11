package bws.customerrelation.DAL.Gateway;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import bws.customerrelation.Model.BECanvas;

/**
 * Created by Jacob Ferdinandsen on 27-08-2015.
 */
public class SoapHelper extends AsyncTask<String, Void, String> {

    //    final String NAMESPACE = "http://www.w3.org/2001/XMLSchema/";
//    final String METHOD_NAME1 = "GetAllCompanies";
//    final String SOAP_ACTION1 = "http://www.w3.org/2001/XMLSchema/GetAllCompanies";
//    final String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/CreateClient?WSDL";
    BECanvas _canvas;
    ArrayList<?> list;

    public SoapHelper(BECanvas canvas) {
        _canvas = canvas;
    }

    public String postCanvas() {
        ArrayList<NameValuePair> alist = new ArrayList<>();
        alist.add(new BasicNameValuePair("REGION", _canvas.getM_Region() != null ? _canvas.getM_Region() : "ToDo"));
        alist.add(new BasicNameValuePair("TYPEOFTRANSPORT", _canvas.getM_TypeOfTransport() != null ? _canvas.getM_TypeOfTransport() : "ToDo"));
        alist.add(new BasicNameValuePair("COUNTRY", _canvas.getM_Country() != null ? _canvas.getM_Country() : "ToDo"));
        alist.add(new BasicNameValuePair("ACTIVITYTYPE", _canvas.getM_ActivityType() != null ? _canvas.getM_ActivityType() : "ToDo"));
        alist.add(new BasicNameValuePair("BUSINESSAREA", _canvas.getM_BusinessArea() != null ? _canvas.getM_BusinessArea() : "ToDo"));
        alist.add(new BasicNameValuePair("OFFICE", _canvas.getM_Office() != null ? _canvas.getM_Office() : "ToDo"));
        alist.add(new BasicNameValuePair("SENDER", _canvas.getM_Sender() != null ? _canvas.getM_Sender() : "ToDo"));
        alist.add(new BasicNameValuePair("TOINTERNAL", _canvas.getM_ToInternal() != null ? _canvas.getM_ToInternal() : "ToDo"));
        alist.add(new BasicNameValuePair("FOLLOWUPSALESMAN", _canvas.getM_FollowUpSalesman() != null ? _canvas.getM_FollowUpSalesman() : "ToDo"));
        alist.add(new BasicNameValuePair("FOLLOWUPDATE", _canvas.getM_FollowUpDate() != null ? _canvas.getM_FollowUpDate() : "ToDo"));
        alist.add(new BasicNameValuePair("CREATEDDATE", _canvas.getM_date() != null ? _canvas.getM_date() : "ToDo"));
        alist.add(new BasicNameValuePair("SUBJECT", _canvas.getM_Subject() != null ? _canvas.getM_Subject() : "ToDo"));
        alist.add(new BasicNameValuePair("VISITBY", _canvas.getM_VisitBy() != null ? _canvas.getM_VisitBy() : "ToDo"));
        alist.add(new BasicNameValuePair("TYPEVISIT", _canvas.getM_TypeOfVisit() != null ? _canvas.getM_TypeOfVisit() : "ToDo"));
        alist.add(new BasicNameValuePair("COMMENTS", _canvas.getM_text() != null ? _canvas.getM_text() : "ToDo"));
        alist.add(new BasicNameValuePair("MAINDOCUNID", _canvas.getM_companyId() != null ? _canvas.getM_companyId() : "ToDo"));

        String NAMESPACE = "http://www.w3.org/2001/12/soap-envelope/";
        String METHOD_NAME1 = "CreateCanvas";
        String SOAP_ACTION1 = "";
        String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/CreateClient?WSDL";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
        for (NameValuePair x : alist) {
            request.addProperty(x.getName(), x.getValue());
        }
        HttpTransportSE ht = new HttpTransportSE(URL);
        SoapObject response = new SoapObject();
        try {
            ht.call(SOAP_ACTION1, envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            response = (SoapObject) envelope.getResponse();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }


        return response.toString();

    }

    public ArrayList<?> getAllCompanies() {
        list = new ArrayList<>();

        String NAMESPACE = "http://www.w3.org/2001/XMLSchema/";
        String METHOD_NAME = "GetAllCompanies";
        String SOAP_ACTION = "http://www.w3.org/2001/XMLSchema/GetAllCompanies";
        String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/CreateClient?WSDL";
        //Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Use this to add parameters
//    request.addProperty("Fahrenheit",txtFar.getText().toString());

        //Declare the version of the SOAP request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        envelope.setAddAdornments(false);

        try {
            SoapObject result;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            //this is the actual part that will call the webservice
            androidHttpTransport.call(SOAP_ACTION, envelope);

            // Get the SoapResult from the envelope body.
//            SoapObject result = (SoapObject) envelope.bodyIn;
            try {
                result = (SoapObject) envelope.getResponse();
            } catch (ClassCastException e) {
                result = (SoapObject) envelope.bodyIn;
            }

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
    protected String doInBackground(String... params) {
        Log.v("SOAP Helper", "Do in background");
        return postCanvas();
//        return getAllCompanies();
    }
}
