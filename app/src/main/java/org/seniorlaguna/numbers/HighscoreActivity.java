package org.seniorlaguna.numbers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.KeyEvent;

import org.seniorlaguna.engine.custom.SoundActivity;
import org.seniorlaguna.engine.utils.ViewUtils;

public class HighscoreActivity extends SoundActivity {

    private SharedPreferences sharedPreferences;

    protected boolean keepMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        showHighscores();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivityWithMusic(new Intent(this, MenuActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showHighscores() {
        float originalHighscore = sharedPreferences.getFloat(Integer.toString(Constants.ORIGINAL_GAME), 0);
        float reverseHighscore = sharedPreferences.getFloat(Integer.toString(Constants.REVERSE_GAME), 0);
        float surviveHighscore = sharedPreferences.getFloat(Integer.toString(Constants.SURVIVE_GAME), 0);

        String originalHighscoreText;
        String reverseHighscoreText;
        String surviveHighscoreText;
        
        if (originalHighscore == 0) {
            originalHighscoreText = "Original: -";
        }
        else {
            originalHighscoreText = "Original: " + Float.toString(originalHighscore) + " s";
        }

        if (reverseHighscore == 0) {
            reverseHighscoreText = "Reverse: -";
        }
        else {
            reverseHighscoreText = "Reverse: " + Float.toString(reverseHighscore) + " s";
        }

        if (surviveHighscore == 0) {
            surviveHighscoreText = "Survive: -";
        }
        else {
            surviveHighscoreText = "Survive : Lvl " + Integer.toString((int) surviveHighscore);
        }

        ViewUtils.fillTextView(this, R.id.originalHighscoreTextView, originalHighscoreText);
        ViewUtils.fillTextView(this, R.id.reverseHighscoreTextView, reverseHighscoreText);
        ViewUtils.fillTextView(this, R.id.surviveHighscoreTextView, surviveHighscoreText);
    }
}
