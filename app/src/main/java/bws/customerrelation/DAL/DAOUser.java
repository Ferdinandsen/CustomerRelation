package bws.customerrelation.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import bws.customerrelation.Model.BEUser;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class DAOUser {

    Context _context;
    SQLiteDatabase _db;
    SQLiteStatement _sql;

    String _INSERT = "INSERT INTO " + DAConstants.TABLE_USER + "(Firstname, Lastname, Email, Password, PhoneNumber) VALUES (?, ?, ?, ?, ?)";

    public DAOUser(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(_context);
        _db = openHelper.getWritableDatabase();
    }

    public long insert(BEUser user) {
        _sql = _db.compileStatement(_INSERT);
        _sql.bindString(1, user.getFirstname());
        _sql.bindString(2, user.getLastname());
        _sql.bindString(3, user.getEmail());
        _sql.bindString(4, user.getPassword());
        _sql.bindString(5, "" + user.getPhoneNumber());
        return _sql.executeInsert();
    }

    public BEUser getUserByCredentials(String email, String password) {
        BEUser localUser = null;
        Cursor cursor = _db.query(DAConstants.TABLE_USER, new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "PhoneNumber"}, "Email=? and Password=?", new String[]{"" + email, "" + password}, null, null, null);
        if (cursor.moveToFirst()) {
            localUser = new BEUser(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(6));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return localUser;
    }

    public ArrayList<BEUser> getAllUsers() {
        ArrayList<BEUser> users = new ArrayList<BEUser>();
        Cursor cursor = _db.query(DAConstants.TABLE_USER, new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "PhoneNumber"}, null, null, null, null, "Firstname desc");
        if (cursor.moveToFirst()) {
            do {
                users.add(new BEUser(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return users;
    }
}
