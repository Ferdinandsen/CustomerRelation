package bws.customerrelation.Controller;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOClient;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BEClient;

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
            _daoClient.insert(new BEClient("André", "Psy", "SejerejekillerxX@live.dk", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Thomas", "Petersen", "PsykoMegetOverklar@msn.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Bob", "Olesen", "bobolesen@hotmail.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Kevin", "Ørskov", "yoyo@live.dk", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Jacob", "Jakobsen", "feedthehorse@yumyum.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Trine", "Hansen", "thansen@fakta.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Anne", "Dahl", "adahl@gmail.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Thue", "Emilsen", "Volume@onmypc.com", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Mette", "Enevoldsen", "menevold@live.dk", "hej123", "BWS", 0,false));
            _daoClient.insert(new BEClient("Kasper", "Juul", "kjuu@live.dk", "hej123", "BWS", 0,false));
        }
    }

    public ArrayList<BEClient> getAllClients() {
        return _daoClient.getAllClients();
    }

    public void deleteAllClients() {
        _daoClient.deleteAllClients();
    }

    public void createClientList(ArrayList<BEClient> clients) {
        ArrayList<BEClient> test = getAllClientsFromDevice();
        ArrayList<BEClient> test1 = new ArrayList<>();

        if (test.isEmpty()) {
            for (BEClient x : clients) {
                _daoClient.insertClientOnList(x);
            }
        } else {
            for (BEClient x : clients) {
                for (BEClient y : test) {
                    if (x.getId() == y.getId())
                        test1.add(x);
                }
            }
            for (BEClient x : test1)
                clients.remove(x);

            for (BEClient x : clients) {
                _daoClient.insertClientOnList(x);
            }
        }
    }

    public ArrayList<BEClient> getAllClientsFromDevice() {
        return _daoClient.getAllClientsFromDevice();
    }

    public long saveCanvas(String canvas, BEClient client) {
       return _daoClient.insertCanvas(canvas, client);
    }
    public ArrayList<BECanvas> getAllCanvasByClientId(BEClient client){
        return _daoClient.getAllCanvasByClientId(client);
    }
}
