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
import bws.customerrelation.DAL.Gateway.SoapHelper;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class DAOCompany {

    Context _context;
    SQLiteDatabase _db;
    SQLiteStatement _sql;
    SoapHelper soapHelper;
    //Dummy use
    String _INSERT = "INSERT INTO " + DAConstants.TABLE_CLIENT + "(Firstname, Lastname, Email, Password, Company, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
    //DL
    String _INSERTCLIENT = "INSERT INTO " + DAConstants.TABLE_CLIENTLIST + "(Id, CompanyName, Address, City, Zip, Country, Phone, Fax, Email, SeNo, " +
            "SalesArea, BusinessRelation, CompanyGroup, CompanyClosed, companyHomepage) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String _INSERTCANVAS = "INSERT INTO " + DAConstants.TABLE_CANVAS + "(ClientID, Canvas) VALUES (? ,?)";

    public DAOCompany(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(_context);
        _db = openHelper.getWritableDatabase();
    }

//    public long insert(BECompany client) {
//        _sql = _db.compileStatement(_INSERT);
//        _sql.bindString(1, client.getFirstName());
//        _sql.bindString(2, client.getLastName());
//        _sql.bindString(3, client.getEmail());
//        _sql.bindString(4, client.getPassword());
//        _sql.bindString(5, client.getCompany());
//        _sql.bindString(6, "" + client.getPhoneNumber());
//        return _sql.executeInsert();
//    }

    public ArrayList<BECompany> getCompanyFromApi() {
        ArrayList<BECompany> companyList = new ArrayList<>();
        String URL = "http://skynet.bws.dk/Applications/smsAndroid.nsf/LookupCompanyNameAndUNID?readviewentries&outputformat=json&start=1&count=10&restrict=2C7EFD49ADD61732C1256C2C002FEF71#";
        JSONArray obj;
        GetJSONFromAPI api = new GetJSONFromAPI();
        api.execute(URL);

        try {
//            soapHelper = new SoapHelper();
//            soapHelper.execute();
            obj = api.get();
            companyList = ConvertFromJsonToBE(obj);
        } catch (Exception e) {
            Log.e("Api get", "Error when trying to connect to api", e);
        }
        return companyList;
    }

    //KONVERTER JSON OG ADD TIL ARRAYLIST
    private ArrayList<BECompany> ConvertFromJsonToBE(JSONArray array) throws JSONException {
        JSONObject obj = new JSONObject();
        JSONArray obj1 = new JSONArray();

        ArrayList<BECompany> mList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            obj = array.getJSONObject(i);
            obj1 = obj.getJSONArray("entrydata");
            mList.add(setCompanyBE(obj1));
        }
        return mList;
    }

    private BECompany setCompanyBE(JSONArray array) throws JSONException {
        int id;
        String zip, country, phone, fax, email, seNo, salesArea, businessRelation, companyGroup,
                companyName, address, city, companyHomepage;
        Boolean companyClosed;

        JSONArray array1 = new JSONArray();
        for (int y = 0; y < array.length(); y++) {
            array1.put(array.getJSONObject(y).getJSONObject("text").toString());
        }

        id = Integer.parseInt(array1.get(0).toString());
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
        companyClosed = (Boolean) array1.get(13);
        companyHomepage = (String) array1.get(14);

        BECompany company = new BECompany(id, companyName, address, city, zip, country, phone, fax, email, seNo, salesArea, businessRelation, companyGroup, companyClosed,
                companyHomepage);
        return company;
    }

    public ArrayList<BECompany> getAllClients() {
        ArrayList<BECompany> clients = new ArrayList<BECompany>();
        Cursor cursor = _db.query(DAConstants.TABLE_CLIENT,
                new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "Company", "PhoneNumber"},
                null, null, null, null,
                "Id desc");
        if (cursor.moveToFirst()) {
            do {
                clients.add(new BECompany(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }

    public ArrayList<BECompany> getAllClientsFromDevice() {
        ArrayList<BECompany> clients = new ArrayList<BECompany>();
        Cursor cursor = _db.query(DAConstants.TABLE_CLIENTLIST,
                new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "Company", "PhoneNumber"},
                null, null, null, null,
                "Firstname desc");
        if (cursor.moveToFirst()) {
            do {
                clients.add(new BECompany(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }

    public void deleteAllClients() {
        _db.execSQL("DELETE FROM " + DAConstants.TABLE_CLIENTLIST + " WHERE ID != " + 100000);

    }

    public long insertClientOnList(BECompany client) {
        _sql = _db.compileStatement(_INSERTCLIENT);
        _sql.bindString(1, "" + client.getM_id());
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

    public long insertCanvas(String canvas, BECompany client) {
        _sql = _db.compileStatement(_INSERTCANVAS);
        _sql.bindString(1, "" + client.getM_id());
        _sql.bindString(2, canvas);
        return _sql.executeInsert();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany client) {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"Id", "ClientId", "Canvas"},
                "ClientId=?", new String[]{"" + client.getM_id()}, null, null,
                "Id desc");
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return canvasList;
    }
    //Hent en specifik user's, Client liste ud fra AD
//    public ArrayList<BECompany> getUsersClients(BEUser user) {
//
//        ArrayList<BECompany> clients = new ArrayList<BECompany>();
//        Cursor cursor = _db.query( //INDSÆT DET KORREKTE );
//        if (cursor.moveToFirst()) {
//            clients.add(new BECompany(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
//        }
//        if (cursor != null && !cursor.isClosed()) {
//            cursor.close();
//        }
//        return clients;
//    }
}

