package org.seniorlaguna.engine.custom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.seniorlaguna.engine.sound.MusicPlayer;


/*
 *  SoundActivity
 *
 *
 */

public class SoundActivity extends AppCompatActivity {
    private boolean keepMusic = false;

    @Override
    protected void onResume() {
        super.onResume();
        MusicPlayer.resume();
        keepMusic = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!keepMusic) {
            MusicPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!keepMusic) {
            MusicPlayer.release();
        }
    }

    protected void startActivityWithMusic(Intent intent) {
        keepMusic = true;
        startActivity(intent);
        finish();
    }
}
