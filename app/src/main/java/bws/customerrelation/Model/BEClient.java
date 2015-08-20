package bws.customerrelation.Model;

import java.io.Serializable;
import java.security.BasicPermission;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class BEClient implements Serializable {

    private int m_id;
    private String m_firstName;
    private String m_lastName;
    private String m_email;
    private String m_password;
    private String m_company;
    private int m_phoneNumber;
    boolean m_onListSelected;

    public BEClient(String firstName, String lastName, String email, String password, String company, int phoneNumber, boolean onListSelected) {
        m_firstName = firstName;
        m_lastName = lastName;
        m_email = email;
        m_password = password;
        m_company = company;
        m_phoneNumber = phoneNumber;
        m_onListSelected = onListSelected;
    }

    public BEClient(int id, String firstName, String lastName, String email, String password, String company, int phoneNumber, boolean onListSelected) {
        m_id = id;
        m_firstName = firstName;
        m_lastName = lastName;
        m_email = email;
        m_password = password;
        m_company = company;
        m_phoneNumber = phoneNumber;
        m_onListSelected = onListSelected;
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

    public boolean isOnListSelected(){
        return m_onListSelected;
    }
    public void setOnListSelected(boolean b){
        m_onListSelected = b;
    }
}
