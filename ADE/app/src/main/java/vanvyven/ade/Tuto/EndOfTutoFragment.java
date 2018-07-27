package vanvyven.ade.Tuto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vanvyven.ade.BasicActivity;
import vanvyven.ade.MainActivity;
import vanvyven.ade.R;


public class EndOfTutoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_end_tuto,container,false);


        ImageView i = (ImageView) rootView.findViewById(R.id.imageButtonTuto);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicActivity.mPrefs.edit().putBoolean(BasicActivity.MPREF_TUTO,true).apply();
                Intent s = new Intent(getActivity(),MainActivity.class);
                startActivity(s);
            }
        });
        return rootView;
    }
}

