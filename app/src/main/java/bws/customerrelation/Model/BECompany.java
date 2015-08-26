package bws.customerrelation.Model;

import java.io.Serializable;
import java.security.BasicPermission;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class BECompany implements Serializable {
    /**
     * GENERAL TAB
     */
    private int m_id;
    private String m_firstName;
    private String m_lastName;
    private String m_email;
    private String m_password;
    private String m_company;
    private int m_phoneNumber;
    private String m_zipCode;
    private String m_country;
    private String m_businessRelation;
    private String m_closedCompany;
    private String m_group;
    private String m_telephone;
    private String m_fax;
    private String m_seNo;
    private String m_homepage;

    /**
     * SALES INFO TAB
     */
    private String m_salesRep;
    private String m_customerType;
    private String m_typeOfSales;
    private String m_lineOfBusiness;
    private String m_exportArea;
    private String m_importArea;

    public BECompany(String firstName, String lastName, String email, String password, String company, int phoneNumber) {
        m_firstName = firstName;
        m_lastName = lastName;
        m_email = email;
        m_password = password;
        m_company = company;
        m_phoneNumber = phoneNumber;

    }

    public BECompany(int id, String firstName, String lastName, String email, String password, String company, int phoneNumber) {
        m_id = id;
        m_firstName = firstName;
        m_lastName = lastName;
        m_email = email;
        m_password = password;
        m_company = company;
        m_phoneNumber = phoneNumber;

    }


    public String getM_zipCode() {
        return m_zipCode;
    }

    public void setM_zipCode(String m_zipCode) {
        this.m_zipCode = m_zipCode;
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


//    @Override
//    public String toString() {
//        return m_firstName + " " + m_lastName + " - " + m_company;
//    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getFirstName() {
        return m_firstName;
    }

    public void setFirstName(String firstName) {
        m_firstName = firstName;
    }

    public String getLastName() {
        return m_lastName;
    }

    public void SetLastName(String lastName) {
        m_lastName = lastName;
    }

    public String getEmail() {
        return m_email;
    }

    public void setEmail(String email) {
        m_email = email;
    }

    public String getPassword() {
        return m_password;
    }

    public void setPassword(String password) {
        m_password = password;
    }

    public String getCompany() {
        return m_company;
    }

    public void setCompany(String company) {
        m_company = company;
    }

    public int getPhoneNumber() {
        return m_phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        m_phoneNumber = phoneNumber;
    }
}
