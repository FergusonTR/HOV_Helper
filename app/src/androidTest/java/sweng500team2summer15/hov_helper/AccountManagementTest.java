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

    // TODO - redo tests based on new design

    // TC-03.1
    @SmallTest
    public void test_SignIn_Success() {
        AccountManagement am = new AccountManagement();
        String username = "team_2";
        String password = "Sweng_500";

        assertEquals(am.signIn(username, password), 0);
    }
    // TC-03.2
    @SmallTest
    public void test_SignIn_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String username = "team2";
        String password = "Sweng_500";

        assertEquals(am.signIn(username, password), 1);
    }
    // TC-04
    @SmallTest
    public void test_SignIn_BadPassword() {
        AccountManagement am = new AccountManagement();
        String username = "team_2";
        String password = "sweng50";

        assertEquals(am.signIn(username, password), 1);
    }

    // TODO - redo tests based on new design

    // TC-05.1 - success
    @SmallTest
    public void test_SignUp_Success() {
        AccountManagement am = new AccountManagement();
        String username = "team2";
        String password = "Sweng_500";

        assertEquals(am.signUp(username, password), 0);
    }
    // TC-05.2 - User exists
    @SmallTest
    public void test_SignUp_UserExists() {
        AccountManagement am = new AccountManagement();
        String username = "team_2";
        String password = "Sweng_500";

        assertEquals(am.signUp(username, password), 1);
    }

    @SmallTest
    public void test_SignUp_EmailExists() {
        AccountManagement am = new AccountManagement();
        String username = "team2";
        String password = "Sweng_500";

        assertEquals(am.signUp(username, password), 1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //delete test sign-up accounts (ie, username: "team2", email; "team2_sweng500@psu.edu")
    }
}
