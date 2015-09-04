package bws.customerrelation.Model;

/**
 * Created by Jaje on 19-Aug-15.
 */
public class BECanvas {
    private int m_id;
    private String m_canvasId;
    private String m_companyId;
    private String m_TypeOfVisit;
    private String m_VisitBy;
    private String m_Subject;
    private String m_date;
    private String m_FollowUpSalesman;
    private String m_FollowUpDate;
    private String m_Sender;
    private String m_ToInternal;
    private String m_Region;
    private String m_Country;
    private String m_TypeOfTransport;
    private String m_ActivityType;
    private String m_BusinessArea;
    private String m_Office;
    private String m_text;


    public BECanvas(int id, String ClientId, String text) {
        m_id = id;
        m_companyId = ClientId;
        m_text = text;
    }

    public BECanvas(String canvasId, String companyId, String TypeOfVisit, String VisitBy,
                    String Subject, String date, String FollowUpSalesman, String FollowUpDate,
                    String Sender, String ToInternal, String Region, String Country,
                    String TypeOfTransport, String ActivityType, String BusinessArea, String Office, String text) {
        m_canvasId = canvasId;
        m_companyId = companyId;
        m_TypeOfVisit = TypeOfVisit;
        m_VisitBy = VisitBy;
        m_Subject = Subject;
        m_date = date;
        m_FollowUpSalesman = FollowUpSalesman;
        m_FollowUpDate = FollowUpDate;
        m_Sender = Sender;
        m_ToInternal = ToInternal;
        m_Region = Region;
        m_Country = Country;
        m_TypeOfTransport = TypeOfTransport;
        m_ActivityType = ActivityType;
        m_BusinessArea = BusinessArea;
        m_Office = Office;

        m_text = text;
    }


    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getM_companyId() {
        return m_companyId;
    }

    public void setM_companyId(String m_companyId) {
        this.m_companyId = m_companyId;
    }

    public String getM_canvasId() {
        return m_canvasId;
    }

    public void setM_canvasId(String m_canvasId) {
        this.m_canvasId = m_canvasId;
    }

    public String getText() {
        return m_text;
    }

    public void setText(String text) {
        m_text = text;
    }

    public String getM_TypeOfVisit() {
        return m_TypeOfVisit;
    }

    public void setM_TypeOfVisit(String m_TypeOfVisit) {
        this.m_TypeOfVisit = m_TypeOfVisit;
    }

    public String getM_VisitBy() {
        return m_VisitBy;
    }

    public void setM_VisitBy(String m_VisitBy) {
        this.m_VisitBy = m_VisitBy;
    }

    public String getM_Subject() {
        return m_Subject;
    }

    public void setM_Subject(String m_Subject) {
        this.m_Subject = m_Subject;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public String getM_FollowUpSalesman() {
        return m_FollowUpSalesman;
    }

    public void setM_FollowUpSalesman(String m_FollowUpSalesman) {
        this.m_FollowUpSalesman = m_FollowUpSalesman;
    }

    public String getM_FollowUpDate() {
        return m_FollowUpDate;
    }

    public void setM_FollowUpDate(String m_FollowUpDate) {
        this.m_FollowUpDate = m_FollowUpDate;
    }

    public String getM_Sender() {
        return m_Sender;
    }

    public void setM_Sender(String m_Sender) {
        this.m_Sender = m_Sender;
    }

    public String getM_ToInternal() {
        return m_ToInternal;
    }

    public void setM_ToInternal(String m_ToInternal) {
        this.m_ToInternal = m_ToInternal;
    }

    public String getM_Region() {
        return m_Region;
    }

    public void setM_Region(String m_Region) {
        this.m_Region = m_Region;
    }

    public String getM_Country() {
        return m_Country;
    }

    public void setM_Country(String m_Country) {
        this.m_Country = m_Country;
    }

    public String getM_TypeOfTransport() {
        return m_TypeOfTransport;
    }

    public void setM_TypeOfTransport(String m_TypeOfTransport) {
        this.m_TypeOfTransport = m_TypeOfTransport;
    }

    public String getM_ActivityType() {
        return m_ActivityType;
    }

    public void setM_ActivityType(String m_ActivityType) {
        this.m_ActivityType = m_ActivityType;
    }

    public String getM_BusinessArea() {
        return m_BusinessArea;
    }

    public void setM_BusinessArea(String m_BusinessArea) {
        this.m_BusinessArea = m_BusinessArea;
    }

    public String getM_Office() {
        return m_Office;
    }

    public void setM_Office(String m_Office) {
        this.m_Office = m_Office;
    }

    public String getM_text() {
        return m_text;
    }

    public void setM_text(String m_text) {
        this.m_text = m_text;
    }

    @Override
    public String toString() {
        return m_companyId + " " + m_Subject + " canvas id : " + m_canvasId;
    }


}
