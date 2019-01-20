package org.seniorlaguna.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.seniorlaguna.engine.custom.SoundActivity;
import org.seniorlaguna.engine.preferences.SimpleBooleanPreference;
import org.seniorlaguna.engine.sound.MusicPlayer;
import org.seniorlaguna.engine.utils.DisplayUtils;
import org.seniorlaguna.engine.utils.ViewUtils;

public class MenuActivity extends SoundActivity implements View.OnClickListener{

    public static final float BUTTON_HEIGHT_FACTOR = 0.15f;
    public static final float TITLE_HEIGHT_FACTOR = 0.1f;

    private SimpleBooleanPreference musicPreference;
    private boolean keepMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initMusic();
        scaleViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.originalImageButton):
                startActivityWithMusic(new Intent(this, OriginalGameActivity.class));
                break;

            case (R.id.reverseImageButton):
                startActivityWithMusic(new Intent(this, ReverseGameActivity.class));
                break;

            case (R.id.surviveImageButton):
                startActivityWithMusic(new Intent(this, SurviveGameActivity.class));
                break;

            case (R.id.highscoreImageButton):
                startActivityWithMusic(new Intent(this, HighscoreActivity.class));
                break;

            case (R.id.soundToggleImageButton):
                ImageButton soundToggleButton = (ImageButton) findViewById(R.id.soundToggleImageButton);
                if (musicPreference.getPreference()) {
                    musicPreference.togglePreference();
                    soundToggleButton.setImageResource(R.drawable.musicoff);
                    MusicPlayer.pause();
                }
                else {
                    musicPreference.togglePreference();
                    soundToggleButton.setImageResource(R.drawable.musicon);
                    MusicPlayer.resume();
                }
        }
    }

    protected void initMusic() {
        musicPreference = new SimpleBooleanPreference(this, Constants.MUSIC_PREFERENCE, true);
        MusicPlayer.setVolume(50);

        if (musicPreference.getPreference()) {
            ((ImageButton) findViewById(R.id.soundToggleImageButton)).setImageResource(R.drawable.musicon);
        }
        else {
            ((ImageButton) findViewById(R.id.soundToggleImageButton)).setImageResource(R.drawable.musicoff);
        }
    }

    protected void scaleViews() {
        int views[] = {R.id.originalImageButton, R.id.reverseImageButton, R.id.surviveImageButton, R.id.highscoreImageButton};
        int screenHeight = DisplayUtils.getScreenHeight(this);
        int height;

        height = (int) (screenHeight * TITLE_HEIGHT_FACTOR);
        ViewUtils.scaleView(this, R.id.titleImageView, height, ViewGroup.LayoutParams.MATCH_PARENT);

        height = (int) (screenHeight * BUTTON_HEIGHT_FACTOR);
        for (int id : views) {
            ViewUtils.scaleView(this, id, height, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}