package bws.customerrelation.Model;

import java.io.Serializable;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class BECompany implements Serializable {
    /**
     * GENERAL TAB
     */
    private int m_id;
    private int m_CompanyId;
    private String m_firstName;
    private String m_lastName;

    private String m_email;
    private String m_password;
    private String m_companyName;
    private int m_phoneNumber;
    private String m_zipCode;
    private String m_address;
    private String m_city;
    private String m_country;
    private String m_businessRelation;
    private String m_closedCompany;
    private String m_group;
    private String m_telephone;
    private String m_fax;
    private String m_seNo;
    private String m_homepage;
    private String m_salesArea;
    private String m_companyGroup;
    private Boolean m_isCompanyClosed;

    /**
     * SALES INFO TAB
     */
    private String m_salesRep;
    private String m_customerType;
    private String m_typeOfSales;
    private String m_lineOfBusiness;
    private String m_exportArea;
    private String m_importArea;

    public BECompany(int id, int companyId, String companyName, String address, String city,
                     String zip, String country, String phone, String fax,
                     String email, String seNo, String salesArea, String businessRelation,
                     String companyGroup, Boolean companyClosed, String companyHomepage) {
        m_id = id;
        m_CompanyId = companyId;
        m_companyName = companyName;
        m_address = address;
        m_city = city;
        m_zipCode = zip;
        m_country = country;
        m_telephone = phone;
        m_fax = fax;
        m_email = email;
        m_seNo = seNo;
        m_salesArea = salesArea;
        m_businessRelation = businessRelation;
        m_companyGroup = companyGroup;
        m_isCompanyClosed = companyClosed;
        m_homepage = companyHomepage;
    }

    public BECompany(int id, String companyName, String address, String city,
                     String zip, String country, String phone, String fax,
                     String email, String seNo, String salesArea, String businessRelation,
                     String companyGroup, Boolean companyClosed, String companyHomepage) {
        m_id = id;
        m_companyName = companyName;
        m_address = address;
        m_city = city;
        m_zipCode = zip;
        m_country = country;
        m_telephone = phone;
        m_fax = fax;
        m_email = email;
        m_seNo = seNo;
        m_salesArea = salesArea;
        m_businessRelation = businessRelation;
        m_companyGroup = companyGroup;
        m_isCompanyClosed = companyClosed;
        m_homepage = companyHomepage;
    }

    /*
    *Only for use Dummy Data
    */

    public BECompany(String firstName, String lastName, String email, String password, String company, int phoneNumber) {
        m_firstName = firstName;
        m_lastName = lastName;
        m_email = email;
        m_password = password;
        m_companyName = company;
        m_phoneNumber = phoneNumber;

    }

    /*
    *Only for use Dummy Data
    */
    public BECompany(int id, String firstName, String lastName, String email, String password, String company, int phoneNumber) {
        m_id = id;
        m_firstName = firstName;
        m_lastName = lastName;
        m_email = email;
        m_password = password;
        m_companyName = company;
        m_phoneNumber = phoneNumber;

    }


    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getM_CompanyId() {
        return m_CompanyId;
    }

    public void setM_CompanyId(int m_CompanyId) {
        this.m_CompanyId = m_CompanyId;
    }

    public String getM_firstName() {
        return m_firstName;
    }

    public void setM_firstName(String m_firstName) {
        this.m_firstName = m_firstName;
    }

    public String getM_lastName() {
        return m_lastName;
    }

    public void setM_lastName(String m_lastName) {
        this.m_lastName = m_lastName;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password;
    }

    public String getM_companyName() {
        return m_companyName;
    }

    public void setM_companyName(String m_companyName) {
        this.m_companyName = m_companyName;
    }

    public int getM_phoneNumber() {
        return m_phoneNumber;
    }

    public void setM_phoneNumber(int m_phoneNumber) {
        this.m_phoneNumber = m_phoneNumber;
    }

    public String getM_zipCode() {
        return m_zipCode;
    }

    public void setM_zipCode(String m_zipCode) {
        this.m_zipCode = m_zipCode;
    }

    public String getM_address() {
        return m_address;
    }

    public void setM_address(String m_address) {
        this.m_address = m_address;
    }

    public String getM_city() {
        return m_city;
    }

    public void setM_city(String m_city) {
        this.m_city = m_city;
    }

    public String getM_country() {
        return m_country;
    }

    public void setM_country(String m_country) {
        this.m_country = m_country;
    }

    public String getM_businessRelation() {
        return m_businessRelation;
    }

    public void setM_businessRelation(String m_businessRelation) {
        this.m_businessRelation = m_businessRelation;
    }

    public String getM_closedCompany() {
        return m_closedCompany;
    }

    public void setM_closedCompany(String m_closedCompany) {
        this.m_closedCompany = m_closedCompany;
    }

    public String getM_group() {
        return m_group;
    }

    public void setM_group(String m_group) {
        this.m_group = m_group;
    }

    public String getM_telephone() {
        return m_telephone;
    }

    public void setM_telephone(String m_telephone) {
        this.m_telephone = m_telephone;
    }

    public String getM_fax() {
        return m_fax;
    }

    public void setM_fax(String m_fax) {
        this.m_fax = m_fax;
    }

    public String getM_seNo() {
        return m_seNo;
    }

    public void setM_seNo(String m_seNo) {
        this.m_seNo = m_seNo;
    }

    public String getM_homepage() {
        return m_homepage;
    }

    public void setM_homepage(String m_homepage) {
        this.m_homepage = m_homepage;
    }

    public String getM_salesArea() {
        return m_salesArea;
    }

    public void setM_salesArea(String m_salesArea) {
        this.m_salesArea = m_salesArea;
    }

    public String getM_companyGroup() {
        return m_companyGroup;
    }

    public void setM_companyGroup(String m_companyGroup) {
        this.m_companyGroup = m_companyGroup;
    }

    public Boolean getM_isCompanyClosed() {
        return m_isCompanyClosed;
    }

    public void setM_isCompanyClosed(Boolean m_isCompanyClosed) {
        this.m_isCompanyClosed = m_isCompanyClosed;
    }

    public String getM_salesRep() {
        return m_salesRep;
    }

    public void setM_salesRep(String m_salesRep) {
        this.m_salesRep = m_salesRep;
    }

    public String getM_customerType() {
        return m_customerType;
    }

    public void setM_customerType(String m_customerType) {
        this.m_customerType = m_customerType;
    }

    public String getM_typeOfSales() {
        return m_typeOfSales;
    }

    public void setM_typeOfSales(String m_typeOfSales) {
        this.m_typeOfSales = m_typeOfSales;
    }

    public String getM_lineOfBusiness() {
        return m_lineOfBusiness;
    }

    public void setM_lineOfBusiness(String m_lineOfBusiness) {
        this.m_lineOfBusiness = m_lineOfBusiness;
    }

    public String getM_exportArea() {
        return m_exportArea;
    }

    public void setM_exportArea(String m_exportArea) {
        this.m_exportArea = m_exportArea;
    }

    public String getM_importArea() {
        return m_importArea;
    }

    public void setM_importArea(String m_importArea) {
        this.m_importArea = m_importArea;
    }

}
