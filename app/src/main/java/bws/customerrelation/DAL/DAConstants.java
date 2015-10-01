package bws.customerrelation.DAL;

import java.util.ArrayList;
import java.util.Arrays;

public class DAConstants {
    public static String DATABASE_NAME = "sqlite1.db";
    public static int DATABASE_VERSION = 13;

    public static String TABLE_USER = "tb_user";
    public static String TABLE_COMPANY = "tb_company";
    public static String TABLE_CANVAS = "tb_canvas";
    public static String TABLE_UPLOAD = "tb_upload";
    public static final String TABLE_COUNTRY = "tb_country";
    public static final String TABLE_ACTIVE = "tb_active";
    public static final String TABLE_ACTIVITYTYPE = "tb_Activity_type";
    public static final String TABLE_BUSINESSAREA = "tb_business_area";
    public static final String TABLE_BUSINESSRELATION = "tb_business_relation";
    public static final String TABLE_TYPEOFTRANSPORT = "tb_type_of_transport";
    public static final String TABLE_TYPEOFVISIT = "tb_type_of_visit";
    public static final String TABLE_OFFICE = "tb_office";
    public static final String TABLE_CONTACTS = "tb_contacts";

    public static ArrayList<String> TABLEPACK = new ArrayList<>(Arrays.asList(TABLE_ACTIVE, TABLE_ACTIVITYTYPE, TABLE_BUSINESSAREA, TABLE_BUSINESSRELATION, TABLE_TYPEOFTRANSPORT, TABLE_TYPEOFVISIT, TABLE_OFFICE));
}
