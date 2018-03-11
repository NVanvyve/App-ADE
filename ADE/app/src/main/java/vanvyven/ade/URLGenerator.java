package vanvyven.ade;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nicolas Vanvyve on 13/02/2018.
 */

public class URLGenerator{

    private String url;
    private String projet;
    private String codes;
    final String TAG = "URL";
    private static URLGenerator urlGenerator = null;

    public static URLGenerator getInstance(){
        if (urlGenerator == null ){
            urlGenerator = new URLGenerator();
        }
        return urlGenerator;
    }

    private URLGenerator() {
        codes = "";
        projet = "";
        url = "";
    }

    void project() {
        String html = "";

        myAsyncTask task = new myAsyncTask();
        task.execute("http://ucl2ics.appspot.com/ucl2ics?login=false");
        try {
            html = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("((id=\"projet\" value=\")([0-9]*))");
        Matcher m = p.matcher(html);
        String code = "";
        while (m.find()) {
            code = m.group();
        }
        this.projet = code.split("value=\"")[1];
        BasicActivity.myLog(TAG,"projet : "+this.projet);

    }

    String getUrl() {
        if (!codes.equals("")) {
            url = "http://horaire.uclouvain.be/direct//index.jsp?displayConfName=webEtudiant&showTree=false&showOptions=false&login=etudiant&password=student&projectId=" + projet + "&code="
                    + codes + "&weeks=";
        }
        return url;
    }

    String getProjet() {
        project();
        return projet;
    }

    void setProjet(String projet) {
        this.projet = projet;
    }

    String getCodes() {
        return codes;
    }

    void setCodes(String cours) {
        this.codes = cours;
    }
}



