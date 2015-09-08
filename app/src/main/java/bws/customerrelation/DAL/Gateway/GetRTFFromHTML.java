package bws.customerrelation.DAL.Gateway;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.List;

/**
 * Created by Jaje on 08-Sep-15.
 */
public class GetRTFFromHTML extends AsyncTask<String, Void, String> {

    Document _doc;
//    Context _Context;
//    private ProgressDialog _dialog;
//
//    public GetRTFFromHTML(Context context) {
//        _Context = context;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        _dialog = new ProgressDialog(_Context);
//        _dialog.setMessage("Doing something, please wait.");
//        _dialog.show(); //Rammer 84 gange = alle canvas!
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        if (_dialog.isShowing()) {
//            _dialog.dismiss();
//        }
//    }

    @Override
    protected String doInBackground(String... params) {
        //HTML FETCH

        String url = "http://skynet.bws.dk/Applications/smsAndroid.nsf/(CanvasByCompany)/" + params[0] + "?OpenDocument";
        try {
            _doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return "NULL";
        }

        Elements ps = _doc.select("form");
        List<Node> ele = ps.get(0).childNodes();

        StringBuilder sb = new StringBuilder();
        String resultat = "";
        List<Node> ele1 = ele.subList(1, ele.size() - 3);
        for (Node e : ele1) {
            if (e.toString().equals("") || !(e.toString().equals("<br>"))) {
                sb.append(e.toString() + "\n");
            }
        }
        resultat = sb.toString();

        String res = resultat;
        return res;
    }

}
