package sweng500team2summer15.hov_helper.Account;

import android.content.Intent;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Toast;

import junit.framework.TestCase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import sweng500team2summer15.hov_helper.JSONParser;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagement {

    final JSONParser jsonParser = new JSONParser();

    String login;
    String password;

    // sign into HOV_Helper
    public int signIn(String login, String password)
    {
        // return 1 for success, 0 for failure

        //ToDo develop a way of performing the create over an SSL.

        // url to sign in
        String url_sign_in = "http://www.hovhelper.com/signin.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_USER = "user";
        String TAG_LOGIN = "login";
        String TAG_PASSWORD = "password";

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_sign_in,"GET", params);

        // check for success tag
        int tmp = 0;
        try {
            int success = json.getInt(TAG_SUCCESS);
            tmp = success;

            if (success == 1) {
                // successfully signed in
                JSONArray userArray = json.getJSONArray(TAG_USER); // JSON Array

                // get user object from JSON Array
                JSONObject userObj = userArray.getJSONObject(0);

                //load the results of the JSON Array into the current object
                this.login = (userObj.getString(TAG_LOGIN));
                this.password = (userObj.getString(TAG_PASSWORD));
            } else {
                // failed to sign in
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tmp;
    }

    // sign up for HOV_Helper
    public int signUp(String login, String password)
    {
        // return 1 for success, 0 for failure

        //ToDo develop a way of performing the create over an SSL.

        // url to sign up
        String url_sign_up = "http://www.hovhelper.com/signup.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        // Building Parameters
        int verificationCode = 100000 + new Random().nextInt(900000);

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("verificationCode", Integer.toString(verificationCode)));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_sign_up, "POST", params);

        // check for success tag
        int tmp = 0;
        try {
            int success = json.getInt(TAG_SUCCESS);
            tmp = success;

            if (success == 1) {
                // successfully created account
                // this.loginId = json.getInt("signup");
            } else {
                // failed to create account
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tmp;
    }
}

