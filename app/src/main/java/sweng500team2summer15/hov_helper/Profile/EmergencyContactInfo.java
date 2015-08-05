package sweng500team2summer15.hov_helper.Profile;

/**
 * Created by Mike on 6/7/2015.
 */
public class EmergencyContactInfo
{
    // 'Struct' to hold emergency contact info
    public String ContactName = "";
    public String ContactNumber = "";
    public EmergencyContactInfo(String contactName, String contactNumber)
    {
        this.ContactName = contactName;
        this.ContactNumber = contactNumber;
    }
}
