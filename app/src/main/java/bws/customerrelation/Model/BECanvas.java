package bws.customerrelation.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Jaje on 19-Aug-15.
 */
public class BECanvas {
    //    @SerializedName("DocUNID")
    private String m_canvasId;
    @SerializedName("MainDocUNID")
    private String m_companyId;
    @SerializedName("TypeVisit")
    private String m_TypeOfVisit;
    @SerializedName("VisitBy")
    private String m_VisitBy;
    @SerializedName("Subject")
    private String m_Subject;
    @SerializedName("CreatedDate")
    private String m_date;
    @SerializedName("FollowUpSalesman")
    private String m_FollowUpSalesman;
    @SerializedName("FollowUpDate")
    private String m_FollowUpDate;
    @SerializedName("Sender")
    private String m_Sender;
    @SerializedName("ToInternal")
    private String m_ToInternal;
    @SerializedName("Region")
    private String m_Region;
    @SerializedName("Country")
    private String m_Country;
    @SerializedName("TypeOfTransport")
    private String m_TypeOfTransport;
    @SerializedName("ActivityType")
    private String m_ActivityType;
    @SerializedName("BusinessArea")
    private String m_BusinessArea;
    @SerializedName("Office")
    private String m_Office;
    @SerializedName("Comments")
    private String m_text;

    public BECanvas(String canvasId, String companyId, String Subject, String VisitBy, String TypeOfVisit,
                    String date, String FollowUpDate, String FollowUpSalesman, String Sender, String ToInternal,
                    String Region, String Country, String TypeOfTransport, String ActivityType,
                    String BusinessArea, String Office, String text) {

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
