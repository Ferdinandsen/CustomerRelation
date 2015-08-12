package bws.customerrelation.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import bws.customerrelation.Model.BEClient;
import bws.customerrelation.Model.BEUser;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class DAOClient {

    Context _context;
    SQLiteDatabase _db;
//    SQLiteStatement _sql;

    public DAOClient(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(_context);
        _db = openHelper.getWritableDatabase();
    }

    //Hent en specifik user's, Client liste ud fra AD
    public ArrayList<BEClient> getUsersClients(BEUser user) {

        ArrayList<BEClient> clients = new ArrayList<BEClient>();
//       Cursor cursor = _db.query( //INDSÆT DET KORREKTE );
        if (cursor.moveToFirst()) {
            clients.add(new BEClient(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }
}

