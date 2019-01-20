package org.seniorlaguna.numbers;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import org.seniorlaguna.engine.custom.SoundActivity;
import org.seniorlaguna.engine.preferences.SimpleBooleanPreference;
import org.seniorlaguna.engine.sound.MusicPlayer;
import org.seniorlaguna.engine.utils.*;

public class LogoActivity extends SoundActivity implements Runnable {

    public final int DELAY = 1500;
    public final int MUSIC_ID = R.raw.music;

    private Handler handler;
    private int handlerState = 0;

    private SimpleBooleanPreference musicPreference;
    private boolean keepMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        scaleViews();
        handler = new Handler();
        handler.post(this);

        musicPreference = new SimpleBooleanPreference(this, Constants.MUSIC_PREFERENCE, true);
        MusicPlayer.init(this, MUSIC_ID, musicPreference);
        if (musicPreference.getPreference()) MusicPlayer.play();
        MusicPlayer.setVolume(100);
    }

    @Override
    public void run() {
        if (handlerState == 0) {
            handlerState += 1;
            handler.postDelayed(this, DELAY);
        }
        else if (handlerState == 1) {
            startActivityWithMusic(new Intent(this, MenuActivity.class));
        }
    }

    protected void scaleViews() {
        int width = (int) (DisplayUtils.getScreenWidth(this) * 0.5);
        ViewUtils.scaleView(this, R.id.logoImageView, ViewGroup.LayoutParams.WRAP_CONTENT, width);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            handler.removeCallbacks(this);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
