package bws.customerrelation.Model;

/**
 * Created by Jaje on 19-Aug-15.
 */
public class BECanvas {
    int m_id;
    int m_ClientID;
    String m_text;

    public BECanvas(int id, int ClientId, String text) {
        m_id = id;
        m_ClientID = ClientId;
        m_text = text;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public int getClientId() {
        return m_ClientID;
    }

    public void setM_ClientID(int clientID) {
        m_ClientID = clientID;
    }

    public String getText() {
        return m_text;
    }

    public void setText(String text) {
        m_text = text;
    }

    @Override
    public String toString() {
        return m_id + " " + m_text;
    }
}
