package bws.customerrelation.DAL;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import bws.customerrelation.Controller.CanvasController;
import bws.customerrelation.Controller.SettingsController;
import bws.customerrelation.DAL.Gateway.GetJSONFromAPI;
import bws.customerrelation.DAL.Gateway.GetRTFFromHTML;
import bws.customerrelation.DAL.Gateway.VolleySingleton;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jaje on 01-Sep-15.
 */
public class DAOCanvas {

    Activity _activity;
    SQLiteDatabase _db;
    SQLiteStatement _sql;
    ArrayList<BECanvas> aList;
    JSONObject obj;
    Gson gson;

    //DL
    String _INSERTCANVAS = "INSERT INTO " + DAConstants.TABLE_CANVAS + "(CanvasId, CompanyId, Subject, VisitBy, " +
            "TypeOfVisit, Date, FollowUpDate, FollowUpSalesman, " +
            "Sender, ToInternal, Region, Country, TypeOfTransport, " +
            "ActivityType, BusinessArea, Office, Text) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String _INSERTUPLOAD = "INSERT INTO " + DAConstants.TABLE_UPLOAD + "(CanvasId, CompanyId, Subject, VisitBy, " +
            "TypeOfVisit, Date, FollowUpDate, FollowUpSalesman, " +
            "Sender, ToInternal, Region, Country, TypeOfTransport, " +
            "ActivityType, BusinessArea, Office, Text) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public DAOCanvas(Activity activity) {
        _activity = activity;
        OpenHelper openHelper = new OpenHelper(_activity);
        _db = openHelper.getWritableDatabase();
        gson = new Gson();
    }

    /**
     * Henter alle Canvas fra URL
     *
     * @return ArrayList<BECanvas> med alle canvas i fra URL/Notes
     */
//    public ArrayList<BECanvas> getAllCanvasFromAPI() {
//        ArrayList<BECanvas> companyList = new ArrayList<>();
//        final String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/(CanvasByCompany)?readviewentries&outputformat=json&start=1&count=1000&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
//        JSONObject obj;
//        GetJSONFromAPI api = new GetJSONFromAPI(_activity);
//        api.execute(URL);
//
//        try {
//            obj = api.get();
//            companyList = convertFromJsonToBE(obj);
//        } catch (Exception e) {
//            Log.e("Api get", "Error when trying to connect to api", e);
//        }
//        return companyList;
//    }

    /**
     * KONVERTER JSON OG ADD TIL ARRAYLIST
     */
    public void getJSON() {
        String url = "http://skynet.bws.dk/Applications/smsAndroid.nsf/(CanvasByCompany)?readviewentries&outputformat=json&start=1&count=1000&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        obj = new JSONObject();
        aList = new ArrayList<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        obj = response;
                        try {
                            aList = convertFromJsonToBE(obj);
                            CanvasController.setCachedList(aList);
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


    /**
     * Konverterer JSON til BE og adder til ArrayList<BECanvas>
     *
     * @param object Det objekt vi modtager fra getAllCanvasFromAPI
     * @return ArrayList<BECanvas> en komplet liste af BECanvas
     * @throws JSONException
     */

    private ArrayList<BECanvas> convertFromJsonToBE(JSONObject object) throws JSONException {
        JSONObject obj;
        JSONArray obj1;
        JSONArray jsonArray = (JSONArray) object.get("viewentry");

        ArrayList<BECanvas> mList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            obj = jsonArray.getJSONObject(i);
            obj1 = obj.getJSONArray("entrydata");
            mList.add(setBECanvas(obj1)); //Todo FOR løkke!
        }
        return mList;
    }

    private BECanvas setBECanvas(JSONArray array) throws JSONException {
        String canvasId, companyId, TypeOfVisit, VisitBy,
                Subject, Date, FollowUpBy, FollowUpDate,
                Sender, ToInternal, Region, Country,
                TypeOfTransport, Activity, BusinessArea, Office, Text;

        ArrayList<String> array1 = new ArrayList<>();
        java.util.Date nyDate;
        for (int y = 0; y < array.length(); y++) {
            nyDate = new Date();
            if (!array.getJSONObject(y).isNull("text")) {
                array1.add(array.getJSONObject(y).getJSONObject("text").getString("0"));
            } else if (!array.getJSONObject(y).isNull("textlist")) {
                JSONArray ara = array.getJSONObject(y).getJSONObject("textlist").getJSONArray("text");

                String res = "";
                StringBuilder sb = new StringBuilder(res);
                for (int i = 0; i < ara.length(); i++) {
                    sb.append(ara.getJSONObject(i).getString("0") + "\n");
                }
                array1.add(sb.toString());
            } else if (!array.getJSONObject(y).isNull("datetime")) {

                String date = array.getJSONObject(y).getJSONObject("datetime").getString("0");
                String test;
                SimpleDateFormat sdfIn;
                String res = "NULL";
                SimpleDateFormat sdfOut;

                String[] ss = date.split("T");
                test = ss[0];
                sdfIn = new SimpleDateFormat("yyyyMMdd");
                sdfOut = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    nyDate = sdfIn.parse(test);
                    res = sdfOut.format(nyDate);

                } catch (ParseException e) {
                    Log.e("DAOCanvas", "Parse date error", e);
                    e.printStackTrace();
                }
                array1.add(res);
            } else {
                array1.add("");
                Log.e("DAOCanvas", "Error in adding correct data to array");
            }
        }
        Date = (String) array1.get(0);
        canvasId = array1.get(1);
        companyId = (String) array1.get(2);
        Subject = (String) array1.get(3);
        VisitBy = (String) array1.get(4);
        TypeOfVisit = (String) array1.get(5);
        FollowUpDate = (String) array1.get(6);
        FollowUpBy = (String) array1.get(7);
        Sender = (String) array1.get(8);
        ToInternal = (String) array1.get(9);
        Region = (String) array1.get(10);
        Country = (String) array1.get(11);
        TypeOfTransport = (String) array1.get(12);
        Activity = (String) array1.get(13);
        BusinessArea = (String) array1.get(14);
        Office = (String) array1.get(15);
        Text = "pre entry";

        BECanvas canvas = new BECanvas(canvasId, companyId, Subject, VisitBy, TypeOfVisit, Date, FollowUpDate, FollowUpBy,
                Sender, ToInternal, Region, Country,
                TypeOfTransport, Activity, BusinessArea, Office, Text);
        return canvas;
    }


