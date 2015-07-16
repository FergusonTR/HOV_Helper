package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

import sweng500team2summer15.hov_helper.Account.AccountManagement;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagementTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    // TC-03.1
    @SmallTest
    public void test_signIn_Success() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";
        String password = "Sweng_500";

        assertNull(am.signIn(email, password));
        //assertEquals(am.signIn(email, password), null);
    }

    public void test_signIn_Disabled() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_disabled@hovhelper.com";
        String password = "Sweng_500";

        assertNotNull(am.signIn(email, password));
        assertEquals(am.signIn(email, password), "Invalid Login/Password");
    }

    // TC-03.2
    @SmallTest
    public void test_signIn_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";
        String password = "Sweng_500";

        assertNotNull(am.signIn(email, password));
        assertEquals(am.signIn(email, password), "Invalid Login/Password");
    }

    @SmallTest
    public void test_signIn_NotAEmail() {
        AccountManagement am = new AccountManagement();
        String email = "team_2";
        String password = "sweng50";

        assertNotNull(am.signIn(email, password));
        assertEquals(am.signIn(email, password), "Not a valid email address");
    }

    // TC-04
    @SmallTest
    public void test_signIn_BadPassword() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";
        String password = "sweng50";

        assertNotNull(am.signIn(email, password));
        assertEquals(am.signIn(email, password), "Invalid Login/Password");
    }

    // TC-05.1 - success
    @SmallTest
    public void test_signUp_Success() {
        AccountManagement am = new AccountManagement();
        String email = "team_2_test@hovhelper.com";
        String password = "Sweng_500";

        assertNull(am.signUp(email, password));
        //assertEquals(am.signUp(email, password), null);

        // TODO - delete user after test sign up
    }
    // TC-05.2 - User exists
    @SmallTest
    public void test_signUp_LoginExists() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";
        String password = "Sweng_500";

        assertNotNull(am.signUp(email, password));
        assertEquals(am.signUp(email, password), "Login already exists");
    }

    @SmallTest
    public void test_signUp_NotAEmail() {
        AccountManagement am = new AccountManagement();
        String email = "team_2";
        String password = "sweng50";

        assertNotNull(am.signUp(email, password));
        assertEquals(am.signUp(email, password), "Not a valid email address");
    }

    @SmallTest
    public void test_verifyAccount_Success() {
        AccountManagement am = new AccountManagement();
        String verificationCode = "566273";

        assertNull(am.verifyAccount(verificationCode));
    }

    @SmallTest
    public void test_verifyAccount_CodeDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String verificationCode = "000000";

        assertNotNull(am.verifyAccount(verificationCode));
        assertEquals(am.verifyAccount(verificationCode), "Verification code not accepted");
    }

    @SmallTest
    public void test_resetPassword_Success() {
        AccountManagement am = new AccountManagement();
        String email = "reset@hovhelper.com";

        assertNull(am.resetPassword(email));
    }

    @SmallTest
    public void test_resetPassword_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";

        assertNotNull(am.resetPassword(email));
        assertEquals(am.resetPassword(email), "Password not reset");
    }

    @SmallTest
    public void test_resendVerificationCode_Success() {
        AccountManagement am = new AccountManagement();
        String email = "team_2@hovhelper.com";

        assertNull(am.resendVerificationCode(email));
    }

    @SmallTest
    public void test_resendVerificationCode_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String email = "team2@hovhelper.com";

        assertNotNull(am.resendVerificationCode(email));
        assertEquals(am.resendVerificationCode(email), "User not found");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //delete test sign-up accounts (ie, username: "team2", email; "team2_sweng500@psu.edu")
    }
}
