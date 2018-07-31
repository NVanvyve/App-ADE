package vanvyven.ade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Nicolas Vanvyve on 13/02/2018.
 */

public class BasicActivity extends AppCompatActivity {

    protected Toolbar myToolbar;
    public static SharedPreferences mPrefs;
    protected final boolean CRASHLYTICS_ENABLE = true;

    protected final String MPREF_CODES_COURS_INDEX_MISSING = "codes_";
    protected final String MPREF_PROJECT_NBR_INDEX_MISSING = "project_";
    protected final String MPREF_PROJECT_NAME_INDEX_MISSING = "hor_name_";
    protected final String INTENT_INDEX = "index";
    protected final String MPREF_INDEX_MAX_MEMO = "index_memo";
    protected final String MPREF_COUNTDOWN = "coutndown";
    public static final String MPREF_TUTO = "tuto";
    protected final int DIALOG_THEME = android.R.style.Theme_Material_Dialog_Alert;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("ADE_memory", 0);
    }

    public static void myLog(String tag, String msg){
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

    protected void style_ed(EditText ed) {
        ed.setTextColor(getResources().getColor(R.color.colorEditText_text));//BLACK
        ed.setHintTextColor(getResources().getColor(R.color.colorEditText_hint));
        ed.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        ed.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    }

    protected void updatebox() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Mise à jour nécessaire")
                .setMessage("Votre version de l'application n'est pas la dernière disponible.\nCette version de l'application c'est plus garantie.\nVoulez vous télécharger la nouvelle version?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "https://github.com/NVanvyve/App-ADE/blob/master/README.md";
                        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.stat_sys_download_done)
                .show();
    }

    protected boolean check_version(){
        myAsyncTask task = new myAsyncTask();
        String html = "";
        task.execute("https://raw.githubusercontent.com/NVanvyve/App-ADE/master/ADE/app/build.gradle");
        try {
            html = task.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        Pattern p = Pattern.compile("versionCode [0-9]*");
        Matcher m = p.matcher(html);
        String code = "";
        while (m.find()) {
            code = m.group();
        }
        int vcode = Integer.parseInt(code.split(" ")[1]);

        return (BuildConfig.VERSION_CODE >= vcode);
    }

}