package org.seniorlaguna.engine.sound;

import android.content.Context;
import android.media.MediaPlayer;

import org.seniorlaguna.engine.preferences.SimpleBooleanPreference;

public class MusicPlayer {

    private static MediaPlayer mediaPlayer;
    private static SimpleBooleanPreference preference;

    public static void init(Context context, int soundId, SimpleBooleanPreference preference) {
        MusicPlayer.preference = preference;
        MusicPlayer.mediaPlayer = MediaPlayer.create(context, soundId);
        MusicPlayer.mediaPlayer.setLooping(true);
    }

    public static void play() {
        if (preference.getPreference()) MusicPlayer.mediaPlayer.start();
    }

    public static void pause() {
        MusicPlayer.mediaPlayer.pause();
    }

    public static void resume() {
        if (preference.getPreference()) MusicPlayer.play();
    }

    public static void release() {
        MusicPlayer.mediaPlayer.release();
    }

    public static void setVolume(int volume) {
        if (volume <= 0) {
            MusicPlayer.mediaPlayer.setVolume(0, 0);
        }
        else if (volume >= 100) {
            MusicPlayer.mediaPlayer.setVolume(100, 100);
        }
        else {
            MusicPlayer.mediaPlayer.setVolume(volume, volume);
        }

    }
}
