package bws.customerrelation.Model;

/**
 * Created by jaje on 21-Sep-15.
 */
public class BECountry {

    private String _name;
    private String _region;
    private String _countryCode;
    private String _phonePrefix;
    
    public BECountry(String name,String region,String conuntryCode,String phonePrefix ){
        set_name(name);
        set_region(region);
        set_countryCode(conuntryCode);
        set_phonePrefix(phonePrefix);
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_region() {
        return _region;
    }

    public void set_region(String _region) {
        this._region = _region;
    }

    public String get_countryCode() {
        return _countryCode;
    }

    public void set_countryCode(String _countryCode) {
        this._countryCode = _countryCode;
    }

    public String get_phonePrefix() {
        return _phonePrefix;
    }

    public void set_phonePrefix(String _phonePrefix) {
        this._phonePrefix = _phonePrefix;
    }
}
