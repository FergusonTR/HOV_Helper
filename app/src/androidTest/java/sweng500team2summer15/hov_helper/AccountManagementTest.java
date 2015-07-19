package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sweng500team2summer15.hov_helper.Account.AccountManagement;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagementTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    // SignIn
    // TC-03.1
    @SmallTest
    public void test_signIn_Success() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";
        String password = "Sweng_500";

        String result = am.signIn(email, password);
        assertNotNull(result);
        assertEquals("Success", result);
    }

    public void test_signIn_Disabled() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_disabled@hovhelper.com";
        String password = "Sweng_500";

        String result = am.signIn(email, password);
        assertNotNull(result);
        assertEquals("Error: Account Not Enabled", result);
    }

    // TC-03.2
    @SmallTest
    public void test_signIn_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";
        String password = "Sweng_500";

        String result = am.signIn(email, password);
        assertNotNull(result);
        assertEquals("Error: Invalid Login/Password", result);
    }

    @SmallTest
    public void test_signIn_NotAEmail() {
        AccountManagement am = new AccountManagement();
        String email = "team_2";
        String password = "sweng50";

        String result = am.signIn(email, password);
        assertNotNull(result);
        assertEquals("Error: Not a valid email address", result);
    }

    // TC-04
    @SmallTest
    public void test_signIn_BadPassword() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";
        String password = "sweng50";

        String result = am.signIn(email, password);
        assertNotNull(result);
        assertEquals("Error: Invalid Login/Password", result);
    }

    // SignUp
    // TC-05.1 - success
    @SmallTest
    public void test_signUp_Success() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_test@hovhelper.com";
        String password = "Sweng_500";

        String result = am.signUp(email, password);
        assertNotNull(result);
        assertEquals("Success", result);

        // url to delete test user
        String url_verify = "http://www.hovhelper.com/delete_test_user.php";
        final JSONParser jsonParser = new JSONParser();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("login", email));

        jsonParser.makeHttpRequest(url_verify, "POST", params);
    }
    // TC-05.2 - User exists
    @SmallTest
    public void test_signUp_LoginExists() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";
        String password = "Sweng_500";

        String result = am.signUp(email, password);
        assertNotNull(result);
        assertEquals("Error: Login already exists", result);
    }

    @SmallTest
    public void test_signUp_NotAEmail() {
        AccountManagement am = new AccountManagement();
        String email = "team_2";
        String password = "sweng50";

        String result = am.signUp(email, password);
        assertNotNull(result);
        assertEquals("Error: Not a valid email address", result);
    }


    @SmallTest
    public void test_signUp_PasswordTooShort() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";
        String password = "sweng";

        String result = am.signUp(email, password);
        assertNotNull(result);
        assertEquals("Error: Please enter a password with more than 6 characters", result);
    }

    // Verify Account
    @SmallTest
    public void test_verifyAccount_Success() {
        AccountManagement am = new AccountManagement();
        String verificationCode = "752750";
        String login = "team_2@hovhelper.com";

        String result = am.verifyAccount(login, verificationCode);
        assertNotNull(result);
        assertEquals("Success", result);
    }

    @SmallTest
    public void test_verifyAccount_CodeNotAccepted() {
        AccountManagement am = new AccountManagement();
        String verificationCode = "000000";
        String login = "team_2@hovhelper.com";

        String result = am.verifyAccount(login, verificationCode);
        assertNotNull(result);
        assertEquals("Error: Verification code not accepted", result);
    }

    @SmallTest
    public void test_verifyAccount_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String verificationCode = "000000";
        String login = "team2@hovhelper.com";

        String result = am.verifyAccount(login, verificationCode);
        assertNotNull(result);
        assertEquals("Error: Verification code not accepted", result);
    }

    // Reset Password
    @SmallTest
    public void test_resetPassword_Success() {
        AccountManagement am = new AccountManagement();
        String email = "reset@hovhelper.com";

        String result = am.resetPassword(email);
        assertNotNull(result);
        assertEquals("Success", result);
    }

    @SmallTest
    public void test_resetPassword_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";

        String result = am.resetPassword(email);
        assertNotNull(result);
        assertEquals("Error: Password not reset", result);
    }

    // Resend Verification Code
    @SmallTest
    public void test_resendVerificationCode_Success() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_disabled@hovhelper.com";

        String result = am.resendVerificationCode(email);
        assertNotNull(result);
        assertEquals("Success", result);
    }

    @SmallTest
    public void test_resendVerificationCode_UserAlreadyEnabled() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";

        String result = am.resendVerificationCode(email);
        assertNotNull(result);
        assertEquals("Error: User is already enabled", result);
    }


    @SmallTest
    public void test_resendVerificationCode_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";

        String result = am.resendVerificationCode(email);
        assertNotNull(result);
        assertEquals("Error: User not found", result);
    }

    // Change Password
    @SmallTest
    public void test_changePassword_currentPwIncorrect() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_changepw@hovhelper.com";
        String oldPassword = "sweng50";
        String newPassword = "test500";
        String reenterPassword = "test500";

        String result = am.changePassword(email, oldPassword, newPassword, reenterPassword);
        assertNotNull(result);
        assertEquals("Error: Current password incorrect", result);
    }

    @SmallTest
    public void test_changePassword_newPasswordsDoNotMatch() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_changepw@hovhelper.com";
        String oldPassword = "sweng500";
        String newPassword = "test500";
        String reenterPassword = "test50";

        String result = am.changePassword(email, oldPassword, newPassword, reenterPassword);
        assertNotNull(result);
        assertEquals("Error: New passwords do not match", result);
    }

    @SmallTest
    public void test_changePassword_newPasswordsDoNotMatch2() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_changepw@hovhelper.com";
        String oldPassword = "sweng500";
        String newPassword = "test50";
        String reenterPassword = "test500";

        String result = am.changePassword(email, oldPassword, newPassword, reenterPassword);
        assertNotNull(result);
        assertEquals("Error: New passwords do not match", result);
    }

    @SmallTest
    public void test_changePassword_passwordTooShort() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_changepw@hovhelper.com";
        String oldPassword = "sweng500";
        String newPassword = "test5";
        String reenterPassword = "test5";

        String result = am.changePassword(email, oldPassword, newPassword, reenterPassword);
        assertNotNull(result);
        assertEquals("Error: Please enter a password with more than 6 characters", result);
    }

    @SmallTest
    public void test_changePassword_samePasswords() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_changepw@hovhelper.com";
        String oldPassword = "sweng500";
        String newPassword = "sweng500";
        String reenterPassword = "sweng500";

        String result = am.changePassword(email, oldPassword, newPassword, reenterPassword);
        assertNotNull(result);
        assertEquals("Error: The new password cannot be the same as the current password", result);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
