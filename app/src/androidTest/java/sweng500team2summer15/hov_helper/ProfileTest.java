package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import sweng500team2summer15.hov_helper.Profile.EmergencyContactInfo;
import sweng500team2summer15.hov_helper.Profile.Profile;

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
    public void test_CreateProfile()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"Test", "Test", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Store in DB
        testProf.SubmitProfile();

        // Retrieve profile from DB
    }

    // TC 12 - Profile - required fields are blank
    @SmallTest
    public void test_CreateProfile_Blank()
    {
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("", 0);
        Profile testProf = new Profile(0, "","", Profile.Sex.MALE, 0, Profile.PreferredContactMethod.CALL, "", testEmergency, Profile.SmokingPreference.NONSMOKE);

        // Assert that the following values are not the default (empty) values
        assertTrue(!testProf.UserFirstName.equals(""));
        assertTrue(!testProf.UserLastName.equals(""));
        assertTrue(!testProf.EmailAddress.equals(""));
        assertTrue(!testProf.EmergencyContactInfo.ContactName.equals(""));
        assertTrue(testProf.EmergencyContactInfo.ContactNumber != 0);
        assertTrue(testProf.PhoneNumber != 0);
    }

    // TC 13 - Update Profile - First Name
    @SmallTest
    public void test_UpdateProfile_FirstName()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"InitialName", "Test", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Update the First Name property
        testProf.UserFirstName = "UpdatedName";

        assertTrue(testProf.UserFirstName.equals("UpdateName"));
    }

    // TC 14 - Update Profile - Last Name
    @SmallTest
    public void test_UpdateProfile_LastName()
    {
        // Setup Profile object
        EmergencyContactInfo testEmergency = new EmergencyContactInfo("TestContact", 5555555);
        Profile testProf = new Profile(1,"InitialName", "InitialName", Profile.Sex.MALE, 5555555, Profile.PreferredContactMethod.CALL, "Test@Test.Com", testEmergency ,Profile.SmokingPreference.NONSMOKE);

        // Update the First Name property
        testProf.UserLastName = "UpdatedName";

        assertTrue(testProf.UserLastName.equals("UpdateName"));
    }

    // TC 15 - Update Profile - Home Location
    @SmallTest
    public void test_UpdateProfile_HomeLocation()
    {
        assertTrue(false);
    }

    // TC 16 - Update Profile - Work Location
    @SmallTest
    public void test_UpdateProfile_WorkLocation()
    {
        assertTrue(false);
    }

    // TC 17 - Update Profile - Carpool Preferences
    @SmallTest
    public void test_UpdateProfile_CarpoolPreference()
    {
        assertTrue(false);
    }

    // TC 18 - Update Profile - Frequency Preferences
    @SmallTest
    public void test_UpdateProfile_Frequency()
    {
        assertTrue(false);
    }

    // TC 19 - Update Profile - Pickup to work time
    @SmallTest
    public void test_UpdateProfile_PickupTime_ToWork()
    {
        assertTrue(false);
    }

    // TC 20 - Update Profile - pickup from work
    @SmallTest
    public void test_UpdateProfile_PickupTime_FromWork()
    {
        assertTrue(false);
    }

    // TC 21 - Update Profile - Other Preferences
    @SmallTest
    public void test_UpdateProfile_OtherPreferences()
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
        assertTrue(testProf.EmergencyContactInfo.ContactName.equals("UpdatedContact"));
    }

    // TC 22 - Update Profile - Cancel Update
    @SmallTest
    public void test_UpdateProfile_CancelUpdate()
    {
        assertTrue(false);
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
