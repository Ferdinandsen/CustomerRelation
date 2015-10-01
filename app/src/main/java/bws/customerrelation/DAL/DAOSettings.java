package bws.customerrelation.DAL;

import android.app.Activity;

import android.database.Cursor;
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

import java.util.ArrayList;

import java.util.HashMap;

import bws.customerrelation.Controller.SettingsController;
import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.DAL.Gateway.GetJSONListHelper;
import bws.customerrelation.DAL.Gateway.VolleySingleton;
import bws.customerrelation.GUI.LoginActivity;
import bws.customerrelation.Model.BECompany;
import bws.customerrelation.Model.BECountry;

/**
 * Created by Jaje on 17-Sep-15.
 */
public class DAOSettings {

    Activity _activity;
    SQLiteDatabase _db;
    SQLiteStatement _sql;
    JSONObject obj;
    HashMap<String, ArrayList<String>> stringList;
    ArrayList<String> list;
    ArrayList<BECountry> countryList;
    GetJSONListHelper helper;


    String _INSERTCOUNTRY = "INSERT INTO " + DAConstants.TABLE_COUNTRY + "(Name, Region, CountryCode, PhonePrefix) VALUES (?,?,?,?)";
    String _INSERTACTIVE = "INSERT INTO " + DAConstants.TABLE_ACTIVE + "(Name) VALUES(?)";
    String _INSERTACTIVITY = "INSERT INTO " + DAConstants.TABLE_ACTIVITYTYPE + "(Name) VALUES(?)";
    String _INSERTBUSINESSAREA = "INSERT INTO " + DAConstants.TABLE_BUSINESSAREA + "(Name) VALUES(?)";
    String _INSERTBUSINESSRELATION = "INSERT INTO " + DAConstants.TABLE_BUSINESSRELATION + "(Name) VALUES(?)";
    String _INSEROFFICE = "INSERT INTO " + DAConstants.TABLE_OFFICE + "(Name) VALUES(?)";
    String _INSERTTYPEOFTRANSPORT = "INSERT INTO " + DAConstants.TABLE_TYPEOFTRANSPORT + "(Name) VALUES(?)";
    String _INSERTTYPEOFVISIT = "INSERT INTO " + DAConstants.TABLE_TYPEOFVISIT + "(Name) VALUES(?)";

    public DAOSettings(Activity activity) {
        _activity = activity;
        list = new ArrayList<>();
        OpenHelper openHelper = new OpenHelper(_activity);
        _db = openHelper.getWritableDatabase();
    }

