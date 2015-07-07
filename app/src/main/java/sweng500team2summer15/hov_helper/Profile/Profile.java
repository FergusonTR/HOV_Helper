package sweng500team2summer15.hov_helper.Profile;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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

    public int LoginID = 0;
    public String UserFirstName = "";
    public String UserLastName = "";
    public int PhoneNumber = 0;
    public String EmailAddress = "";
    public sweng500team2summer15.hov_helper.Profile.EmergencyContactInfo EmergencyContactInfo;
    public Sex UserSex = Sex.MALE;
    public PreferredContactMethod UserPreferredContactMethod = PreferredContactMethod.CALL;
    public SmokingPreference UserSmokingPreference = SmokingPreference.NONSMOKE;

    public enum Sex {MALE, FEMALE}
    public enum PreferredContactMethod {TEXT, CALL}
    public enum SmokingPreference {SMOKE, NONSMOKE, NOPREF}

    public Profile(){}

    public Profile(int loginID, String firstName, String lastName, Sex sex, int phoneNumber, PreferredContactMethod preferredContactMethod,
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
    public Boolean SubmitProfile()
    {
        //This code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
        JSONParser jsonParser = new JSONParser();

        // url to create new product
        //ToDo Change this to point to the Hovhelper website
        String url_create_event = "http://www.hovhelper.com/create_profile.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        // TODO !!! these are hardcoded for now, but need to change this
        Random rand = new Random();
        int randomLoginId = rand.nextInt((99999 - 1) + 1) + 1;
        String loginId = Integer.toString(randomLoginId);
        String password = "password";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("LoginID", Integer.toString(this.LoginID)));
        params.add(new BasicNameValuePair("loginId", loginId));
        params.add(new BasicNameValuePair("password", password));

        params.add(new BasicNameValuePair("userFirstName", this.UserFirstName));
        params.add(new BasicNameValuePair("userLastName", this.UserLastName));
        params.add(new BasicNameValuePair("phoneNumber", Integer.toString(this.PhoneNumber)));
        params.add(new BasicNameValuePair("email", this.EmailAddress));
        params.add(new BasicNameValuePair("emergencyContact_ContactNumber", Integer.toString(this.EmergencyContactInfo.ContactNumber)));
        params.add(new BasicNameValuePair("emergencyContact_ContactName", this.EmergencyContactInfo.ContactName));
        params.add(new BasicNameValuePair("sex", this.UserSex.toString()));
        params.add(new BasicNameValuePair("preferredContactMethod", this.UserPreferredContactMethod.toString()));
        params.add(new BasicNameValuePair("smokingPreference", this.UserSmokingPreference.toString()));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_event,"POST", params);

        // check log cat for response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int wasSuccess = json.getInt(TAG_SUCCESS);

            if (wasSuccess == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Retrieve the profile matching the supplied profile ID
    private Profile GetProfile(int profileID) {

        Profile returnedProfile;

        //This code was borrowed from http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
        JSONParser jsonParser = new JSONParser();

        // url to create new product
        //ToDo Change this to point to the Hovhelper website
        String url_create_event = "http://192.168.1.6/create_event.php";

        // JSON Node names
        String TAG_SUCCESS = "success";

        // Building Parameters
        //ToDo remove deprecated approach and use URLBuilder instead
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("LoginID", Integer.toString(profileID)));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_event, "GET", params);

        // check log cat for response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int wasSuccess = json.getInt(TAG_SUCCESS);

            if (wasSuccess == 1) {
                // TODO - set the values for the returned profile

                //return returnedProfile;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
