package vanvyven.ade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class GlobalSettings extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_settings);

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Settings application");

        Switch sw = findViewById(R.id.tuto);
        if (mPrefs.getBoolean(MPREF_TUTO, true)) {
            sw.setChecked(false);
        } else {
            sw.setChecked(true);
        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putBoolean(MPREF_TUTO, !mPrefs.getBoolean(MPREF_TUTO, true)).apply();
            }
        });

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialogBox();
            }
        });

        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedInternet(GlobalSettings.this)){
                    if ((!check_version())){
                            updatebox();
                    }else {
                        Toast.makeText(getApplicationContext(),"Application à jour",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), R.string.internetconnected,Toast.LENGTH_LONG).show();
                }
            }
        });

        Button projet = findViewById(R.id.projectid);
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GlobalSettings.this, DIALOG_THEME);
                } else {
                    builder = new AlertDialog.Builder(GlobalSettings.this);
                }
                builder.setTitle("Le projet ADE c'est quoi?")
                        .setMessage("Le numéro du projet permet d'afficher la bonne année académique (1 pour 2017-18, 2 pour 2018-19).\n" +
                                "Normalement il est possible de mettre à jour automatiquement ce code en utilisant le bouton prévu çà cet effet.\n" +
                                "Si cela ne fonctionne pas il est toujours possible d'entrer soi-même le code. On peut facilement le trouver quand on consulte ADE sur un ordinateur.\n" +
                                "Il se trouve dans l'URL, après le signe \"=\" dans \"projectId=\"")
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .show();
            }
        });

        Button share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                ImageView view1 = new ImageView(GlobalSettings.this);
                view1.setImageResource(R.drawable.readmelink);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GlobalSettings.this, DIALOG_THEME);
                } else {
                    builder = new AlertDialog.Builder(GlobalSettings.this);
                }
                builder.setView(view1)
                        .show();

            }
        });
    }



    private void showDialogBox() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Reset")
                .setMessage("Êtes-vous sûr de vouloir supprimer toute les données de l'application?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPrefs.edit().clear().apply();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

        builder.setIcon(android.R.drawable.ic_menu_delete);
        builder.show();
    }

}
