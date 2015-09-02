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

        db.execSQL("CREATE TABLE " + DAConstants.TABLE_CANVAS + "(CompanyId TEXT, CanvasID TEXT PRIMARY KEY,Subject TEXT, " +
                "VisitBy TEXT, TypeOfVisit TEXT, Date TEXT, FollowUpDate TEXT, FollowUpSalesman TEXT, Sender TEXT, " +
                "ToInternal TEXT, Region TEXT, Country TEXT, TypeOfTransport TEXT, ActivityType TEXT, " +
                "BusinessArea TEXT, Office TEXT, Text TEXT)");

//        db.execSQL("CREATE TABLE " + DAConstants.TABLE_MESSAGE + "(Id INTEGER PRIMARY KEY, UserFrom INTEGER, UserTo INTEGER, Message TEXT, Date_Created DATETIME)");
//        db.execSQL("CREATE TABLE " + DAConstants.TABLE_REQUEST + "(Id INTEGER PRIMARY KEY, UserFrom INTEGER, UserTo INTEGER, RequestMessage TEXT, Date_Created DATETIME)");
//        db.execSQL("CREATE TABLE " + DAConstants.TABLE_COMMENT + "(Id INTEGER PRIMARY KEY, PostId INTEGER, UserId INTEGER, Message TEXT, Date_Created DATETIME)");
//        db.execSQL("CREATE TABLE " + DAConstants.TABLE_POST_LIKE + "(PostId INTEGER, UserId INTEGER, PRIMARY KEY (PostId, UserId))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_CANVAS);
//        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_MESSAGE);
//        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_REQUEST);
//        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_COMMENT);
//        db.execSQL("DROP TABLE IF EXISTS " + DAConstants.TABLE_POST_LIKE);

        onCreate(db);
    }
}
