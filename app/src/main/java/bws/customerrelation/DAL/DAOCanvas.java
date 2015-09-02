package bws.customerrelation.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bws.customerrelation.DAL.Gateway.GetJSONFromAPI;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jaje on 01-Sep-15.
 */
public class DAOCanvas {

    Context _context;
    SQLiteDatabase _db;
    SQLiteStatement _sql;

    //DL
    String _INSERTCANVAS = "INSERT INTO " + DAConstants.TABLE_CANVAS + "( CompanyId, CanvasID, Subject, VisitBy, " +
            "TypeOfVisit, Date, FollowUpDate, FollowUpSalesman, " +
            "Sender, ToInternal, Region, Country, TypeOfTransport, " +
            "ActivityType, BusinessArea, Office, Text) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public DAOCanvas(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(_context);
        _db = openHelper.getWritableDatabase();
    }

    public ArrayList<BECanvas> getAllCanvasFromAPI() {
        ArrayList<BECanvas> companyList = new ArrayList<>();
        String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/(CanvasByCompany)?readviewentries&outputformat=json&start=1&count=10&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        JSONObject obj;
        GetJSONFromAPI api = new GetJSONFromAPI();
        api.execute(URL);

        try {
            obj = api.get();
            companyList = ConvertFromJsonToBE(obj);
        } catch (Exception e) {
            Log.e("Api get", "Error when trying to connect to api", e);
        }
        return companyList;
    }

    //KONVERTER JSON OG ADD TIL ARRAYLIST
    private ArrayList<BECanvas> ConvertFromJsonToBE(JSONObject object) throws JSONException {
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
                Subject, date, FollowUpBy, FollowUpDate,
                From, ToInternal, Region, Country,
                TypeOfTransport, Activity, BusinessArea, Office, text;

        ArrayList<String> array1 = new ArrayList<>();
        for (int y = 0; y < array.length(); y++) {


            if (!array.getJSONObject(y).isNull("text")) {
                array1.add(array.getJSONObject(y).getJSONObject("text").getString("0"));
            } else if (!array.getJSONObject(y).isNull("textlist")) {
                JSONArray ara = array.getJSONObject(y).getJSONObject("textlist").getJSONArray("text");

                String res = "";
                //STRINGBUILDER ISTEDET!!!
                for (int i = 0; i < ara.length(); i++) {
                    res += ara.getJSONObject(i).getString("0");
                }
                array1.add(res);
            } else if (!array.getJSONObject(y).isNull("datetime")) {
                array1.add(array.getJSONObject(y).getJSONObject("datetime").getString("0"));
            }
        }

        companyId = (String) array1.get(0);
        canvasId = array1.get(1);
        Subject = (String) array1.get(2);
        VisitBy = (String) array1.get(3);
        TypeOfVisit = (String) array1.get(4);
        date = (String) array1.get(5);
        FollowUpDate = (String) array1.get(6);
        FollowUpBy = (String) array1.get(7);
        From = (String) array1.get(8);
        ToInternal = (String) array1.get(9);
        Region = (String) array1.get(10);
        Country = (String) array1.get(11);
        TypeOfTransport = (String) array1.get(12);
        Activity = (String) array1.get(13);
        BusinessArea = (String) array1.get(14);
        Office = (String) array1.get(15);
        text = (String) array1.get(16);

        BECanvas canvas = new BECanvas(companyId, canvasId, Subject, VisitBy, TypeOfVisit, date, FollowUpDate, FollowUpBy,
                From, ToInternal, Region, Country,
                TypeOfTransport, Activity, BusinessArea, Office, text);
        return canvas;
    }

    public long insertCanvas(BECanvas canvas) {
        _sql = _db.compileStatement(_INSERTCANVAS);
        _sql.bindString(0, canvas.getM_companyId());
        _sql.bindString(1, canvas.getM_canvasId());
        _sql.bindString(2, canvas.getM_Subject());
        _sql.bindString(3, canvas.getM_VisitBy());
        _sql.bindString(4, canvas.getM_TypeOfVisit());
        _sql.bindString(5, canvas.getM_date());
        _sql.bindString(6, canvas.getM_FollowUpDate());
        _sql.bindString(7, canvas.getM_FollowUpSalesman());
        _sql.bindString(8, canvas.getM_Sender());
        _sql.bindString(9, canvas.getM_ToInternal());
        _sql.bindString(10, canvas.getM_Region());
        _sql.bindString(11, canvas.getM_Country());
        _sql.bindString(12, canvas.getM_TypeOfTransport());
        _sql.bindString(13, canvas.getM_ActivityType());
        _sql.bindString(14, canvas.getM_BusinessArea());
        _sql.bindString(15, canvas.getM_Office());
        _sql.bindString(16, canvas.getM_text());

        return _sql.executeInsert();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany client) {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"Id", "ClientId", "Canvas"},
                "ClientId=?", new String[]{"" + client.getM_companyId()}, null, null,
                "Id desc");
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return canvasList;
    }

    public ArrayList<BECanvas> getAllCanvasFromDevice() {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"companyId", "canvasId", "Subject", "VisitBy", "TypeOfVisit",
                        "date", "FollowUpDate", "FollowUpSalesman", "Sender", "ToInternal", "Region", "Country",
                        "TypeOfTransport", "ActivityType", "BusinessArea", "Office", "text"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return canvasList;
    }
}
