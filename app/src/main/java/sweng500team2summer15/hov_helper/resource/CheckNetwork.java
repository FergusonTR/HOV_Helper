package sweng500team2summer15.hov_helper.resource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Terry on 8/5/2015.
 */
public class CheckNetwork {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connMan=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=connMan.getActiveNetworkInfo();
        if (network == null || !network.isConnected()) {
              return false;
        }
        return true;
    }

}
