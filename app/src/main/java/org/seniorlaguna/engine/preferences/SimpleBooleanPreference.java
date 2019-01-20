package org.seniorlaguna.engine.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SimpleBooleanPreference {

    private Context context;
    private String preferenceName;
    private boolean defaultValue;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SimpleBooleanPreference(Context context, String preferenceName, boolean defaultValue) {
        this.context = context;
        this.preferenceName = preferenceName;
        this.defaultValue = defaultValue;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public boolean getPreference() {
        return sharedPreferences.getBoolean(preferenceName, defaultValue);
    }

    public void setPreference(boolean value) {
        editor.putBoolean(preferenceName, value);
        editor.commit();
    }

    public void togglePreference() {
        setPreference(!getPreference());
    }



}
