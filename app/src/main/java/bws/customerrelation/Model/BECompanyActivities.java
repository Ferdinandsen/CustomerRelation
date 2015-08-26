package bws.customerrelation.Model;

/**
 * Created by Jaje on 25-Aug-15.
 */
public class BECompanyActivities {

    private int m_id;
    private int m_companyId;
    private String m_typeOfTransport;
    private String m_type;
    private String m_country;
    private String m_businessArea;
    private boolean m_active;

    public BECompanyActivities(int id,int companyId, String tot, String type, String co, String ba, boolean active) {
        m_id = id;
        m_companyId = companyId;
        m_typeOfTransport = tot;
        m_type = type;
        m_country = co;
        m_businessArea = ba;
        m_active = active;
    }


    public int getM_companyId() {
        return m_companyId;
    }

    public void setM_companyId(int m_companyId) {
        this.m_companyId = m_companyId;
    }

    public String getM_typeOfTransport() {
        return m_typeOfTransport;
    }

    public void setM_typeOfTransport(String m_typeOfTransport) {
        this.m_typeOfTransport = m_typeOfTransport;
    }

    public String getM_type() {
        return m_type;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public String getM_country() {
        return m_country;
    }

    public void setM_country(String m_country) {
        this.m_country = m_country;
    }

    public String getM_businessArea() {
        return m_businessArea;
    }

    public void setM_businessArea(String m_businessArea) {
        this.m_businessArea = m_businessArea;
    }

    public boolean isM_active() {
        return m_active;
    }

    public void setM_active(boolean m_active) {
        this.m_active = m_active;
    }


}
