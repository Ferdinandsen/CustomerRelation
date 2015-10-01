package bws.customerrelation.GUI.Canvas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bws.customerrelation.GUI.Company.CompanyDataActivity;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.R;

/**
 * Created by jaje on 01-Oct-15.
 */
public class ShowCanvasFragmentMain extends Fragment {

    View rootView;
    TextView region;
    TextView country;
    TextView typeOfTransport;
    TextView activityType;
    TextView businessArea;
    TextView office;
    TextView typeOfvisit;
    TextView visitBy;
    TextView toBeFollowedUpBy;
    TextView followUpDate;
    TextView subject;
    TextView date;
    TextView from;
    TextView toInternal;
    TextView comments;
    private static String TAG = "ShowCanvasActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_canvas_main, container, false);
        super.onCreate(savedInstanceState);
        BECanvas canvas = CompanyDataActivity.SELECTEDCANVAS;
        findviews();
        setCanvasData(canvas);

        return rootView;
    }

    private void setCanvasData(BECanvas c) {
        region.setText(c.getM_Region());
        country.setText((c.getM_Country()));
        typeOfTransport.setText(c.getM_TypeOfTransport());
        activityType.setText(c.getM_ActivityType());
        businessArea.setText(c.getM_BusinessArea());
        office.setText(c.getM_Office());
        typeOfvisit.setText(c.getM_TypeOfVisit());
        visitBy.setText(c.getM_VisitBy());
        toBeFollowedUpBy.setText(c.getM_FollowUpSalesman());
        followUpDate.setText(c.getM_FollowUpDate());
        subject.setText(c.getM_Subject());
        date.setText(c.getM_date());
        from.setText(c.getM_Sender());
        toInternal.setText(c.getM_ToInternal());
        comments.setText(c.getM_text());
    }

    private void findviews() {
        region = (TextView) rootView.findViewById(R.id.region);
        country = (TextView) rootView.findViewById(R.id.country);
        typeOfTransport = (TextView) rootView.findViewById(R.id.typeOfTransport);
        activityType = (TextView) rootView.findViewById(R.id.activityType);
        businessArea = (TextView) rootView.findViewById(R.id.businessArea);
        office = (TextView) rootView.findViewById(R.id.office);
        typeOfvisit = (TextView) rootView.findViewById(R.id.TypeOfVisit);
        visitBy = (TextView) rootView.findViewById(R.id.visitBy);
        toBeFollowedUpBy = (TextView) rootView.findViewById(R.id.toBeFollowedUpBy);
        followUpDate = (TextView) rootView.findViewById(R.id.followUpDate);
        subject = (TextView) rootView.findViewById(R.id.subject);
        date = (TextView) rootView.findViewById(R.id.date);
        from = (TextView) rootView.findViewById(R.id.from);
        toInternal = (TextView) rootView.findViewById(R.id.toInternal);
        comments = (TextView) rootView.findViewById(R.id.comments);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_show_canvas, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}


}
