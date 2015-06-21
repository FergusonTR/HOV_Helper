package sweng500team2summer15.hov_helper;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Mike on 6/7/2015.
 */
public class Profile {

    int LoginID = 0;
    String UserFirstName = "";
    String UserLastName = "";
    int PhoneNumber = 0;
    String EmailAddress = "";
    EmergencyContactInfo EmergencyContactInfo;
    Sex UserSex = Sex.MALE;
    PreferredContactMethod UserPreferredContactMethod = PreferredContactMethod.CALL;
    SmokingPreference UserSmokingPreference = SmokingPreference.NONSMOKE;

    enum Sex {MALE, FEMALE}

    enum PreferredContactMethod {TEXT, CALL}

    enum SmokingPreference {SMOKE, NONSMOKE, NOPREF}

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

        boolean success = SubmitProfile(this);

        //if(!success)
          // TODO - what to do if we fail to create the profile?
    }

    // Submit a profile to the database
    private Boolean SubmitProfile(Profile profile)
    {
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
        params.add(new BasicNameValuePair("LoginID", Integer.toString(profile.LoginID)));
        params.add(new BasicNameValuePair("UserFirstName", profile.UserFirstName));
        params.add(new BasicNameValuePair("UserLastName", profile.UserLastName));
        params.add(new BasicNameValuePair("PhoneNumber", Integer.toString(profile.PhoneNumber)));
        params.add(new BasicNameValuePair("EmailAddress", profile.EmailAddress));
        params.add(new BasicNameValuePair("EmergencyContactInfo_Name", profile.EmergencyContactInfo.ContactName));
        params.add(new BasicNameValuePair("EmergencyContactInfo_Number", Integer.toString(profile.EmergencyContactInfo.ContactNumber)));
        params.add(new BasicNameValuePair("Sex", profile.UserSex.toString()));
        params.add(new BasicNameValuePair("PreferredContactMethod", profile.UserPreferredContactMethod.toString()));
        params.add(new BasicNameValuePair("SmokingPreference", profile.UserSmokingPreference.toString()));

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
