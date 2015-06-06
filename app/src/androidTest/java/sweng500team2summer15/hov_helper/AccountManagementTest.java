package sweng500team2summer15.hov_helper;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;

/**
 * Created by Edward J. Crishock on 6/6/2015.
 */
public class AccountManagementTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    // SignIn tests
    // good username: "team_2"
    // good password: "Sweng_500"

    // TC-03
    @SmallTest
    public void Test_SignIn_Success() {
        AccountManagement am = new AccountManagement();
        String username = "team_2";
        String password = "Sweng_500";

        assertEquals(am.SignIn(username, password), 0);
    }

    @SmallTest
    public void Test_SignIn_UserDoesNotExist() {
        AccountManagement am = new AccountManagement();
        String username = "team2";
        String password = "Sweng_500";

        assertEquals(am.SignIn(username, password), 1);
    }

    @SmallTest
    public void Test_SignIn_BadPassword() {
        AccountManagement am = new AccountManagement();
        String username = "team_2";
        String password = "sweng50";

        assertEquals(am.SignIn(username, password), 1);
    }

    // SignUp tests
    // good username: "team2"
    // good email: "team2sweng500@psu.edu"
    // existing user: "team_2"
    // existing email: "team2_sweng500@psu.edu"

    // TC-04
    @SmallTest
    public void Test_SignUp_Success() {
        AccountManagement am = new AccountManagement();
        String username = "team2";
        String password = "Sweng_500";
        String email = "team2sweng500@psu.edu";

        assertEquals(am.SignUp(username, password, email), 0);
    }

    @SmallTest
    public void Test_SignUp_UserExists() {
        AccountManagement am = new AccountManagement();
        String username = "team_2";
        String password = "Sweng_500";
        String email = "team2sweng500@psu.edu";

        assertEquals(am.SignUp(username, password, email), 1);
    }

    @SmallTest
    public void Test_SignUp_EmailExists() {
        AccountManagement am = new AccountManagement();
        String username = "team2";
        String password = "Sweng_500";
        String email = "team2_sweng500@psu.edu";

        assertEquals(am.SignUp(username, password, email), 1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //delete test sign-up accounts (ie, username: "team2", email; "team2_sweng500@psu.edu")
    }
}
