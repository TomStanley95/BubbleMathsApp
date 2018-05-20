package com.example.admin.bubblemaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Enable this if you want to navigate up after any preference change
//        switch (key){
//            case "socialMediaPref":
//                getActivity().navigateUpTo(new Intent(getContext(),MainActivity.class));
//                break;
//
//            case "mathOperatorsPref":
//                getActivity().navigateUpTo(new Intent(getContext(),MainActivity.class));
//                break;
//            case "tiltSwitchPref":
//                getActivity().navigateUpTo(new Intent(getContext(),MainActivity.class));
//                break;
//        }
    }
}
