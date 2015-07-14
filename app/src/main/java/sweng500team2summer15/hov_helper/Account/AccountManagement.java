package sweng500team2summer15.hov_helper.Account;

import android.content.Intent;
import android.net.Uri;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Toast;

import junit.framework.TestCase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.security.SecureRandom;

import sweng500team2summer15.hov_helper.JSONParser;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagement {

    final JSONParser jsonParser = new JSONParser();

    String login;
    String password;
    String message;

    // sign into HOV_Helper
    public String signIn(String login, String password)
    {
        // url to sign in
        String url_sign_in = "http://www.hovhelper.com/signin.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";
        String TAG_USER = "user";
        String TAG_LOGIN = "login";
        String TAG_PASSWORD = "password";

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("password", password));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)

        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_sign_in,"GET", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

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
                message = json.getString(TAG_MESSAGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

    // sign up for HOV_Helper
    public String signUp(String login, String password)
    {
        //ToDo develop a way of performing the create over an SSL.

        // url to sign up
        String url_sign_up = "http://www.hovhelper.com/signup.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";

        // Building Parameters
        int verificationCode = 100000 + new Random().nextInt(900000);

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("verificationCode", Integer.toString(verificationCode)));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)
        //        .appendQueryParameter("verificationCode", Integer.toString(verificationCode));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_sign_up, "POST", params);

        // check for success tag
        int tmp = 0;
        try {
            int success = json.getInt(TAG_SUCCESS);
            tmp = success;

            if (success == 1) {
                // successfully created account
            } else {
                // failed to create account
                message = json.getString(TAG_MESSAGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String verifyAccount(String verificationCode)
    {
        // url to verify account
        String url_verify = "http://www.hovhelper.com/verify.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("verificationCode", verificationCode));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)
        //        .appendQueryParameter("verificationCode", Integer.toString(verificationCode));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_verify, "POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully verified account
            } else {
                // failed to create account
                message = json.getString(TAG_MESSAGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String resetPassword(String email)
    {
        // url to verify account
        String url_verify = "http://www.hovhelper.com/reset.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";

        //generate password
        SecureRandom random = new SecureRandom();
        String password = new BigInteger(130, random).toString(32).substring(0, 7);

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", email));
        params.add(new BasicNameValuePair("password", password));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)
        //        .appendQueryParameter("verificationCode", Integer.toString(verificationCode));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_verify, "POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully verified account
            } else {
                // failed to create account
                message = json.getString(TAG_MESSAGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }
}

