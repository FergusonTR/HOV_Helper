package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagement {

    // constructor
    public AccountManagement()
    {

    }

    // sign into HOV_Helper
    public int SignIn(String username, String password)
    {
        //if username exists
            //if password is bad
                //prompt that password is incorrect
            //else
                //sign-in
                //redirect to profile dashboard
        //else
            //prompt to sign up

        // return 0 for success, 1 for failure
        return 0;
    }

    // sign up for HOV_Helper
    public int SignUp(String username, String password, String email)
    {
        //if username exists
            //prompt that username has been taken
        //else if email exists
            //prompt that email is already in use, use another email
        //else
            //sign-up; insert username, password, and email into database
            //redirect to create profile page

        // return 0 for success, 1 for failure
        return 0;
    }
}

