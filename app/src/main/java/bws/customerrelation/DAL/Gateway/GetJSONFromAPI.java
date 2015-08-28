package bws.customerrelation.DAL.Gateway;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacob Ferdinandsen on 25-08-2015.
 */
public class GetJSONFromAPI extends AsyncTask<String, Void, JSONArray> {

    JSONArray jsonArray;
    JSONObject jsnobject;
    String result;
    //    String data = "";
//    String error;
    //    ProgressDialog progressDialog = new ProgressDialog();
//    Context _context;
//    EditText userInput;
    TextView serverDataReceiver;
    TextView showParsedView;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        progressDialog.setTitle("Please wait ...");
//        progressDialog.show();

//        try {
//            data += "&" + URLEncoder.encode("data", "UTF-8") + "-" + userInput.getText();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader br = null;
        URL url;
        try {
//            HttpResponse = httpClient.execute(new HttpGet(url);
            url = new URL(params[0]);

//            url = new URL("http://skynet.bws.dk/Applications/smsAndroid.nsf/(CanvasByCompany)?readviewentries&outputformat=json&start=1&count=10&restrict=2C7EFD49ADD61732C1256C2C002FEF71#");
            connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestMethod("GET");
            connection.connect();

//            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
//            osw.write(data);
//            osw.flush();

            InputStream is = connection.getInputStream();
//            InputStreamReader is =  new InputStreamReader();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                    return null;
            }
            br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            if (sb.length() == 0) {
                return null;
            }
            is.close();
            result = sb.toString();

//        } catch (MalformedURLException e) {
//            error = e.getMessage();
//            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Get Clients", "Error ", e);
            return null;
        }
            if (connection != null) {
                connection.disconnect();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (final IOException e) {
                    Log.e("GET Clients", "Error closing stream", e);
                }
            }
//            return null;
//            jsonArray = null;
            int size = 0;
            try {
//               jsonArray =  new JSONTokener(result).nextValue();
               jsnobject = new JSONObject(result);
                jsonArray = (JSONArray) jsnobject.get("viewentry");
//                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                Log.e("JSON", "Error creating JSON", e);
            }

            return jsonArray;

    }


//        @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//
////        progressDialog.dismiss();
//
//        if (error != null) {
//            serverDataReceiver.setText("error " + error);
//        } else {
//            serverDataReceiver.setText(result);

//            String output = "";
//            JSONObject jsonRespons;
//
//            try {
//                jsonRespons = new JSONObject(result);
//                JSONArray jsonArray = jsonRespons.optJSONArray("android");
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject child = jsonArray.getJSONObject(i);
//
//                    String name = child.getString("name");
//                    String number = child.getString("number");
//                    String time = child.getString("date_added");
//
//                    output = "name: " + name + System.getProperty("line.separator") + number + System.getProperty("line.separator") + time;
//                    output += System.getProperty("line.separator");
//                }
//
//                showParsedView.setText(output);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//}
//}
}
