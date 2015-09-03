package bws.customerrelation.Controller;

import android.app.Activity;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOCanvas;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jaje on 01-Sep-15.
 */
public class CanvasController {
    Activity _activity;
    ClientController _clientController;
    DAOCanvas _daoCanvas;

    public CanvasController(Activity context) {
        _activity = context;
        _daoCanvas = new DAOCanvas(_activity);
        _clientController = new ClientController(_activity);
    }


    public void createCanvasList() {

        ArrayList<BECompany> dlCompanies = _clientController.getAllClientsFromDevice();
        ArrayList<BECanvas> APIcanvas = getAllCanvasFromAPI();
        ArrayList<BECanvas> dlCanvas = _daoCanvas.getAllCanvasFromDevice();
        ArrayList<BECompany> test = new ArrayList<>();
        //FØRSTE GANG - INDSÆTTER ALLE
        for(BECompany x : dlCompanies){
            test.add(x);
        }

        if (dlCanvas.size() == 0) {
            for (BECompany clie : dlCompanies) {
                for (BECanvas canvas : APIcanvas) {
                    if (clie.getM_companyId().equals(canvas.getM_companyId())) {
                        _daoCanvas.insertCanvas(canvas);
                    }
                }
            } //ANDEN GANG...
        } else {
            for (BECompany company : dlCompanies) {
                for (BECanvas c : dlCanvas) {
                    if (company.getM_companyId().equals(c.getM_companyId())) {
                        test.remove(company);
                    }
                }
            }
            //INDSÆTTER DEM DER MANGLER
            for (BECompany co : test) {
                for (BECanvas ca : APIcanvas) {
                    if (co.getM_companyId().equals(ca.getM_companyId())) {
                        _daoCanvas.insertCanvas(ca);
                    }
                }
            }
        }

    }

    public ArrayList<BECanvas> getAllCanvasFromAPI() {
        return _daoCanvas.getAllCanvasFromAPI();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany client) {
        return _daoCanvas.getAllCanvasByClientId(client);
    }

    public void deleteAllCanvas() {
        _daoCanvas.deleteAllCanvas();
    }
}
