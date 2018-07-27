package vanvyven.ade.Tuto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import vanvyven.ade.BasicActivity;
import vanvyven.ade.MainActivity;
import vanvyven.ade.R;
/**
 * Created by nicolasvanvyve in mai 2017.
 */

public class ScreenSlidePageFragment extends Fragment{

    private static final String TAG = "TUTO FRAG";

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_tuto,container,false);
        try {
            int image = R.drawable.tuto1;

            Bundle bundle = getArguments();
            if (bundle != null) {
                image = bundle.getInt("image_tuto",0);
            } else {
                BasicActivity.myLog(TAG,"bundle is null");
            }
            ImageView screen = (ImageView) rootView.findViewById(R.id.screenshot);
            screen.setImageResource(image);

            return rootView;
        }
        catch (OutOfMemoryError e){
            BasicActivity.myLog(TAG,e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(),"Out Of Memory Error - Skip Tutorial",Toast.LENGTH_SHORT).show();
            BasicActivity.mPrefs.edit().putBoolean(BasicActivity.MPREF_TUTO,true).apply();
            startActivity(new Intent(getActivity(),MainActivity.class));
            return rootView;
        }
    }
}

