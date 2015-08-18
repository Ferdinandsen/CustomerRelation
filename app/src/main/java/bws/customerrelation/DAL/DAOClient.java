package bws.customerrelation.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BEClient;
import bws.customerrelation.Model.BEUser;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class DAOClient {

    Context _context;
    SQLiteDatabase _db;
    SQLiteStatement _sql;

    String _INSERT = "INSERT INTO " + DAConstants.TABLE_CLIENT + "(Firstname, Lastname, Email, Password, Company, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";

    public DAOClient(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(_context);
        _db = openHelper.getWritableDatabase();
    }

    public long insert(BEClient client) {
        _sql = _db.compileStatement(_INSERT);
        _sql.bindString(1, client.getFirstName());
        _sql.bindString(2, client.getLastName());
        _sql.bindString(3, client.getEmail());
        _sql.bindString(4, client.getPassword());
        _sql.bindString(5, client.getCompany());
        _sql.bindString(6, "" + client.getPhoneNumber());
        return _sql.executeInsert();
    }

    public ArrayList<BEClient> getAllClients() {
        ArrayList<BEClient> clients = new ArrayList<BEClient>();
        Cursor cursor = _db.query(DAConstants.TABLE_CLIENT,
                new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "Company", "PhoneNumber"},
                null, null, null, null,
                "Firstname desc");
        if (cursor.moveToFirst()) {
            do {
                clients.add(new BEClient(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }

    public void deleteAllClients() {
        _db.execSQL("DELETE FROM " + DAConstants.TABLE_CLIENT + " WHERE ID != " + 100000);
    }
    //Hent en specifik user's, Client liste ud fra AD
//    public ArrayList<BEClient> getUsersClients(BEUser user) {
//
//        ArrayList<BEClient> clients = new ArrayList<BEClient>();
//        Cursor cursor = _db.query( //INDSÆT DET KORREKTE );
//        if (cursor.moveToFirst()) {
//            clients.add(new BEClient(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
//        }
//        if (cursor != null && !cursor.isClosed()) {
//            cursor.close();
//        }
//        return clients;
//    }
}
