package vanvyven.ade;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;

import vanvyven.ade.Tuto.SwipeTuto;

public class MainActivity extends BasicActivity {
    LinearLayout ll;
    int indexCounter = 0;
    int index_max;

    SharedPreferences.Editor editor;

    String TAG = "MAIN";
    private ArrayList<Button> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CRASHLYTICS_ENABLE) {
            Fabric.with(this, new Crashlytics());        }

        setContentView(R.layout.activity_main);

        if(!mPrefs.getBoolean(MPREF_TUTO, false)){
            startActivity(new Intent(this,SwipeTuto.class));
        }

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("ADE Menu");

        ll = findViewById(R.id.layout_option);
        list = new ArrayList<>();

        editor = mPrefs.edit();
        index_max = mPrefs.getInt(MPREF_INDEX_MAX_MEMO,0);

        do {
            add_new(indexCounter);
        }while (indexCounter<index_max);

        if (isConnectedInternet(this)){
            if ((!check_version())){
                int count = mPrefs.getInt(MPREF_COUNTDOWN,0);
                if(count==0){
                    updatebox();
                    count = 20;
                }else {
                    count--;
                }
                editor.putInt(MPREF_COUNTDOWN,count).apply();
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        for (Button b : list){
            int index = list.indexOf(b);
            String name = mPrefs.getString(MPREF_PROJECT_NAME_INDEX_MISSING+index,"ADE "+(index+1));
            if (name.equals("")){
                name = "ADE "+(index+1);
            }
            b.setText(name);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.add_bar:
                add_new(indexCounter);

                return super.onOptionsItemSelected(item);
            case R.id.cred_bar:
                credit();
                return super.onOptionsItemSelected(item);
            case R.id.set_bar:
                Intent s = new Intent(MainActivity.this, GlobalSettings.class);
                startActivity(s);
                return super.onOptionsItemSelected(item);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void credit() {

        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, DIALOG_THEME);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        final SpannableString s = new SpannableString("Application créée par Nicolas Vanvyve en juillet 2018.\nCode disponible sur GitHub :\nhttps://github.com/NVanvyve/App-ADE");
        Linkify.addLinks(s, Linkify.ALL);

        builder.setTitle("Crédits")
                .setMessage(s)
                .setIcon(android.R.drawable.ic_dialog_info);
        AlertDialog d = builder.create();

        d.show();

        // Make the textview clickable. Must be called after show()
        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void add_new(final int index) {
        LinearLayout lin = new LinearLayout(this);
        lin.setOrientation(LinearLayout.HORIZONTAL);
        lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        Button ade = new Button(this);
        list.add(ade);
        String name = mPrefs.getString(MPREF_PROJECT_NAME_INDEX_MISSING+index,"ADE "+(index+1));
        if (name.equals("")){
            name = "ADE "+(index+1);
        }
        ade.setText(name);
        ade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageButton settings = new ImageButton(this);
        settings.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        settings.setImageResource(R.mipmap.ic_settings_foreground);

        ade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this, ADEActivity.class);
                s.putExtra(INTENT_INDEX,index);
                startActivity(s);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this, SettingsActivity.class);
                s.putExtra(INTENT_INDEX,index);
                startActivity(s);
            }
        });

        final String finalName = name;
        ade.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialogBox(index,finalName);
                return true;
            }
        });

        settings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialogBox(index,finalName);
                return true;
            }
        });


        lin.addView(settings);
        lin.addView(ade);

        ll.addView(lin);
        indexCounter++;

        if (indexCounter>index_max) {
            index_max++;
            editor.putInt(MPREF_INDEX_MAX_MEMO, index_max).apply();
        }
    }

    private void showDialogBox(final int i, String name) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, DIALOG_THEME);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(name);
        if (i==0){
            builder.setMessage("Impossible de supprimer le 1er element")
                    .setCancelable(true)
                    .setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    });
        }else {
            builder.setMessage("Etes vous sur de vouloir supprimer cet horaire?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            delete(i);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    });
        }

        builder.setIcon(android.R.drawable.ic_menu_delete);
        builder.show();
    }

    private void delete(int i){

        myLog(TAG,"indexCounter  "+indexCounter);
        myLog(TAG,"i  "+i);
        myLog(TAG,"index_max  "+index_max);

        index_max--;
        indexCounter--;
            for(int j=i;j<index_max;j++){
                editor.putString(MPREF_PROJECT_NBR_INDEX_MISSING+j,mPrefs.getString(MPREF_PROJECT_NBR_INDEX_MISSING+(j+1),""));
                editor.putString(MPREF_PROJECT_NAME_INDEX_MISSING+j,mPrefs.getString(MPREF_PROJECT_NAME_INDEX_MISSING+(j+1),""));
                editor.putString(MPREF_CODES_COURS_INDEX_MISSING+j,mPrefs.getString(MPREF_CODES_COURS_INDEX_MISSING+(j+1),""));
                editor.apply();
            }
            editor.remove(MPREF_CODES_COURS_INDEX_MISSING+indexCounter);
            editor.remove(MPREF_PROJECT_NAME_INDEX_MISSING+indexCounter);
            editor.remove(MPREF_PROJECT_NBR_INDEX_MISSING+indexCounter);
            ll.removeView((View)list.get(indexCounter).getParent());
            list.remove(indexCounter);
        editor.putInt(MPREF_INDEX_MAX_MEMO,index_max);
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}