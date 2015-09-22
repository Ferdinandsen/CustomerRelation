package bws.customerrelation.DAL;

import android.app.Activity;
import android.content.Context;
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

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.CompanyController;
import bws.customerrelation.DAL.Gateway.GetJSONFromAPI;
import bws.customerrelation.DAL.Gateway.VolleySingleton;
import bws.customerrelation.GUI.LoginActivity;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class DAOCompany {

    Activity _activity;
    SQLiteDatabase _db;
    SQLiteStatement _sql;
    JSONObject obj;
    ArrayList<BECompany> aList;

    String _INSERTCOMPANY = "INSERT INTO " + DAConstants.TABLE_COMPANY + "(CompanyId, CompanyName, Address, City, Zip, Country, Phone, Fax, Email, SeNo, " +
            "SalesArea, BusinessRelation, CompanyGroup, CompanyClosed, CompanyHomepage) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public DAOCompany(Activity activity) {
        _activity = activity;
        OpenHelper openHelper = new OpenHelper(_activity);
        _db = openHelper.getWritableDatabase();
    }

    public void getJSON() {
        String url = "http://skynet.bws.dk/Applications/smsAndroid.nsf/LookupCompanyNameAndUNID?readviewentries&outputformat=json&start=1&count=100&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        obj = new JSONObject();
        aList = new ArrayList<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        obj = response;
                        try {
                            aList = ConvertFromJsonToBE(obj);
                            CompanyController.setCachedList(aList);
                            LoginActivity.loadingCompanies = true;
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

//    public ArrayList<BECompany> getCompanyFromApi() {
//        ArrayList<BECompany> companyList = new ArrayList<>();
//        String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/LookupCompanyNameAndUNID?readviewentries&outputformat=json&start=1&count=100&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
//        JSONObject obj;
//        GetJSONFromAPI api = new GetJSONFromAPI(_activity);
//        api.execute(URL);
//
//        try {
//            obj = api.get();
//            companyList = ConvertFromJsonToBE(obj);
//        } catch (Exception e) {
//            Log.e("Api get", "Error when trying to connect to api", e);
//        }
//        return companyList;
//    }

    /**
     * KONVERTER JSON OG ADD TIL ARRAYLIST
     */

    private ArrayList<BECompany> ConvertFromJsonToBE(JSONObject object) throws JSONException {
        JSONObject obj;
        JSONArray obj1;

        JSONArray jsonArray = (JSONArray) object.get("viewentry");

        ArrayList<BECompany> mList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            obj = jsonArray.getJSONObject(i);
            float flo = Float.parseFloat(obj.getString("@position"));
            if (flo % 1 == 0) {
                obj1 = obj.getJSONArray("entrydata");
                mList.add(setCompanyBE(obj1));
            }
        }
        return mList;
    }

    private BECompany setCompanyBE(JSONArray array) throws JSONException {
        String id, zip, country, phone, fax, email, seNo, salesArea, businessRelation, companyGroup,
                companyName, address, city, companyHomepage;
        Boolean companyClosed;

        ArrayList<String> array1 = new ArrayList<>();
        for (int y = 0; y < array.length(); y++) {
            if (!array.getJSONObject(y).isNull("text")) {
                array1.add(array.getJSONObject(y).getJSONObject("text").getString("0"));
            } else {
                array1.add("NULL");
            }
        }

        id = array1.get(0);
        companyName = (String) array1.get(1);
        address = (String) array1.get(2);
        city = (String) array1.get(3);
        zip = (String) array1.get(4);
        country = (String) array1.get(5);
        phone = (String) array1.get(6);
        fax = (String) array1.get(7);
        email = (String) array1.get(8);
        seNo = (String) array1.get(9);
        salesArea = (String) array1.get(10);
        businessRelation = (String) array1.get(11);
        companyGroup = (String) array1.get(12);
        if (array1.get(13).equals("") || array1.get(13).toLowerCase().equals("false")) {
            companyClosed = false;
        } else {
            companyClosed = true;
        }
        companyHomepage = (String) array1.get(14);

        BECompany company = new BECompany(id, companyName, address, city, zip, country, phone, fax, email, seNo, salesArea, businessRelation, companyGroup, companyClosed,
                companyHomepage);
        return company;
    }

    public ArrayList<BECompany> getAllCompaniesFromDevice() {
        ArrayList<BECompany> clients = new ArrayList<BECompany>();
        Cursor cursor = _db.query(DAConstants.TABLE_COMPANY,
                new String[]{"CompanyId", "CompanyName", "Address", "City", "Zip", "Country", "Phone",
                        "Fax", "Email", "SeNo", "SalesArea", "BusinessRelation", "CompanyGroup", "CompanyClosed", "CompanyHomepage"},
                null, null, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                boolean bol = Boolean.valueOf(cursor.getString(13));
                BECompany compa = new BECompany(
                        cursor.getString(0), cursor.getString(1).replace("Ã~", "oe"), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11),
                        cursor.getString(12), bol, cursor.getString(14)); //, isDl
                clients.add(compa);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }

    public long insertCompanyOnDevice(BECompany client) {
        _sql = _db.compileStatement(_INSERTCOMPANY);
        _sql.bindString(1, "" + client.getM_companyId());
        _sql.bindString(2, "" + client.getM_companyName());
        _sql.bindString(3, "" + client.getM_address());
        _sql.bindString(4, "" + client.getM_city());
        _sql.bindString(5, "" + client.getM_zipCode());
        _sql.bindString(6, "" + client.getM_country());
        _sql.bindString(7, "" + client.getM_telephone());
        _sql.bindString(8, "" + client.getM_fax());
        _sql.bindString(9, "" + client.getM_email());
        _sql.bindString(10, "" + client.getM_seNo());
        _sql.bindString(11, "" + client.getM_salesArea());
        _sql.bindString(12, "" + client.getM_businessRelation());
        _sql.bindString(13, "" + client.getM_companyGroup());
        _sql.bindString(14, "" + client.getM_closedCompany());
        _sql.bindString(15, "" + client.getM_homepage());
        return _sql.executeInsert();
    }

    public void deleteAllCompanies() {
//        _db.execSQL("DELETE FROM " + DAConstants.TABLE_COMPANY + " WHERE CompanyId != " + 107800);
        _db.delete(DAConstants.TABLE_COMPANY, null, null);
    }

    public void deleteById(String id) {
        _db.delete(DAConstants.TABLE_COMPANY, "CompanyId=?" ,new String[]{id});
    }
}

