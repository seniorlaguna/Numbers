package org.seniorlaguna.engine.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SimpleIntegerPreference {

    private Context context;
    private String preferenceName;
    private int defaultValue;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SimpleIntegerPreference(Context context, String preferenceName, int defaultValue) {
        this.context = context;
        this.preferenceName = preferenceName;
        this.defaultValue = defaultValue;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public int getPreference() {
        return sharedPreferences.getInt(preferenceName, defaultValue);
    }

    public void setPreference(int value) {
        editor.putInt(preferenceName, value);
    }



}
