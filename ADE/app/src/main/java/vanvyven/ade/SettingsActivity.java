package vanvyven.ade;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;

public class SettingsActivity extends BasicActivity {

    EditText project_nbr;
    EditText hor_name;
    int index;
    String TAG = "SETTINGS";
    LinkedList<EditText> memory;
    LinearLayout lc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        index = getIntent().getExtras().getInt(INTENT_INDEX,-1);
        myLog(TAG,"index = "+ index);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Settings");
//        myToolbar.inflateMenu();

        final URLGenerator urlGenerator = URLGenerator.getInstance();
        myLog(TAG,MPREF_CODES_COURS_INDEX_MISSING+index);
        String cours = mPrefs.getString(MPREF_CODES_COURS_INDEX_MISSING+index,"");
        myLog(TAG,"Codes : "+cours);
        final String project = mPrefs.getString(MPREF_PROJECT_NBR_INDEX_MISSING+index,"1");
        myLog(TAG,"Projet : "+project);

//        cours_i = findViewById(R.id.cours1);
//        cours_i.setText(cours);


        project_nbr = findViewById(R.id.project_nbr);
        project_nbr.setText(project);
        style_ed(project_nbr);
        project_nbr.setInputType(InputType.TYPE_CLASS_NUMBER);

        hor_name = findViewById(R.id.hor_name);
        hor_name.setText(mPrefs.getString(MPREF_PROJECT_NAME_INDEX_MISSING+index,"ADE "+(index+1)));
        style_ed(hor_name);

        // Projet
        Button update = findViewById(R.id.project_update);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isConnectedInternet(SettingsActivity.this)){
                    Toast.makeText(getApplicationContext(),"Online update",Toast.LENGTH_LONG).show();
                    String update_nbr = urlGenerator.getProjet();
                    project_nbr.setText(update_nbr);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You're not connected to the internet",Toast.LENGTH_LONG).show();
                }

            }
        });


        // Cours
        lc = findViewById(R.id.layout_cours);
        memory = new LinkedList<>();

        if (cours.length() == 0) {
            addCourse(lc);
        }
        else{
            String [] cours_tab = cours.split(",");
            for (String str : cours_tab){
                addCourse(lc,str);
            }
        }

    }

    void addCourse(LinearLayout lc,String text){
        final EditText ed = new EditText(getApplicationContext());
        ed.setHint("Code de cours");
        if (text!=null) {
            ed.setText(text);
        }
        style_ed(ed);
        lc.addView(ed);
        memory.add(ed);
    }

    void addCourse(LinearLayout lc){
        addCourse(lc,null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.add_bar:
                myLog(TAG,"ADD COURSE");
                addCourse(lc);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(MPREF_PROJECT_NBR_INDEX_MISSING+index,project_nbr.getText().toString());
        editor.putString(MPREF_PROJECT_NAME_INDEX_MISSING+index,hor_name.getText().toString());

        StringBuffer sb = new StringBuffer();
        while (!memory.isEmpty()){
            EditText temp = memory.pop();
            String content = temp.getText().toString();
            if (content.matches("^[a-zA-Z0-9_]+$") || content.equals("")) {
                if (!temp.getText().toString().equals("")) {
                    myLog(TAG,temp.getText().toString());
                    sb.append(temp.getText().toString());
                    sb.append(",");
                }
            } else {
                Toast.makeText(getApplicationContext(),"Erreur format",Toast.LENGTH_SHORT).show();
                myLog(TAG,"Erreur format : "+temp.getText().toString());
            }
        }
        if(sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        editor.putString(MPREF_CODES_COURS_INDEX_MISSING+index,sb.toString());

        editor.apply();
        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }

}
