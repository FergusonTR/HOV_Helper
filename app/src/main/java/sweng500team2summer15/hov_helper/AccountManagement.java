package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagement {

    int loginId = 0;
    String login = "";
    String password = "";

    // sign into HOV_Helper
    public int signIn(String login, String password)
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
    public int signUp(String login, String password)
    {
        //if username exists
            //prompt that username has been taken
        //else if email exists
            //prompt that email is already in use, use another email
        //else
            //sign-up; insert username, password, and email into database
            //redirect to create profile page

        // return 0 for success, 1 for failure

        JSONParser jsonParser = new JSONParser();

        //This would add the event to the mySQL database.
        //Test Value
        this.loginId = 0;

        //ToDo develop a way of performing the create over an SSL.
        //ToDo have user data verified prior to creation of event.
        //ToDo Consider creating a counter to limit the number of entries created by one user over a period of time.

        // url to create new product
        String url_sign_up = "http://www.hovhelper.com/signup.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        // Building Parameters
        Date d = new Date();
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("timeCreated", d.toString()));

        // getting JSON Object
        // Note that create event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_sign_up,"POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created account
                this.loginId = json.getInt("signup");

            } else {
                // failed to create account
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this.loginId;
    }
}

