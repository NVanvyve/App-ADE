package vanvyven.ade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ADEActivity extends BasicActivity {

    String TAG = "ADE";
    int index;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ade);
        index = getIntent().getExtras().getInt(INTENT_INDEX,-1);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        String title = mPrefs.getString(MPREF_PROJECT_NAME_INDEX_MISSING+index,null);
        if (title==null || title.equals("") ||title.equals("ADE "+(index+1))) {
            getSupportActionBar().setTitle("ADE");
        } else {
            getSupportActionBar().setTitle("ADE : "+title);
        }
        if(!isConnectedInternet(ADEActivity.this)){
            Toast.makeText(getApplicationContext(),"You're not connected to the internet",Toast.LENGTH_LONG).show();
        }

        String codes = mPrefs.getString(MPREF_CODES_COURS_INDEX_MISSING+index,"");
        if (codes.equals("")){
            Toast.makeText(this,"No courses",Toast.LENGTH_LONG).show();
        }
        URLGenerator urlGenerator = URLGenerator.getInstance();
        urlGenerator.setCodes(codes);
        urlGenerator.setProjet(mPrefs.getString(MPREF_PROJECT_NBR_INDEX_MISSING+index,"1"));
        String url = urlGenerator.getUrl();

        myLog(TAG,"URL = "+url);

        WebView wv = findViewById(R.id.ade_view);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        return super.onOptionsItemSelected(item);
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }

}
