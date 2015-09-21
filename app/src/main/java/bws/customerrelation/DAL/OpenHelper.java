package bws.customerrelation.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    public OpenHelper(Context context) {
        super(context, DAConstants.DATABASE_NAME, null, DAConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_USER + " (Id INTEGER PRIMARY KEY, Firstname TEXT, Lastname TEXT, Email TEXT, Password TEXT, PhoneNumber INTEGER)");

        db.execSQL("CREATE TABLE " + DAConstants.TABLE_COMPANY + "(CompanyId TEXT PRIMARY KEY, CompanyName TEXT, Address TEXT," +
                "City TEXT, Zip TEXT, Country TEXT, Phone TEXT, Fax TEXT, " +
                "Email TEXT, SeNo TEXT, SalesArea TEXT, BusinessRelation TEXT, " +
                "CompanyGroup TEXT, CompanyClosed TEXT, CompanyHomepage TEXT )");

        db.execSQL("CREATE TABLE " + DAConstants.TABLE_CANVAS + "(CompanyId TEXT, CanvasId TEXT PRIMARY KEY, Subject TEXT, " +
                "VisitBy TEXT, TypeOfVisit TEXT, Date TEXT, FollowUpDate TEXT, FollowUpSalesman TEXT, Sender TEXT, " +
                "ToInternal TEXT, Region TEXT, Country TEXT, TypeOfTransport TEXT, ActivityType TEXT, " +
                "BusinessArea TEXT, Office TEXT, Text TEXT)");

        db.execSQL("CREATE TABLE " + DAConstants.TABLE_UPLOAD + "(CompanyId TEXT, CanvasId TEXT PRIMARY KEY, Subject TEXT, " +
                "VisitBy TEXT, TypeOfVisit TEXT, Date TEXT, FollowUpDate TEXT, FollowUpSalesman TEXT, Sender TEXT, " +
                "ToInternal TEXT, Region TEXT, Country TEXT, TypeOfTransport TEXT, ActivityType TEXT, " +
                "BusinessArea TEXT, Office TEXT, Text TEXT)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_COUNTRY + "(Name TEXT PRIMARY KEY, Region TEXT, CountryCode TEXT, PhonePrefix TEXT)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_ACTIVE + "(Name TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_ACTIVITYTYPE + "(Name TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_BUSINESSAREA + "(Name TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_BUSINESSRELATION + "(Name TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_OFFICE + "(Name TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_TYPEOFTRANSPORT + "(Name TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + DAConstants.TABLE_TYPEOFVISIT + "(Name TEXT PRIMARY KEY)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_CANVAS);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_UPLOAD);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_ACTIVE);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_ACTIVITYTYPE);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_BUSINESSAREA);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_BUSINESSRELATION);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_OFFICE);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_TYPEOFTRANSPORT);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_TYPEOFVISIT);

        onCreate(db);
    }
}
