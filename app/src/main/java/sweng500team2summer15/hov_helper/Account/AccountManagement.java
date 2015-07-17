package sweng500team2summer15.hov_helper.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

    // sign up for HOV_Helper
    public String signUp(String login, String password)
    {
        //ToDo develop a way of performing the create over an SSL.

        if (password.length() < 6) {
            return "Error: Please enter a password with more than 6 characters";
        }

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

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
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

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
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
        int verificationCode = 100000 + new Random().nextInt(900000);

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("verificationCode", Integer.toString(verificationCode)));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)
        //        .appendQueryParameter("verificationCode", Integer.toString(verificationCode));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_verify, "POST", params);

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String resendVerificationCode(String email)
    {
        // url to verify account
        String url_verify = "http://www.hovhelper.com/resend.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";


        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", email));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)
        //        .appendQueryParameter("verificationCode", Integer.toString(verificationCode));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_verify, "POST", params);

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String changePassword(String login, String currentPassword, String newPassword, String reenterPassword)
    {
        // url to verify account
        String url_getpassword = "http://www.hovhelper.com/get_old_password.php";
        String url_changepassword = "http://www.hovhelper.com/change_password.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> oldParams = new ArrayList<NameValuePair>();
        oldParams.add(new BasicNameValuePair("login", login));
        oldParams.add(new BasicNameValuePair("password", currentPassword));

        // posting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_getpassword, "GET", oldParams);
        int oldPasswordCheck = 0;

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            oldPasswordCheck = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // is old password correct?
        if (oldPasswordCheck == 0) {
            return message;
        }

        // is new password and reentered password different?
        if (!newPassword.equals(reenterPassword)) {
            return "Error: New passwords do not match";
        }

        // does new password have characters?
        if (newPassword.length() < 6) {
            return "Error: Please enter a password with more than 6 characters";
        }

        // is new password the same as the old password?
        if (currentPassword.equals(newPassword)) {
            return "Error: The new password cannot be the same as the current password";
        }

        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", login));
        params.add(new BasicNameValuePair("password", newPassword));

        //Uri.Builder builder = Uri.parse(url_sign_up)
        //        .buildUpon()
        //        .appendQueryParameter("login", login)
        //        .appendQueryParameter("password", password)
        //        .appendQueryParameter("verificationCode", Integer.toString(verificationCode));

        // posting JSON Object
        json = jsonParser.makeHttpRequest(url_changepassword, "POST", params);

        // get value from message tag
        try {
            //int success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return message;
    }
}

