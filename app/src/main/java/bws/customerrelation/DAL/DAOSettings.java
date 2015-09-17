package bws.customerrelation.DAL;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bws.customerrelation.Controller.SettingsController;
import bws.customerrelation.DAL.Gateway.VolleySingleton;
import bws.customerrelation.Model.BECanvas;

/**
 * Created by Jaje on 17-Sep-15.
 */
public class DAOSettings {

    Activity _activity;
    SQLiteDatabase _db;
    JSONObject obj;
    ArrayList<String> stringList;
    ArrayList<String> list;

    public DAOSettings(Activity activity) {
        _activity = activity;
        OpenHelper openHelper = new OpenHelper(_activity);
        _db = openHelper.getWritableDatabase();
        list = new ArrayList<>();
    }

    public void getJSONListOfActivity() {
        String url = "http://skynet.bws.dk/Applications/smsAndroid.nsf/LookupKeywordActivityType?readviewentries&outputformat=json&start=1&count=1000&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        obj = new JSONObject();
        stringList = new ArrayList<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        obj = response;
                        try {
                            stringList = convertFromJSONtoStrings(obj);
                            SettingsController.setSettingsList(stringList);
                        } catch (JSONException e) {
                            Log.e("DAOCanvas", "Error in GetJSON", e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("DAOCANVAS", "Error in volley part", error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(_activity).addToRequestQueue(jsObjRequest);
    }

    private ArrayList<String> convertFromJSONtoStrings(JSONObject object) throws JSONException {
        JSONObject obj;
        JSONArray obj1;
        JSONArray jsonArray = (JSONArray) object.get("viewentry");

        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            obj = jsonArray.getJSONObject(i);
            obj1 = obj.getJSONArray("entrydata");
            mList = createArrayListFromJSONObj(obj1);
        }
        return mList;
    }

    //TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private ArrayList<String> createArrayListFromJSONObj(JSONArray array) throws JSONException {

        for (int y = 0; y < array.length(); y++) {
            if (!array.getJSONObject(y).isNull("text")) {
                list.add(array.getJSONObject(y).getJSONObject("text").getString("0"));
            } else if (!array.getJSONObject(y).isNull("textlist")) {
                JSONArray ara = array.getJSONObject(y).getJSONObject("textlist").getJSONArray("text");

                String res = "";
                StringBuilder sb = new StringBuilder(res);
                for (int i = 0; i < ara.length(); i++) {
                    sb.append(ara.getJSONObject(i).getString("0") + "\n");
                }
                res = sb.toString();
                String[] split = res.split("\n");
                for (String x : split) {
                    list.add(x);
                }
//                array1.add(sb.toString());
            } else {
                list.add("");
                Log.e("DAOCanvas", "Error in adding correct data to array");
            }
        }
        return list;
    }
}
