package bws.customerrelation.DAL.Gateway;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import bws.customerrelation.Controller.SharedConstants;

/**
 * Created by jaje on 01-Oct-15.
 */
public class GetJSONListHelper {
    JSONObject obj;
    HashMap<?, ?> stringList = new HashMap<>();
    Activity _activity;
    ArrayList<String> list = new ArrayList<>();

    public void GetJSONListHelper(Activity activity, String url) {
        _activity = activity;
        obj = new JSONObject();
        stringList = new HashMap<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        obj = response;
                        try {
                            convertFromJSONToStringsSettings(obj);
//                            stringList = convertFromJSONtoStrings(obj);
//                            addSettingsToDB(stringList);
//                            LoginActivity.loadingSettingsDone = true;
                        } catch (JSONException e) {
                            Log.e("DAOSettings", "Error in GetJSON", e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("DAOSettings", "Error in volley part", error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(_activity).addToRequestQueue(jsObjRequest);
    }

    private HashMap<String, ArrayList<String>> convertFromJSONToStringsSettings(JSONObject object) throws JSONException {
        JSONObject obj;
        JSONArray obj1;
        ArrayList<String> activeList;
        ArrayList<String> activityTypeList;
        ArrayList<String> businessAreaList;
        ArrayList<String> businessRelationList;
        ArrayList<String> officeList;
        ArrayList<String> typeOfTransportList;
        ArrayList<String> typeOfVisitList;
        HashMap<String, ArrayList<String>> map = new HashMap<>();

        JSONArray jsonArray = (JSONArray) object.get("viewentry");

        for (int i = 0; i < jsonArray.length(); i++) {
            obj = jsonArray.getJSONObject(i);
            obj1 = obj.getJSONArray("entrydata");
            String switchString = obj1.getJSONObject(0).getJSONObject("text").getString("0");
            switch (switchString) {
                case SharedConstants.ACTIVE:
                    activeList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.ACTIVE, activeList);
                    break;
                case SharedConstants.ACTIVITY:
                    activityTypeList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.ACTIVITY, activityTypeList);
                    break;
                case SharedConstants.BUSINESSAREA:
                    businessAreaList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.BUSINESSAREA, businessAreaList);
                    break;
                case SharedConstants.BUSINESSRELATION:
                    businessRelationList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.BUSINESSRELATION, businessRelationList);
                    break;
                case SharedConstants.OFFICE:
                    officeList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.OFFICE, officeList);
                    break;
                case SharedConstants.TRANSPORTTYPE:
                    typeOfTransportList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.TRANSPORTTYPE, typeOfTransportList);
                    break;
                case SharedConstants.VISITTYPE:
                    typeOfVisitList = createArrayListFromJSONObj(obj1);
                    map.put(SharedConstants.VISITTYPE, typeOfVisitList);
                    break;
            }
        }
        return map;
    }

    private ArrayList<String> createArrayListFromJSONObj(JSONArray array) throws JSONException {
        list = new ArrayList<>();
        for (int y = 0; y < array.length(); y++) {
            if (!array.getJSONObject(y).isNull("text")) {
                list.add(array.getJSONObject(y).getJSONObject("text").getString("0"));
            } else if (!array.getJSONObject(y).isNull("textlist")) {
                JSONArray ara = array.getJSONObject(y).getJSONObject("textlist").getJSONArray("text");
                for (int i = 0; i < ara.length(); i++) {
                    list.add(ara.getJSONObject(i).getString("0"));
                }
            } else {
                list.add("");
                Log.e("DAOCanvas", "Error in adding correct data to array");
            }
        }
        return list;
    }
}