    public String getRichTextFromHtmlByCanvasId(String id) {
        //Set comments field
        GetRTFFromHTML RTF = new GetRTFFromHTML();
        RTF.execute(id);
        try {
            return RTF.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error getrich interrupted";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Error getrich execution";
        }
    }

    public long insertUploadCanvasShort(BECanvas canvas) {
        _sql = _db.compileStatement(_INSERTUPLOAD);
        _sql.bindString(1, canvas.getM_canvasId());
        _sql.bindString(2, canvas.getM_companyId() != null ? canvas.getM_companyId() : "null");
        _sql.bindString(3, canvas.getM_Subject() != null ? canvas.getM_Subject() : "null");
        _sql.bindString(4, canvas.getM_VisitBy() != null ? canvas.getM_VisitBy() : "null");
        _sql.bindString(6, canvas.getM_date() != null ? canvas.getM_date() : "null");
        _sql.bindString(17, canvas.getM_text() != null ? canvas.getM_text() : "null");

        return _sql.executeInsert();
    }


    public long insertUploadCanvas(BECanvas canvas) {
        _sql = _db.compileStatement(_INSERTUPLOAD);
        _sql.bindString(1, canvas.getM_canvasId());
        _sql.bindString(2, canvas.getM_companyId() != null ? canvas.getM_companyId() : "null");
        _sql.bindString(3, canvas.getM_Subject() != null ? canvas.getM_Subject() : "null");
        _sql.bindString(4, canvas.getM_VisitBy() != null ? canvas.getM_VisitBy() : "null");
        _sql.bindString(5, canvas.getM_TypeOfVisit() != null ? canvas.getM_TypeOfVisit() : "null");
        _sql.bindString(6, canvas.getM_date() != null ? canvas.getM_date() : "null");
        _sql.bindString(7, canvas.getM_FollowUpDate() != null ? canvas.getM_FollowUpDate() : "null");
        _sql.bindString(8, canvas.getM_FollowUpSalesman() != null ? canvas.getM_FollowUpSalesman() : "null");
        _sql.bindString(9, canvas.getM_Sender() != null ? canvas.getM_Sender() : "null");
        _sql.bindString(10, canvas.getM_ToInternal() != null ? canvas.getM_ToInternal() : "null");
        _sql.bindString(11, canvas.getM_Region() != null ? canvas.getM_Region() : "null");
        _sql.bindString(12, canvas.getM_Country() != null ? canvas.getM_Country() : "null");
        _sql.bindString(13, canvas.getM_TypeOfTransport() != null ? canvas.getM_TypeOfTransport() : "null");
        _sql.bindString(14, canvas.getM_ActivityType() != null ? canvas.getM_ActivityType() : "null");
        _sql.bindString(15, canvas.getM_BusinessArea() != null ? canvas.getM_BusinessArea() : "null");
        _sql.bindString(16, canvas.getM_Office() != null ? canvas.getM_Office() : "null");
        _sql.bindString(17, canvas.getM_text() != null ? canvas.getM_text() : "null");

        return _sql.executeInsert();
    }

