package sweng500team2summer15.hov_helper.Profile;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sweng500team2summer15.hov_helper.JSONParser;

/**
 * Created by Mike on 6/7/2015.
 */
public class Profile{

    public String LoginID = "";
    public String UserFirstName = "";
    public String UserLastName = "";
    public String PhoneNumber = "";
    public String EmailAddress = "";
    public sweng500team2summer15.hov_helper.Profile.EmergencyContactInfo EmergencyContactInfo;
    public Sex UserSex = Sex.MALE;
    public PreferredContactMethod UserPreferredContactMethod = PreferredContactMethod.CALL;
    public SmokingPreference UserSmokingPreference = SmokingPreference.NONSMOKE;

    public enum Sex {MALE, FEMALE}
    public enum PreferredContactMethod {TEXT, CALL}
    public enum SmokingPreference {SMOKE, NONSMOKE, NOPREF}

    public Profile(){}

    public Profile(String loginID, String firstName, String lastName, Sex sex, String phoneNumber, PreferredContactMethod preferredContactMethod,
                   String email, EmergencyContactInfo emergencyContactInfo, SmokingPreference smokingPreference)
    {
        this.LoginID = loginID;
        this.UserFirstName = firstName;
        this.UserLastName = lastName;
        this.UserSex = sex;
        this.PhoneNumber = phoneNumber;
        this.UserPreferredContactMethod = preferredContactMethod;
        this.EmailAddress = email;
        this.EmergencyContactInfo = emergencyContactInfo;
        this.UserSmokingPreference = smokingPreference;
    }

    // Submit a profile to the database
    public Boolean SubmitProfile(String loginID, String password)
    {
        //This code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
        JSONParser jsonParser = new JSONParser();

        // url to create new product
        //ToDo Change this to point to the Hovhelper website
        String url_create_event = "http://www.hovhelper.com/create_profile.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        EmergencyContactInfo tempContactInfo = this.EmergencyContactInfo;

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginID", loginID));
        params.add(new BasicNameValuePair("password",password));
        params.add(new BasicNameValuePair("userFirstName", this.UserFirstName));
        params.add(new BasicNameValuePair("userLastName", this.UserLastName));
        params.add(new BasicNameValuePair("phoneNumber",this.PhoneNumber));
        params.add(new BasicNameValuePair("email", this.EmailAddress));
        params.add(new BasicNameValuePair("emergencyContact_ContactNumber", tempContactInfo.ContactNumber));
        params.add(new BasicNameValuePair("emergencyContact_ContactName", tempContactInfo.ContactName));
        params.add(new BasicNameValuePair("sex", this.UserSex.toString()));
        params.add(new BasicNameValuePair("preferredContactMethod", this.UserPreferredContactMethod.toString()));
        params.add(new BasicNameValuePair("smokingPreference", this.UserSmokingPreference.toString()));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);

        // check log cat for response
        Log.d("Create Response", json.toString());

        Boolean success = false;

        // check for success tag
        try {
            int wasSuccess = json.getInt(TAG_SUCCESS);

            if (wasSuccess == 1) {
                success = true;
            } else {
                success = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return success;
    }

    // Retrieve the profile matching the supplied profile ID
    public Profile GetProfile(String loginID) {

        //This code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
        JSONParser jsonParser = new JSONParser();

        // url to create new product
        String url_read_profile = "http://www.hovhelper.com/read_profile.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_PROFILE = "profile";
        String TAG_LOGIN = "loginID";
        String TAG_FIRSTNAME = "userFirstName";
        String TAG_LASTNAME = "userLastName";
        String TAG_PHONE = "phoneNumber";
        String TAG_EMAIL = "email";
        String TAG_ECNUMBER = "emergencyContact_ContactNumber";
        String TAG_ECNAME = "emergencyContact_ContactName";
        String TAG_SEX = "sex";
        String TAG_CONTACTMETHOD = "preferredContactMethod";
        String TAG_SMOKING = "smokingPreference";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginID", loginID));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_read_profile,"GET", params);

        // check log cat for response
        Log.d("Create Response", json.toString());

        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {

                // successfully read event
                JSONArray eventObj = json.getJSONArray(TAG_PROFILE); // JSON Array

                // get event object from JSON Array
                JSONObject profile = eventObj.getJSONObject(0);

                this.LoginID = (profile.getString(TAG_LOGIN));
                this.UserFirstName = (profile.getString(TAG_FIRSTNAME));
                this.UserLastName = (profile.getString(TAG_LASTNAME));
                String sex = (profile.getString(TAG_SEX));
                switch (sex)
                {
                    case "MALE":
                        this.UserSex = Sex.MALE;
                        break;
                    case "FEMALE":
                        this.UserSex = Sex.FEMALE;
                        break;
                }

                this.PhoneNumber = (profile.getString(TAG_PHONE));
                String contact = (profile.getString(TAG_CONTACTMETHOD));
                switch (contact)
                {
                    case "CALL":
                        this.UserPreferredContactMethod = PreferredContactMethod.CALL;
                        break;
                    case "TEXT":
                        this.UserPreferredContactMethod = PreferredContactMethod.TEXT;
                        break;
                }

                this.EmailAddress = ((profile.getString(TAG_EMAIL)));

                String emergencyContactName = ((profile.getString(TAG_ECNAME)));
                String emergencyContactNumber = ((profile.getString(TAG_ECNUMBER)));
                this.EmergencyContactInfo = new EmergencyContactInfo(emergencyContactName, emergencyContactNumber);

                String smokingPreference = ((profile.getString(TAG_SMOKING)));

                switch (smokingPreference){
                    case "NONSMOKE" :
                        this.UserSmokingPreference = SmokingPreference.NONSMOKE;
                        break;
                    case "SMOKE" :
                        this.UserSmokingPreference = SmokingPreference.SMOKE;
                        break;
                    case "NOPREF" :
                        this.UserSmokingPreference = SmokingPreference.NOPREF;
                        break;
                }

            } else {
                // This will indicate the profile wasn't returned
                this.LoginID = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return this;
    }

    public String UpdateProfile(String loginID){

        JSONParser jsonParser = new JSONParser();

        // url to update new product
        String url_update_profile = "http://www.hovhelper.com/update_profile.php";

        // JSON Node names
        String TAG_SUCCESS = "success";
        String TAG_MESSAGE = "message";
        String updateResult = "";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginID", loginID));
        params.add(new BasicNameValuePair("userFirstName", this.UserFirstName));
        params.add(new BasicNameValuePair("userLastName", this.UserLastName));
        params.add(new BasicNameValuePair("phoneNumber", this.PhoneNumber));
        params.add(new BasicNameValuePair("email", this.EmailAddress));
        params.add(new BasicNameValuePair("emergencyContact_ContactNumber", this.EmergencyContactInfo.ContactNumber));
        params.add(new BasicNameValuePair("emergencyContact_ContactName", this.EmergencyContactInfo.ContactName));
        params.add(new BasicNameValuePair("sex", this.UserSex.toString()));
        params.add(new BasicNameValuePair("preferredContactMethod", this.UserPreferredContactMethod.toString()));
        params.add(new BasicNameValuePair("smokingPreference", this.UserSmokingPreference.toString()));

        // getting JSON Object
        // Note that update event url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_update_profile,"POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully updated  event
                updateResult= json.getString(TAG_MESSAGE);

            } else {
                // failed to update profile
                updateResult = "Update Failed";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return updateResult;
    }
}
