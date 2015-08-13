package bws.customerrelation.Controller;

import android.app.Activity;


import bws.customerrelation.DAL.DAOUser;
import bws.customerrelation.Model.BEUser;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class UserController {
    Activity _Activity;
    DAOUser _daoUser;

    public UserController(Activity context) {
        _Activity = context;
        _daoUser = new DAOUser(_Activity);
    }

    public void createDummyUsers() {
        if (_daoUser.getAllUsers().size() < 10) {
            _daoUser.insert(new BEUser("André", "Psy", "SejerejekillerxX@live.dk", "hej123", 0));
            _daoUser.insert(new BEUser("Thomas", "Petersen", "PsykoMegetOverklar@msn.com", "hej123", 0));
            _daoUser.insert(new BEUser("Bob", "Olesen", "bobolesen@hotmail.com", "hej123", 0));
            _daoUser.insert(new BEUser("Kevin", "Ørskov", "yoyo@live.dk", "hej123", 27242508));
            _daoUser.insert(new BEUser("Jacob", "Jakobsen", "feedthehorse@yumyum.com", "hej123", 23839498));
            _daoUser.insert(new BEUser("Trine", "Hansen", "thansen@fakta.com", "hej123", 0));
            _daoUser.insert(new BEUser("Anne", "Dahl", "adahl@gmail.com", "hej123", 0));
            _daoUser.insert(new BEUser("Thue", "Emilsen", "Volume@onmypc.com", "hej123", 0));
            _daoUser.insert(new BEUser("Mette", "Enevoldsen", "menevold@live.dk", "hej123", 0));
            _daoUser.insert(new BEUser("Kasper", "Juul", "kjuu@live.dk", "hej123", 0));
        }
    }

//    public BEUser getUserByCredentials(String email, String password) {
//        return _daoUser.getUserByCredentials(email, password);
//    }

}
