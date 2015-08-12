package bws.customerrelation.Model;

import java.io.Serializable;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class BEUser implements Serializable {

    private int m_id;
    private String m_firstname;
    private String m_lastname;
    private String m_email;
    private String m_password;
    private int m_phoneNumber;

    public BEUser(int id, String firstname, String lastname, String email, String password, int phoneNumber) {
        m_id= id;
        m_firstname = firstname;
        m_lastname = lastname;
        m_email = email;
        m_password = password;
        m_phoneNumber = phoneNumber;
    }

    public BEUser( String firstname, String lastname, String email, String password, int phoneNumber) {
        m_firstname = firstname;
        m_lastname = lastname;
        m_email = email;
        m_password = password;
        m_phoneNumber = phoneNumber;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getFirstname() {
        return m_firstname;
    }

    public void setFirstname(String firstname) {
        m_firstname = firstname;
    }

    public String getLastname() {
        return m_lastname;
    }

    public void setLastname(String lastname) {
        m_lastname = lastname;
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

    public int getPhoneNumber() {
        return m_phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        m_phoneNumber = phoneNumber;
    }
}