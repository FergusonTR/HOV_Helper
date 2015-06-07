package sweng500team2summer15.hov_helper;

/**
 * Created by Mike on 6/7/2015.
 */
public class EmergencyContactInfo
{
    // 'Struct' to hold emergency contact info
    public String ContactName = "";
    public int ContactNumber = 0;
    public EmergencyContactInfo(String contactName, int contactNumber)
    {
        this.ContactName = contactName;
        this.ContactNumber = contactNumber;
    }
}
