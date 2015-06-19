package sweng500team2summer15.hov_helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Edward J. Crishock on 6/18/2015.
 */
public class ServerRequests {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://hovhelper.com";

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void storeLoginDataInBackground(Login login, GetUserCallback callback) {
        progressDialog.show();
        new StoreLoginDataAsyncTask(login, callback).execute();
    }

    public void fetchLoginDataInBackground(Login login, GetUserCallback callback) {
        progressDialog.show();
        new fetchLoginDataAsyncTask(login, callback);
    }

    public class StoreLoginDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Login login;
        GetUserCallback callback;

        public StoreLoginDataAsyncTask(Login login, GetUserCallback callback) {
            this.login = login;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Date d = new Date();
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("LoginId", login.login));
            dataToSend.add(new BasicNameValuePair("Password", login.password));
            dataToSend.add(new BasicNameValuePair("TimeCreated", d.toString()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "register.php");

            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            callback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchLoginDataAsyncTask extends AsyncTask<Void, Void, Login> {
        Login login;
        GetUserCallback callback;

        public fetchLoginDataAsyncTask(Login login, GetUserCallback callback) {
            this.login = login;
            this.callback = callback;
        }

        @Override
        protected Login doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("LoginId", login.login));
            dataToSend.add(new BasicNameValuePair("Password", login.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "fetch_login_data.php");

            Login returnedLogin = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0){
                    returnedLogin = null;
                }
                else {
                    returnedLogin = new Login(login.login, login.password);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return returnedLogin;
        }

        @Override
        protected void onPostExecute(Login returnedLogin) {
            progressDialog.dismiss();
            callback.done(null);
            super.onPostExecute(returnedLogin);
        }
    }
}