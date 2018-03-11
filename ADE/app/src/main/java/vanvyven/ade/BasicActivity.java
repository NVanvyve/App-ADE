package vanvyven.ade;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

/**
 * Created by Nicolas Vanvyve on 13/02/2018.
 */

@SuppressLint("Registered")
public class BasicActivity extends AppCompatActivity {

    protected Toolbar myToolbar;
    protected SharedPreferences mPrefs;
    protected final boolean CRASHLYTICS_ENABLE = false;

    protected final String MPREF_CODES_COURS_INDEX_MISSING = "codes_";
    protected final String MPREF_PROJECT_NBR_INDEX_MISSING = "project_";
    protected final String INTENT_INDEX = "index";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("ADE_memory", 0);
    }

    protected static void myLog(String tag, String msg){
        if(BuildConfig.DEBUG) {
            Log.e(tag,msg);
        }
    }

    protected static boolean isConnectedInternet(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
        {
            NetworkInfo.State networkState = networkInfo.getState();
            return networkState.compareTo(NetworkInfo.State.CONNECTED) == 0;
        }
        else return false;
    }

    protected void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier("12345");
//        Crashlytics.setUserEmail("user@fabric.io");
//        Crashlytics.setUserName("Test User");
    }

    protected void style_ed(EditText ed) {
        ed.setTextColor(getResources().getColor(R.color.colorEditText_text));//BLACK
        ed.setHintTextColor(getResources().getColor(R.color.colorEditText_hint));
        ed.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

    }

}