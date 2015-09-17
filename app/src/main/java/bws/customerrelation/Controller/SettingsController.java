package bws.customerrelation.Controller;

import android.app.Activity;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOCanvas;
import bws.customerrelation.DAL.DAOCompany;
import bws.customerrelation.DAL.DAOSettings;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jaje on 17-Sep-15.
 */
public class SettingsController {

    DAOSettings _daoSettings;
    Activity _activity;
    private static SettingsController instance = null;
    static ArrayList<String> _settingsList;

    private SettingsController(Activity activity) {
        _activity = activity;
        _daoSettings = new DAOSettings(_activity);
        getAllSettingsFromAPI();
    }

    private void getAllSettingsFromAPI() {
        _daoSettings.getJSONListOfActivity();
    }

    public static SettingsController getInstance(Activity context) {
        if (instance == null) {
            instance = new SettingsController(context);
        }
        return instance;
    }

    public static void setSettingsList(ArrayList<String> list) {
        _settingsList = list;
    }
}
