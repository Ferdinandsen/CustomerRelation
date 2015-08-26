package bws.customerrelation.Controller;

import android.app.Activity;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOClient;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jacob Ferdinandsen on 13-08-2015.
 */
public class ClientController {
    DAOClient _daoClient;
    Activity _activity;

    public ClientController(Activity context) {
        _activity = context;
        _daoClient = new DAOClient(_activity);

    }

    public void createDummyClients() {
        int test = _daoClient.getAllClients().size();
        if (_daoClient.getAllClients().size() < 10) {
            _daoClient.insert(new BECompany("André", "Psy", "SejerejekillerxX@live.dk", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Thomas", "Petersen", "PsykoMegetOverklar@msn.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Bob", "Olesen", "bobolesen@hotmail.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Kevin", "Ørskov", "yoyo@live.dk", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Jacob", "Jakobsen", "feedthehorse@yumyum.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Trine", "Hansen", "thansen@fakta.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Anne", "Dahl", "adahl@gmail.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Thue", "Emilsen", "Volume@onmypc.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Mette", "Enevoldsen", "menevold@live.dk", "hej123", "BWS", 0,false));
            _daoClient.insert(new BECompany("Kasper", "Juul", "kjuu@live.dk", "hej123", "BWS", 0,false));
        }
    }

    public ArrayList<BECompany> getAllClients() {
        return _daoClient.getAllClients();
    }

    public void deleteAllClients() {
        _daoClient.deleteAllClients();
    }

    public void createClientList(ArrayList<BECompany> clients) {
        ArrayList<BECompany> fuck = new ArrayList<BECompany>();
        for(BECompany clie : clients){
            fuck.add(clie);
        }
        ArrayList<BECompany> test = getAllClientsFromDevice();
        ArrayList<BECompany> test1 = new ArrayList<>();

        if (test.isEmpty()) {
            for (BECompany x : fuck) {
                _daoClient.insertClientOnList(x);
            }
        } else {
            for (BECompany x : fuck) {
                for (BECompany y : test) {
                    if (x.getId() == y.getId())
                        test1.add(x);
                }
            }
            for (BECompany x : test1)
                fuck.remove(x);

            for (BECompany x : fuck) {
                _daoClient.insertClientOnList(x);
            }
        }
    }

    public ArrayList<BECompany> getAllClientsFromDevice() {
        return _daoClient.getAllClientsFromDevice();
    }

    public long saveCanvas(String canvas, BECompany client) {
       return _daoClient.insertCanvas(canvas, client);
    }
    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany client){
        return _daoClient.getAllCanvasByClientId(client);
    }
}
