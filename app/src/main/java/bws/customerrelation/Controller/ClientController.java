package bws.customerrelation.Controller;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOCanvas;
import bws.customerrelation.DAL.DAOCompany;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jacob Ferdinandsen on 13-08-2015.
 */
public class ClientController {
    DAOCompany _daoCompany;
    DAOCanvas _daoCanvas;
    Activity _activity;
    ArrayList<BECompany> _cacheList;

    public ClientController(Activity context) {
        _activity = context;
        _daoCompany = new DAOCompany(_activity);
        _daoCanvas = new DAOCanvas(_activity);
        _cacheList = getCompanyFromApi();
    }

    public ArrayList<BECompany> getCompanyFromApi() {
        return _daoCompany.getCompanyFromApi();
    }

    public void deleteAllClients() {
        _daoCompany.deleteAllClients();
    }

    public void createCompanyList(ArrayList<BECompany> clients) {
        ArrayList<BECompany> localCompanies = new ArrayList<BECompany>();
        for (BECompany clie : clients) {
            localCompanies.add(clie);
        }
        ArrayList<BECompany> deviceCompanies = getAllClientsFromDevice();
        ArrayList<BECompany> test1 = new ArrayList<>();

        if (deviceCompanies.isEmpty()) {
            for (BECompany x : localCompanies) {
                _daoCompany.insertCompanyOnDevice(x);
            }
        } else {
            for (BECompany x : localCompanies) {
                for (BECompany y : deviceCompanies) {
                    if (x.getM_companyId().equals(y.getM_companyId())) {
                        test1.add(x);
                    }
                }
            }
            for (BECompany x : test1)
                localCompanies.remove(x);

            for (BECompany x : localCompanies) {
                _daoCompany.insertCompanyOnDevice(x);
            }
        }
    }

    public ArrayList<BECompany> getAllClientsFromDevice() {
        return _daoCompany.getAllClientsFromDevice();
    }

    public ArrayList<BECompany> getCompaniesByInput(String s) {

        ArrayList<BECompany> matchedCompanies = new ArrayList<>();
        for (BECompany c : _cacheList) {
            if (c.getM_companyName().toLowerCase().contains(s) || c.getM_seNo().toLowerCase().contains(s)) {
                matchedCompanies.add(c);
            }
        }
        return matchedCompanies;
    }
}
