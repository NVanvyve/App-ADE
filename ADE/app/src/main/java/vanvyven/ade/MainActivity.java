package vanvyven.ade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ade = (Button) findViewById(R.id.ade_button);
        ade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this,ADEActivity.class);
                startActivity(s);
            }
        });

        Button settings = (Button) findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(s);
            }
        });
    }



}
