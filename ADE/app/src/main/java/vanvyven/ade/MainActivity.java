package vanvyven.ade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BasicActivity {
    LinearLayout ll;

    String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CRASHLYTICS_ENABLE) {
            Fabric.with(this, new Crashlytics());
            logUser();

        }
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("ADE Menu");

        LinearLayout ll = findViewById(R.id.layout_option);

        Button ade = findViewById(R.id.ade_button);
        ade.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                mPrefs.edit().putString(MPREF_CODES_COURS_INDEX_MISSING+"0","LELEC2531,LINGI2365,LINGI2132,LINGI2172,LINGI2241,LINGI2255,LINGI2261,LINGI2347,LINGI2252,LINGI2266,LFSAB1105,LFSA1290").apply();
                Intent s = new Intent(MainActivity.this, ADEActivity.class);
                s.putExtra(INTENT_INDEX,0);
                startActivity(s);
            }
        });

        ImageButton settings = findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this, SettingsActivity.class);
                s.putExtra(INTENT_INDEX,0);
                startActivity(s);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.add_bar:
                myLog(TAG,"ADD MAIN");
//                Toast.makeText(this,"Add",Toast.LENGTH_LONG).show();
//                add_new();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void add_new() {
        LinearLayout lin = new LinearLayout(this);
        lin.setOrientation(LinearLayout.HORIZONTAL);
        lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        Button new_she = new Button(this);
        new_she.setText("NEW ADE");
        new_she.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageButton im_but = new ImageButton(this);
        im_but.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        im_but.setImageResource(R.mipmap.ic_launcher_round);

        lin.addView(new_she);
        lin.addView(im_but);

        ll.addView(lin);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}