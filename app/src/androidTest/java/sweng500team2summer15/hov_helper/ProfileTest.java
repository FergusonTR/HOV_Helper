package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by Mike on 6/7/2015.
 */
public class ProfileTest extends TestCase {

    @Override
    protected void setUp() throws Exception{
        super.setUp();
    }

    // TC 11 - Create Profile
    @SmallTest
    public void Test_CreateProfile()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"Test", "Test", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Store it in the list
        ProfileList profileList = new ProfileList();
        profileList.ProfileList.add(testProf);

        // Ensure the object is added and correct
        assertTrue(profileList.ProfileList.contains(testProf));
    }

    // TC 12 - Profile - required fields are blank
    @SmallTest
    public void Test_CreateProfile_Blank()
    {
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("", 0);
        Profile testProf = new Profile(0, "","", Profile.Sex.MALE, 0, Profile.PreferredContactMethod.CALL, "", testEmergency, Profile.SmokingPreference.NONSMOKE);

        // Assert that the following values are not the default (empty) values
        assertTrue(testProf.UserFirstName != "");
        assertTrue(testProf.UserLastName != "");
        assertTrue(testProf.EmailAddress != "");
        assertTrue(testProf.EmergencyContactInfo.ContactName != "");
        assertTrue(testProf.EmergencyContactInfo.ContactNumber != 0);
        assertTrue(testProf.PhoneNumber != 0);
    }

    // TC 13 - Update Profile - First Name
    @SmallTest
    public void Test_UpdateProfile_FirstName()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"InitialName", "Test", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Update the First Name property
        testProf.UserFirstName = "UpdatedName";

        assertTrue(testProf.UserFirstName == "UpdateName");
    }

    // TC 14 - Update Profile - Last Name
    @SmallTest
    public void Test_UpdateProfile_LastName()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"InitialName", "InitialName", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Update the First Name property
        testProf.UserLastName = "UpdatedName";

        assertTrue(testProf.UserLastName == "UpdateName");
    }

    // TC 15 - Update Profile - Home Location
    @SmallTest
    public void Test_UpdateProfile_HomeLocation()
    {
        assertTrue(false);
    }

    // TC 16 - Update Profile - Work Location
    @SmallTest
    public void Test_UpdateProfile_WorkLocation()
    {
        assertTrue(false);
    }

    // TC 17 - Update Profile - Carpool Preferences
    @SmallTest
    public void Test_UpdateProfile_CarpoolPreference()
    {
        assertTrue(false);
    }

    // TC 18 - Update Profile - Frequency Preferences
    @SmallTest
    public void Test_UpdateProfile_Frequency()
    {
        assertTrue(false);
    }

    // TC 19 - Update Profile - Pickup to work time
    @SmallTest
    public void Test_UpdateProfile_PickupTime_ToWork()
    {
        assertTrue(false);
    }

    // TC 20 - Update Profile - pickup from work
    @SmallTest
    public void Test_UpdateProfile_PickupTime_FromWork()
    {
        assertTrue(false);
    }

    // TC 21 - Update Profile - Other Preferences
    @SmallTest
    public void Test_UpdateProfile_OtherPreferences()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"Caitlyn", "Jenner", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Update the enum properties
        testProf.UserSmokingPreference = Profile.SmokingPreference.SMOKE;
        testProf.UserPreferredContactMethod = Profile.PreferredContactMethod.TEXT;
        testProf.UserSex = Profile.Sex.FEMALE;

        // Update the emergency contact info
        testProf.EmergencyContactInfo.ContactName = "UpdatedContact";
        testProf.EmergencyContactInfo.ContactNumber = 9999999;

        assertTrue(testProf.UserSmokingPreference == Profile.SmokingPreference.SMOKE);
        assertTrue(testProf.UserPreferredContactMethod == Profile.PreferredContactMethod.TEXT);
        assertTrue(testProf.UserSex == Profile.Sex.FEMALE);
        assertTrue(testProf.EmergencyContactInfo.ContactNumber == 9999999);
        assertTrue(testProf.EmergencyContactInfo.ContactName == "UpdatedContact");
    }

    // TC 22 - Update Profile - Cancel Update
    @SmallTest
    public void Test_UpdateProfile_CancelUpdate()
    {
        assertTrue(false);
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
