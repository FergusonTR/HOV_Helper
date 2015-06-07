package sweng500team2summer15.hov_helper;

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

    enum Sex {MALE, FEMALE};
    enum PreferredContactMethod {TEXT, CALL};
    enum SmokingPreference {SMOKE, NONSMOKE, NOPREF};

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
}
