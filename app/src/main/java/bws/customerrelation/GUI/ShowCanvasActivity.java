package bws.customerrelation.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import bws.customerrelation.Controller.SharedConstants;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.R;

public class ShowCanvasActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_canvas);
        BECanvas canvas = CompanyDataActivity.SELECTEDCANVAS;
        findviews();
        setCanvasData(canvas);
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
        region = (TextView) findViewById(R.id.region);
        country = (TextView) findViewById(R.id.country);
        typeOfTransport = (TextView) findViewById(R.id.typeOfTransport);
        activityType = (TextView) findViewById(R.id.activityType);
        businessArea = (TextView) findViewById(R.id.businessArea);
        office = (TextView) findViewById(R.id.office);
        typeOfvisit = (TextView) findViewById(R.id.TypeOfVisit);
        visitBy = (TextView) findViewById(R.id.visitBy);
        toBeFollowedUpBy = (TextView) findViewById(R.id.toBeFollowedUpBy);
        followUpDate = (TextView) findViewById(R.id.followUpDate);
        subject = (TextView) findViewById(R.id.subject);
        date = (TextView) findViewById(R.id.date);
        from = (TextView) findViewById(R.id.from);
        toInternal = (TextView) findViewById(R.id.toInternal);
        comments = (TextView) findViewById(R.id.comments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_canvas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
