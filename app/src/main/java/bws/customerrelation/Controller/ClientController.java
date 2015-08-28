package bws.customerrelation.Controller;

import android.app.Activity;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOCompany;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jacob Ferdinandsen on 13-08-2015.
 */
public class ClientController {
    DAOCompany _daoCompany;
    Activity _activity;

    public ClientController(Activity context) {
        _activity = context;
        _daoCompany = new DAOCompany(_activity);

    }

//    public void createDummyClients() {
//        int test = _daoCompany.getAllClients().size();
//        if (_daoCompany.getAllClients().size() < 10) {
//            _daoCompany.insert(new BECompany("André", "Psy", "SejerejekillerxX@live.dk", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Thomas", "Petersen", "PsykoMegetOverklar@msn.com", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Bob", "Olesen", "bobolesen@hotmail.com", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Kevin", "Ørskov", "yoyo@live.dk", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Jacob", "Jakobsen", "feedthehorse@yumyum.com", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Trine", "Hansen", "thansen@fakta.com", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Anne", "Dahl", "adahl@gmail.com", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Thue", "Emilsen", "Volume@onmypc.com", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Mette", "Enevoldsen", "menevold@live.dk", "hej123", "BWS", 0));
//            _daoCompany.insert(new BECompany("Kasper", "Juul", "kjuu@live.dk", "hej123", "BWS", 0));
//        }
//    }

    public ArrayList<BECompany> getAllClients() {
        return _daoCompany.getAllClients();
    }

    public ArrayList<BECompany> getCompanyFromApi() {
        return _daoCompany.getCompanyFromApi();
    }

    public void deleteAllClients() {
        _daoCompany.deleteAllClients();
    }

    public void createClientList(ArrayList<BECompany> clients) {
        ArrayList<BECompany> fuck = new ArrayList<BECompany>();
        for (BECompany clie : clients) {
            fuck.add(clie);
        }
        ArrayList<BECompany> test = getAllClientsFromDevice();
        ArrayList<BECompany> test1 = new ArrayList<>();

        if (test.isEmpty()) {
            for (BECompany x : fuck) {
                _daoCompany.insertClientOnList(x);
            }
        } else {
            for (BECompany x : fuck) {
                for (BECompany y : test) {
                    if (x.getM_CompanyId() == y.getM_CompanyId())
                        test1.add(x);
                }
            }
            for (BECompany x : test1)
                fuck.remove(x);

            for (BECompany x : fuck) {
                _daoCompany.insertClientOnList(x);
            }
        }
    }

    public ArrayList<BECompany> getAllClientsFromDevice() {
        return _daoCompany.getAllClientsFromDevice();
    }

    public long saveCanvas(String canvas, BECompany client) {
        return _daoCompany.insertCanvas(canvas, client);
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany client) {
        return _daoCompany.getAllCanvasByClientId(client);
    }
}
