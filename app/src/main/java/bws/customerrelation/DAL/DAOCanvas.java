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
import java.util.concurrent.ExecutionException;

import bws.customerrelation.Controller.CanvasController;
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

    //DL TODO - add contact info!
    String _INSERTCANVAS = "INSERT INTO " + DAConstants.TABLE_CANVAS + "(CanvasId, CompanyId, Subject, VisitBy, " +
            "TypeOfVisit, Date, FollowUpDate, FollowUpSalesman, " +
            "Sender, ToInternal, Region, Country, TypeOfTransport, " +
            "ActivityType, BusinessArea, Office, Text, Con1, Con1P, Con1M, Con1F, Con1E, Con1D, Con2," +
            "Con2P, Con2M, Con3, Con3P, Con3M, Con4, Con4P, Con4M) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String _INSERTUPLOAD = "INSERT INTO " + DAConstants.TABLE_UPLOAD + "(CanvasId, CompanyId, Subject, VisitBy, " +
            "TypeOfVisit, Date, FollowUpDate, FollowUpSalesman, " +
            "Sender, ToInternal, Region, Country, TypeOfTransport, " +
            "ActivityType, BusinessArea, Office, Text, Con1, Con1P, Con1M, Con1F, Con1E, Con1D, Con2," +
            "Con2P, Con2M, Con3, Con3P, Con3M, Con4, Con4P, Con4M) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public DAOCanvas(Activity activity) {
        _activity = activity;
        OpenHelper openHelper = new OpenHelper(_activity);
        _db = openHelper.getWritableDatabase();
        gson = new Gson();
    }

    /**
     * KONVERTER JSON OG ADD TIL ARRAYLIST
     */
    public void getJSONCanvas() {
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

    //  TODO extract to class
    private ArrayList<BECanvas> convertFromJsonToBE(JSONObject object) throws JSONException {
        JSONObject obj;
        JSONArray obj1;
        JSONArray jsonArray = (JSONArray) object.get("viewentry");

        ArrayList<BECanvas> mList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            obj = jsonArray.getJSONObject(i);
            obj1 = obj.getJSONArray("entrydata");
            mList.add(setBECanvas(obj1));
        }
        return mList;
    }

    private BECanvas setBECanvas(JSONArray array) throws JSONException {
        String canvasId, companyId, TypeOfVisit, VisitBy,
                Subject, Date, FollowUpBy, FollowUpDate,
                Sender, ToInternal, Region, Country,
                TypeOfTransport, Activity, BusinessArea, Office, Text, con1, con1P, con1M, con1F, con1E, con1D, con2,
                con2P, con2M, con3, con3P, con3M, con4, con4P, con4M;

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
        Date = array1.get(0);
        canvasId = array1.get(1);
        companyId = array1.get(2);
        Subject = array1.get(3);
        VisitBy = array1.get(4);
        TypeOfVisit = array1.get(5);
        FollowUpDate = array1.get(6);
        FollowUpBy = array1.get(7);
        Sender = array1.get(8);
        ToInternal = array1.get(9);
        Region = array1.get(10);
        Country = array1.get(11);
        TypeOfTransport = array1.get(12);
        Activity = array1.get(13);
        BusinessArea = array1.get(14);
        Office = array1.get(15);
        Text = "pre entry";

        con1 = array1.get(16);
        con1P = array1.get(17);
        con1M = array1.get(18);
        con1F = array1.get(19);
        con1D = array1.get(20);
        con1E = array1.get(21);
        con2 = array1.get(22);
        con2P = array1.get(23);
        con2M = array1.get(24);
        con3 = array1.get(25);
        con3P = array1.get(26);
        con3M = array1.get(27);
        con4 = array1.get(28);
        con4P = array1.get(29);
        con4M = array1.get(30);

        BECanvas canvas = new BECanvas(canvasId, companyId, Subject, VisitBy, TypeOfVisit, Date, FollowUpDate, FollowUpBy,
                Sender, ToInternal, Region, Country,
                TypeOfTransport, Activity, BusinessArea, Office, Text, con1, con1P, con1M, con1F, con1E, con1D, con2,
                con2P, con2M, con3, con3P, con3M, con4, con4P, con4M);
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
            return "Error get rich interrupted";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Error get rich execution";
        }
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
//        _sql.bindString(18, canvas.getM_mainContact());
//        _sql.bindString(19, canvas.getM_mainContactPhone());
//        _sql.bindString(20, canvas.getM_mainContactMobile());
//        _sql.bindString(21, canvas.getM_mainContactFax());
//        _sql.bindString(22, canvas.getM_mainContactEmail());
//        _sql.bindString(23, canvas.getM_mainContactDivision());
//        _sql.bindString(24, canvas.getM_secondContact());
//        _sql.bindString(25, canvas.getM_secondContactPhone());
//        _sql.bindString(26, canvas.getM_secondContactMobile());
//        _sql.bindString(27, canvas.getM_thirdContact());
//        _sql.bindString(28, canvas.getM_thirdContactPhone());
//        _sql.bindString(29, canvas.getM_thirdContactMobile());
//        _sql.bindString(30, canvas.getM_fourthContact());
//        _sql.bindString(31, canvas.getM_fourthContactPhone());
//        _sql.bindString(32, canvas.getM_fourthContactMobile());

        return _sql.executeInsert();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany company) {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"CanvasId", "CompanyId", "Subject", "VisitBy", "TypeOfVisit",
                        "Date", "FollowUpDate", "FollowUpSalesman", "Sender", "ToInternal", "Region", "Country",
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text", "Con1", "Con1P", "Con1M", "Con1F", "Con1E", "Con1D", "Con2",
                        "Con2P", "Con2M", "Con3", "Con3P", "Con3M", "Con4", "Con4P", "Con4M"},
                "CompanyId=?", new String[]{"" + company.getM_companyId()}, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16),
                        cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22),
                        cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28),
                        cursor.getString(29), cursor.getString(30), cursor.getString(31)));
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
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text", "Con1", "Con1P", "Con1M", "Con1F", "Con1E", "Con1D", "Con2",
                        "Con2P", "Con2M", "Con3", "Con3P", "Con3M", "Con4", "Con4P", "Con4M"},
                "CompanyId=?", new String[]{"" + company.getM_companyId()}, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                local.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16),
                        cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22),
                        cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28),
                        cursor.getString(29), cursor.getString(30), cursor.getString(31)));
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
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text", "Con1", "Con1P", "Con1M", "Con1F", "Con1E", "Con1D", "Con2",
                        "Con2P", "Con2M", "Con3", "Con3P", "Con3M", "Con4", "Con4P", "Con4M"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                local.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16),
                        cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22),
                        cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28),
                        cursor.getString(29), cursor.getString(30), cursor.getString(31)));
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
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "Text", "Con1", "Con1P", "Con1M", "Con1F", "Con1E", "Con1D", "Con2",
                        "Con2P", "Con2M", "Con3", "Con3P", "Con3M", "Con4", "Con4P", "Con4M"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16),
                        cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22),
                        cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28),
                        cursor.getString(29), cursor.getString(30), cursor.getString(31)));
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
