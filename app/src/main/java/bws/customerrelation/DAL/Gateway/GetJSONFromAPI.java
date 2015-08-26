package bws.customerrelation.DAL.Gateway;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import bws.customerrelation.GUI.MainActivity;

/**
 * Created by Jacob Ferdinandsen on 25-08-2015.
 */
public class GetJSONFromAPI extends AsyncTask<String, Void, JSONObject> {

    ArrayList<?> JsonArray;
    final HttpClient httpClient = new DefaultHttpClient();
    String content;
    String data = "";
    String error;
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
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        URL url;
        try {
            url = new URL(params[0]);

            connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.connect();

//            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
//            osw.write(data);
//            osw.flush();

            InputStream is = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (is == null) {
                return null;
            }

            br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            if (sb.length() == 0) {
                return null;
            }


            content = sb.toString();

//        } catch (MalformedURLException e) {
//            error = e.getMessage();
//            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Get Clients", "Error ", e);
            return null;
        } finally {
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
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(content);
            } catch (JSONException e) {
                Log.e("JSON", "Error creating JSON", e);
            }
            return jsonObject;
        }
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
//            serverDataReceiver.setText(content);

//            String output = "";
//            JSONObject jsonRespons;
//
//            try {
//                jsonRespons = new JSONObject(content);
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