    public long insertCanvas(BECanvas canvas) {
        _sql = _db.compileStatement(_INSERTCANVAS);
        _sql.bindString(1, canvas.getM_canvasId());
        _sql.bindString(2, canvas.getM_companyId());
        _sql.bindString(3, canvas.getM_Subject());
        _sql.bindString(4, canvas.getM_VisitBy());
        _sql.bindString(5, canvas.getM_TypeOfVisit());
        _sql.bindString(6, canvas.getM_date());
        _sql.bindString(7, canvas.getM_FollowUpDate());
        _sql.bindString(8, canvas.getM_FollowUpSalesman());
        _sql.bindString(9, canvas.getM_Sender());
        _sql.bindString(10, canvas.getM_ToInternal());
        _sql.bindString(11, canvas.getM_Region());
        _sql.bindString(12, canvas.getM_Country());
        _sql.bindString(13, canvas.getM_TypeOfTransport());
        _sql.bindString(14, canvas.getM_ActivityType());
        _sql.bindString(15, canvas.getM_BusinessArea());
        _sql.bindString(16, canvas.getM_Office());
        _sql.bindString(17, canvas.getM_text());

        return _sql.executeInsert();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany company) {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"CanvasId", "CompanyId", "Subject", "VisitBy", "TypeOfVisit",
                        "Date", "FollowUpDate", "FollowUpSalesman", "Sender", "ToInternal", "Region", "Country",
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text"},
                "CompanyId=?", new String[]{"" + company.getM_companyId()}, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        canvasList.addAll(getCanvasFromUploadTableByCompanyId(company));
        return canvasList;
    }

    private ArrayList<BECanvas> getCanvasFromUploadTableByCompanyId(BECompany company) {
        ArrayList<BECanvas> local = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_UPLOAD,
                new String[]{"CanvasId", "CompanyId", "Subject", "VisitBy", "TypeOfVisit",
                        "Date", "FollowUpDate", "FollowUpSalesman", "Sender", "ToInternal", "Region", "Country",
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text"},
                "CompanyId=?", new String[]{"" + company.getM_companyId()}, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                local.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return local;
    }

    public ArrayList<BECanvas> getAllCanvasFromUploadTable() {
        ArrayList<BECanvas> local = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_UPLOAD,
                new String[]{"CanvasId", "CompanyId", "Subject", "VisitBy", "TypeOfVisit",
                        "Date", "FollowUpDate", "FollowUpSalesman", "Sender", "ToInternal", "Region", "Country",
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                local.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return local;
    }

    public ArrayList<BECanvas> getAllCanvasFromDevice() {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"CanvasId", "CompanyId", "Subject", "VisitBy", "TypeOfVisit",
                        "Date", "FollowUpDate", "FollowUpSalesman", "Sender", "ToInternal", "Region", "Country",
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return canvasList;
    }

    public void postCanvasJson(BECanvas canvas) {

        try {
            obj = new JSONObject(gson.toJson(canvas));
            obj.remove("m_canvasId");  // fix da den sender noget med der ikke skal bruges
            Log.v("tostring", obj.toString());

        } catch (JSONException e) {
            Log.e("PostCanvasJson", "Error");
        }

        RequestQueue queue = Volley.newRequestQueue(_activity);
        String urlEncoded = null;
        try {
            urlEncoded = URLEncoder.encode(obj.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://skynet.bws.dk/Applications/smsAndroid.nsf/CreateJson?OpenAgent&DATA=" + urlEncoded;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("ASDA", "Response is: " + response);
                        int iee = 1;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ASDA", "That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void deleteAllCanvas() {
//        _db.execSQL("DELETE FROM " + DAConstants.TABLE_CANVAS + " WHERE Id != " + 105600);
        _db.delete(DAConstants.TABLE_CANVAS, null, null);
    }

    public void deleteAllCanvasFromUpload() {
        _db.delete(DAConstants.TABLE_UPLOAD, null, null);
    }
}