    public void getJSONCountryList() {
        String url = "http://skynet.bws.dk/Applications/MailCode.nsf/LookupCountry?readviewentries&outputformat=json&start=1&count=500&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        obj = new JSONObject();
        countryList = new ArrayList<BECountry>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        obj = response;
                        try {
                            countryList = ConvertFromJsonToBE(obj);
                            addCountryToDB(countryList);
                            LoginActivity.loadingCountriesDone = true;
                        } catch (JSONException e) {
                            Log.e("DAOSettings", "Error in GetJSON", e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("DAOSettings", "Error in volley part", error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(_activity).addToRequestQueue(jsObjRequest);
    }

    private void addCountryToDB(ArrayList<BECountry> countryList) {

        for (BECountry c : countryList) {
            if (c.get_name().equals("Worldwide") && c.get_region().equals("")) {

            } else {
                insertCountriesOnDevice(c);
            }
        }
    }

    private long insertCountriesOnDevice(BECountry country) {
        _sql = _db.compileStatement(_INSERTCOUNTRY);
        _sql.bindString(1, "" + country.get_name());
        _sql.bindString(2, "" + country.get_region());
        _sql.bindString(3, "" + country.get_countryCode());
        _sql.bindString(4, "" + country.get_phonePrefix());
        return _sql.executeInsert();
    }

    private ArrayList<BECountry> ConvertFromJsonToBE(JSONObject object) throws JSONException {
        JSONObject obj;
        JSONArray obj1;

        JSONArray jsonArray = (JSONArray) object.get("viewentry");
        ArrayList<BECountry> mList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            obj = jsonArray.getJSONObject(i);
            //Sørger for at vi ikke kommer ind i sub entries == 1.1 etc...
            float flo = Float.parseFloat(obj.getString("@position"));
            if (flo % 1 == 0) {
                obj1 = obj.getJSONArray("entrydata");
                mList.add(setCountryBE(obj1));
            }
        }
        return mList;
    }

    private BECountry setCountryBE(JSONArray array) throws JSONException {
        String name, region, code, phonePrefix;

        ArrayList<String> array1 = new ArrayList<>();
        for (int y = 0; y < array.length(); y++) {
            if (!array.getJSONObject(y).isNull("text")) {
                array1.add(array.getJSONObject(y).getJSONObject("text").getString("0"));
            } else {
                array1.add("NULL");
            }
        }
        name = array1.get(0);
        region = array1.get(1);
        code = array1.get(2);
        phonePrefix = array1.get(3);

        BECountry country = new BECountry(name, region, code, phonePrefix);
        return country;
    }

    public void getJSONListOfActivity() {
        String url = "http://skynet.bws.dk/Applications/smsAndroid.nsf/LookupKeywordActivityType?readviewentries&outputformat=json&start=1&count=1000&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        obj = new JSONObject();
        stringList = new HashMap<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        obj = response;
                        try {
                            stringList = convertFromJSONtoStrings(obj);
                            addSettingsToDB(stringList);
                            LoginActivity.loadingSettingsDone = true;
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

    private void addSettingsToDB(HashMap<String, ArrayList<String>> stringList) {
        for (String s : stringList.get(SharedConstants.ACTIVE)) {
            insertSettingsOnDevice(_INSERTACTIVE, s);
        }
        for (String s : stringList.get(SharedConstants.ACTIVITY)) {
            insertSettingsOnDevice(_INSERTACTIVITY, s);
        }
        for (String s : stringList.get(SharedConstants.BUSINESSAREA)) {
            insertSettingsOnDevice(_INSERTBUSINESSAREA, s);
        }
        for (String s : stringList.get(SharedConstants.BUSINESSRELATION)) {
            insertSettingsOnDevice(_INSERTBUSINESSRELATION, s);
        }
        for (String s : stringList.get(SharedConstants.OFFICE)) {
            insertSettingsOnDevice(_INSEROFFICE, s);
        }
        for (String s : stringList.get(SharedConstants.TRANSPORTTYPE)) {
            insertSettingsOnDevice(_INSERTTYPEOFTRANSPORT, s);
        }
        for (String s : stringList.get(SharedConstants.VISITTYPE)) {
            insertSettingsOnDevice(_INSERTTYPEOFVISIT, s);
        }
    }

    private long insertSettingsOnDevice(String x, String y) {
        _sql = _db.compileStatement(x);
        _sql.bindString(1, y);
        return _sql.executeInsert();
    }

    private HashMap<String, ArrayList<String>> convertFromJSONtoStrings(JSONObject object) throws JSONException {
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

    public HashMap<String, ArrayList<String>> getAllSettingsFromDevice() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> localList;
        for (String s : DAConstants.TABLEPACK) {
            localList = getSettingFromDevice(s);
            switch (s) {
                case DAConstants.TABLE_ACTIVE:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.ACTIVE, getSettingFromDevice(s));
                    }
                    break;
                case DAConstants.TABLE_ACTIVITYTYPE:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.ACTIVITY, getSettingFromDevice(s));
                    }
                    break;
                case DAConstants.TABLE_BUSINESSAREA:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.BUSINESSAREA, getSettingFromDevice(s));
                    }
                    break;
                case DAConstants.TABLE_BUSINESSRELATION:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.BUSINESSRELATION, getSettingFromDevice(s));
                    }
                    break;
                case DAConstants.TABLE_OFFICE:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.OFFICE, getSettingFromDevice(s));
                    }
                    break;
                case DAConstants.TABLE_TYPEOFTRANSPORT:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.TRANSPORTTYPE, getSettingFromDevice(s));
                    }
                    break;
                case DAConstants.TABLE_TYPEOFVISIT:
                    if (!localList.isEmpty()) {
                        map.put(SharedConstants.VISITTYPE, getSettingFromDevice(s));
                    }
                    break;
            }
        }
        return map;
    }

    private ArrayList<String> getSettingFromDevice(String s) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = _db.query(s,
                new String[]{"Name"},
                null, null, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                String st = cursor.getString(0);
                list.add(st);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public void deleteDB() {
        _db.delete(DAConstants.TABLE_ACTIVE, null, null);
        _db.delete(DAConstants.TABLE_ACTIVITYTYPE, null, null);
        _db.delete(DAConstants.TABLE_BUSINESSAREA, null, null);
        _db.delete(DAConstants.TABLE_BUSINESSRELATION, null, null);
        _db.delete(DAConstants.TABLE_OFFICE, null, null);
        _db.delete(DAConstants.TABLE_TYPEOFTRANSPORT, null, null);
        _db.delete(DAConstants.TABLE_TYPEOFVISIT, null, null);
        _db.delete(DAConstants.TABLE_COUNTRY, null, null);
    }

    public ArrayList<BECountry> getCountryFromDevice() {
        ArrayList<BECountry> list = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_COUNTRY,
                new String[]{"Name", "Region", "CountryCode", "PhonePrefix"},
                null, null, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String region = cursor.getString(1);
                String code = cursor.getString(2);
                String phone = cursor.getString(3);
                list.add(new BECountry(name, region, code, phone));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;

    }
}


