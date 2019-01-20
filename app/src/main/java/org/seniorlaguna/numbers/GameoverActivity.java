package org.seniorlaguna.numbers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.seniorlaguna.engine.custom.SoundActivity;
import org.seniorlaguna.engine.utils.ViewUtils;

public class GameoverActivity extends SoundActivity implements View.OnClickListener {

    protected int game;
    protected int order;
    protected float myScore;

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    protected boolean keepMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        handleData(getIntent().getExtras());
        initHighscore();
        initTitle();
        showResult();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivityWithMusic(new Intent(this, MenuActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.againImageButton):
                switch (game) {
                    case (Constants.ORIGINAL_GAME):
                        startActivityWithMusic(new Intent(this, OriginalGameActivity.class));
                        break;
                    case (Constants.REVERSE_GAME):
                        startActivityWithMusic(new Intent(this, ReverseGameActivity.class));
                        break;
                    case (Constants.SURVIVE_GAME):
                        startActivityWithMusic(new Intent(this, SurviveGameActivity.class));
                        break;
                }
                break;
            case (R.id.shareImageButton):
                shareScreenshot();
                break;

            case (R.id.quitImageButton):
                startActivityWithMusic(new Intent(this, MenuActivity.class));
                break;
        }

    }

    protected void handleData(Bundle bundle) {
        game = bundle.getInt(Constants.GAME_CODE);
        order = bundle.getInt(Constants.ORDER_CODE);
        myScore = bundle.getFloat(Constants.MY_SCORE_CODE);
    }

    protected void initHighscore() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }

    protected boolean isHighscore() {
        if (getHighscore() == 0) return true;
        if (order == Constants.ASCENDING) return (myScore < getHighscore());
        if (order == Constants.DESCENDING) return (myScore > getHighscore());
        return false;
    }

    protected float getHighscore() {
        return sharedPreferences.getFloat(Integer.toString(game), 0);
    }

    protected void setHighscore(float myScore) {
        editor.putFloat(Integer.toString(game), myScore);
        editor.commit();
    }

    protected void showResult() {
        if (isHighscore()) {
            ((TextView) findViewById(R.id.statusTextView)).setText(getString(R.string.highscore_status));
            setHighscore(myScore);
        }
        else {
            if (game == Constants.ORIGINAL_GAME || game == Constants.REVERSE_GAME) ((TextView) findViewById(R.id.statusTextView)).setText(getString(R.string.normal_status));
            if (game == Constants.SURVIVE_GAME) ((TextView) findViewById(R.id.statusTextView)).setText("Your Level");
        }

        TextView yourTimeTextView = (TextView) findViewById(R.id.yourTimeTextView);
        Animation pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);

        if (game == Constants.ORIGINAL_GAME || game == Constants.REVERSE_GAME) {
            yourTimeTextView.setText(Float.toString(myScore) + " s");
        }
        else if (game == Constants.SURVIVE_GAME) {
            yourTimeTextView.setText("Level " + Integer.toString((int) myScore));
        }
        yourTimeTextView.startAnimation(pulseAnimation);
    }

    protected void initTitle() {
        switch (game) {
            case (Constants.ORIGINAL_GAME):
                ViewUtils.fillTextView(this, R.id.gameoverTextView, "Original Game");
                break;
            case (Constants.REVERSE_GAME):
                ViewUtils.fillTextView(this, R.id.gameoverTextView, "Reverse Game");
                break;
            case (Constants.SURVIVE_GAME):
                ViewUtils.fillTextView(this, R.id.gameoverTextView, "Survive Game");
                break;
        }
    }

    protected void shareScreenshot() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.gameoverScreen);
        Bitmap screenshot;
        File screenshotFile = new File(Environment.getExternalStorageDirectory(), "tmpScore.jpeg");
        FileOutputStream outputStream;
        Intent shareIntent;
        Uri uri = Uri.fromFile(screenshotFile);

        try {
            outputStream = new FileOutputStream(screenshotFile);
            relativeLayout.setDrawingCacheEnabled(true);
            screenshot = relativeLayout.getDrawingCache();
            screenshot.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getText(R.string.share_text));
            startActivity(Intent.createChooser(shareIntent, "Share Your Score!"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
