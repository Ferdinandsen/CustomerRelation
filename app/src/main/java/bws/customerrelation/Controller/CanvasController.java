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
    CompanyController _companyController;
    DAOCanvas _daoCanvas;
    ArrayList<BECanvas> _cacheList;
    private static CanvasController instance = null;

    private CanvasController(Activity activity) {
        _activity = activity;
        _daoCanvas = new DAOCanvas(_activity);
        _companyController = CompanyController.getInstance(_activity);
        _cacheList = getAllCanvasFromAPI();
    }

    public static CanvasController getInstance(Activity activity) {
        if (instance == null) {
            instance = new CanvasController(activity);
        }
        return instance;
    }

    public void createCanvasList() {

        ArrayList<BECompany> dlCompanies = _companyController.getAllClientsFromDevice();
        ArrayList<BECanvas> APIcanvas = _cacheList;
        ArrayList<BECanvas> dlCanvas = _daoCanvas.getAllCanvasFromDevice();
        ArrayList<BECompany> test = new ArrayList<>();
        //FØRSTE GANG - INDSÆTTER ALLE
        for (BECompany x : dlCompanies) {
            test.add(x);
        }

        if (dlCanvas.size() == 0) {
            for (BECompany company : dlCompanies) {
                for (BECanvas canvas : APIcanvas) {
                    if (company.getM_companyId().equals(canvas.getM_companyId())) {
                        canvas.setM_text(_daoCanvas.getRichTextFromHtmlByCanvasId(canvas.getM_canvasId()));
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
                        ca.setM_text(_daoCanvas.getRichTextFromHtmlByCanvasId(ca.getM_canvasId()));
                        _daoCanvas.insertCanvas(ca);
                    }
                }
            }
        }
    }

    private ArrayList<BECanvas> getAllCanvasFromAPI() {
        return _daoCanvas.getAllCanvasFromAPI();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany company) {
        return _daoCanvas.getAllCanvasByClientId(company);
    }


    public long saveCanvas(BECanvas canvas) {
        return _daoCanvas.insertCanvas(canvas);
    }

    public void deleteAllCanvas() {
        _daoCanvas.deleteAllCanvas();
    }

    /**
     * TIL TEST!!
     * @param canvas
     * @return
     */
    public long saveNewCanvas(BECanvas canvas) {
        return _daoCanvas.insertNewCanvas(canvas);
    }
}
