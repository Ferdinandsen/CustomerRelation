package bws.customerrelation.Controller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import bws.customerrelation.DAL.DAOSettings;
import bws.customerrelation.Model.BECountry;

/**
 * Created by Jaje on 17-Sep-15.
 */
public class SettingsController {

    DAOSettings _daoSettings;
    Activity _activity;
    private static SettingsController instance = null;

    HashMap<String, ArrayList<String>> stringList;

    public static ArrayList<BECountry> countryList;

    private SettingsController(Activity activity) {
        _activity = activity;
        _daoSettings = new DAOSettings(_activity);
        getAllSettingsFromAPI();
    }

    private void getAllSettingsFromAPI() {
        if (_daoSettings.getAllSettingsFromDevice().isEmpty()) {
            _daoSettings.getJSONListOfActivity();
            _daoSettings.getJSONCountryList();
        }
    }

    public static SettingsController getInstance(Activity context) {
        if (instance == null) {
            instance = new SettingsController(context);
        }
        return instance;
    }

    public ArrayList<String> populateSettingsLists(String s) {
        stringList = _daoSettings.getAllSettingsFromDevice();
        switch (s) {
            case SharedConstants.ACTIVE:
                return stringList.get(s);
            case SharedConstants.ACTIVITY:
                return stringList.get(s);
            case SharedConstants.BUSINESSAREA:
                return stringList.get(s);
            case SharedConstants.BUSINESSRELATION:
                return stringList.get(s);
            case SharedConstants.OFFICE:
                return stringList.get(s);
            case SharedConstants.TRANSPORTTYPE:
                return stringList.get(s);
            case SharedConstants.VISITTYPE:
                return stringList.get(s);
            default:
                return null;
        }
    }
}

